/* This code was generated by Akka Serverless tooling.
 * As long as this file exists it will not be re-generated.
 * You are free to make changes to this file.
 */
package customer.domain

import com.akkaserverless.scalasdk.testkit.ValueEntityResult
import com.akkaserverless.scalasdk.valueentity.ValueEntity
import com.google.protobuf.empty.Empty
import customer.api
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CustomerSpec
    extends AnyWordSpec
    with Matchers {

  "Customer" must {

    "have example test that can be removed" in {
      val testKit = CustomerTestKit(new Customer(_))
      // use the testkit to execute a command
      // and verify final updated state:
      // val result = testKit.someOperation(SomeRequest)
      // verify the response
      // val actualResponse = result.getReply()
      // actualResponse shouldBe expectedResponse
      // verify the final state after the command
      // testKit.currentState() shouldBe expectedState
    }

    "handle command Create" in {
      val testKit = CustomerTestKit(new Customer(_))
      // val result = testKit.create(api.Customer(...))
    }

    "handle command GetCustomer" in {
      val testKit = CustomerTestKit(new Customer(_))
      // val result = testKit.getCustomer(api.GetCustomerRequest(...))
    }

    "handle command ChangeName" in {
      val testKit = CustomerTestKit(new Customer(_))
      // val result = testKit.changeName(api.ChangeNameRequest(...))
    }

    "handle command ChangeAddress" in {
      val testKit = CustomerTestKit(new Customer(_))
      // val result = testKit.changeAddress(api.ChangeAddressRequest(...))
    }

  }
}
