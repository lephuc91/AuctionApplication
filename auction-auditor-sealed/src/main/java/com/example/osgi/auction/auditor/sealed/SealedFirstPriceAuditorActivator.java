package com.example.osgi.auction.auditor.sealed;

import com.example.osgi.auction.spi.Auditor;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class SealedFirstPriceAuditorActivator implements BundleActivator {
  private ServiceRegistration serviceRegistration;

  @Override public void start(BundleContext context) throws Exception {
    serviceRegistration =
        context.registerService(
            Auditor.class.getName(),
            new SealedFirstPriceAuditor(), null);
  }

  @Override public void stop(BundleContext context) throws Exception {
    serviceRegistration.unregister();
  }
}
