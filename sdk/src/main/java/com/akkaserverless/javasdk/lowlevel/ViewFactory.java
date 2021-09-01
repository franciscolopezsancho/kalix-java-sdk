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

package com.akkaserverless.javasdk.lowlevel;

import com.akkaserverless.javasdk.impl.view.ViewHandler;
import com.akkaserverless.javasdk.view.ViewCreationContext;

/**
 * Low level interface for handling messages in views.
 *
 * <p>Generally, this should not be needed, instead, a class annotated with the {@link
 * com.akkaserverless.javasdk.view.View @View} and similar annotations should be used.
 */
public interface ViewFactory {
  /**
   * Create a view handler for the given context.
   *
   * @param context The context.
   * @return The handler for the given context.
   */
  ViewHandler create(ViewCreationContext context);
}