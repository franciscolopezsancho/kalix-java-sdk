/* This code was generated by Akka Serverless tooling.
 * As long as this file exists it will not be re-generated.
 * You are free to make changes to this file.
 */

package customer;

import com.akkaserverless.javasdk.AkkaServerless;
import customer.domain.CustomerEntity;
import customer.domain.CustomerEntityProvider;
import customer.view.CustomerByNameView;
import customer.view.CustomerByNameViewProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {

  private static final Logger LOG = LoggerFactory.getLogger(Main.class);

  public static AkkaServerless createAkkaServerless() {
    // tag::register-with-class[]
    return new AkkaServerless()
            .register(CustomerByNameViewProvider.of(CustomerByNameView::new))
            // end::register-with-class[]
            .register(CustomerEntityProvider.of(CustomerEntity::new));
  }

  public static void main(String[] args) throws Exception {
    LOG.info("starting the Akka Serverless service");
    createAkkaServerless().start();
  }
}