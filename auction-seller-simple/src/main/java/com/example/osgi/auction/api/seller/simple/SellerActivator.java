package com.example.osgi.auction.api.seller.simple;

import com.example.osgi.auction.api.Auction;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

public class SellerActivator implements BundleActivator, ServiceListener {
  private BundleContext bundleContext;
  private Seller seller = new Seller("Seller 1");
  @Override public void start(BundleContext context) throws Exception {
    this.bundleContext = context;
    //Filter uses predefined objectClass property
    String filter =
        "(&(objectClass="
            + Auction.class.getName()
            + ")(" + Auction.TYPE
            + "=Sealed-First-Price))";
    //Look up service using properties as filter criteria
    ServiceReference[] serviceReferences = bundleContext.getServiceReferences((String)null,filter);
    if (serviceReferences != null) {
      ask(serviceReferences[0]);
    } else {
      bundleContext.addServiceListener(this, filter);
    }
  }

  @Override public void stop(BundleContext context) throws Exception {

  }

  @Override public void serviceChanged(ServiceEvent event) {
    switch (event.getType()) {
      case ServiceEvent.REGISTERED: {
        ask(event.getServiceReference());
      }
      break;
      default:
        //do nothing
    }
  }

  private void ask(ServiceReference serviceReference) {
    Auction auction = (Auction)bundleContext.getService(serviceReference);
    if (auction != null) {
      seller.ask(auction);
      bundleContext.ungetService(serviceReference);
    }
  }
}
