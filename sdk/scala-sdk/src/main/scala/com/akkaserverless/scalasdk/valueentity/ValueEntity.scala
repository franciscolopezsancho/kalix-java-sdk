/*
 * Copyright 2021 Lightbend Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.akkaserverless.scalasdk.valueentity

import scala.jdk.CollectionConverters._
import akka.actor.ActorSystem
import com.akkaserverless.javasdk.ServiceCallFactory
import com.akkaserverless.scalasdk.Metadata
import com.akkaserverless.scalasdk.ServiceCall
import com.akkaserverless.scalasdk.Context
import com.akkaserverless.scalasdk.SideEffect
import com.akkaserverless.scalasdk.impl.valueentity.ValueEntityEffectImpl

object ValueEntity {
  object Effect {

    /**
     * Construct the effect that is returned by the command handler. The effect describes next processing actions, such
     * as updating state and sending a reply.
     *
     * @param [S]
     *   The type of the state for this entity.
     */
    trait Builder[S] {

      def updateState(newState: S): OnSuccessBuilder[S]

      def deleteState: OnSuccessBuilder[S]

      /**
       * Create a message reply.
       *
       * @param message
       *   The payload of the reply.
       * @return
       *   A message reply.
       * @tparam T
       *   The type of the message that must be returned by this call.
       */
      def reply[T](message: T): Effect[T]

      /**
       * Create a message reply.
       *
       * @param message
       *   The payload of the reply.
       * @param metadata
       *   The metadata for the message.
       * @return
       *   A message reply.
       * @tparam T
       *   The type of the message that must be returned by this call.
       */
      def reply[T](message: T, metadata: Metadata): Effect[T]

      /**
       * Create a forward reply.
       *
       * @param serviceCall
       *   The service call representing the forward.
       * @return
       *   A forward reply.
       * @tparam T
       *   The type of the message that must be returned by this call.
       */
      def forward[T](serviceCall: ServiceCall): Effect[T]

      /**
       * Create an error reply.
       *
       * @param description
       *   The description of the error.
       * @return
       *   An error reply.
       * @tparam T
       *   The type of the message that must be returned by this call.
       */
      def error[T](description: String): Effect[T]

      /**
       * Create a reply that contains neither a message nor a forward nor an error.
       *
       * <p>This may be useful for emitting effects without sending a message.
       *
       * @return
       *   The reply.
       * @tparam T
       *   The type of the message that must be returned by this call.
       */
      def noReply[T]: Effect[T]
    }

    trait OnSuccessBuilder[S] {

      /**
       * Reply after for example `updateState`.
       *
       * @param message
       *   The payload of the reply.
       * @return
       *   A message reply.
       * @tparam T
       *   The type of the message that must be returned by this call.
       */
      def thenReply[T](message: T): Effect[T]

      /**
       * Reply after for example <code>updateState</code>.
       *
       * @param message
       *   The payload of the reply.
       * @param metadata
       *   The metadata for the message.
       * @return
       *   A message reply.
       * @tparam T
       *   The type of the message that must be returned by this call.
       */
      def thenReply[T](message: T, metadata: Metadata): Effect[T]

      /**
       * Create a forward reply after for example <code>updateState</code>.
       *
       * @param serviceCall
       *   The service call representing the forward.
       * @return
       *   A forward reply.
       * @tparam T
       *   The type of the message that must be returned by this call.
       */
      def thenForward[T](serviceCall: ServiceCall): Effect[T]

      /**
       * Create a reply that contains neither a message nor a forward nor an error.
       *
       * <p>This may be useful for emitting effects without sending a message.
       *
       * @return
       *   The reply.
       * @tparam T
       *   The type of the message that must be returned by this call.
       */
      def thenNoReply[T]: Effect[T]
    }
  }

  /**
   * A return type to allow returning forwards or failures, and attaching effects to messages.
   *
   * @tparam T
   *   The type of the message that must be returned by this call.
   */
  trait Effect[T] {

    /**
     * Attach the given side effects to this reply.
     *
     * @param sideEffects
     *   The effects to attach.
     * @return
     *   A new reply with the attached effects.
     */
    def addSideEffects(sideEffects: Seq[SideEffect]): Effect[T]
  }

}

/** @param [S] The type of the state for this entity. */
abstract class ValueEntity[S] {
  private var _commandContext: Option[CommandContext] = None

  /**
   * Implement by returning the initial empty state object. This object will be passed into the command handlers, until
   * a new state replaces it.
   *
   * <p>Also known as "zero state" or "neutral state".
   *
   * <p><code>null</code> is an allowed value.
   */
  def emptyState: S

  /**
   * Additional context and metadata for a command handler.
   *
   * <p>It will throw an exception if accessed from constructor.
   */
  protected def commandContext(): CommandContext = {
    try {
      _commandContext.get
    } catch {
      case _: NoSuchElementException =>
        throw new IllegalStateException("CommandContext is only available when handling a command.")
    }
  }

  /** INTERNAL API */
  def _internalSetCommandContext(context: Option[CommandContext]): Unit = {
    _commandContext = context;
  }

  protected def effects: ValueEntity.Effect.Builder[S] =
    ValueEntityEffectImpl[S]()

}