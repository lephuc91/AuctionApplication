package com.example.osgi.auction.auctioneer.sealed;

import com.example.osgi.auction.spi.Auctioneer;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class SealedFirstPriceAuctioneerActivator implements BundleActivator {
  private ServiceRegistration serviceRegistration;

  @Override public void start(BundleContext context) throws Exception {
    SealedFirstPriceAuctioneer auctioneer = new SealedFirstPriceAuctioneer();
    serviceRegistration =
        context.registerService(Auctioneer.class.getName(), auctioneer, auctioneer.getAuctionProperties());
  }

  @Override public void stop(BundleContext context) throws Exception {
    serviceRegistration.unregister();
  }
}
