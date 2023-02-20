package com.example.osgi.auction.api.manager;

import java.util.HashMap;
import java.util.Map;

import com.example.osgi.auction.api.Auction;
import com.example.osgi.auction.spi.Auctioneer;
import com.example.osgi.auction.spi.Auditor;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

public class AuctionManagerActivator implements BundleActivator, ServiceListener {
  private BundleContext                              bundleContext;
  private Map<ServiceReference, ServiceRegistration> registeredAuctions =
      new HashMap<>();
  private Map<ServiceReference, Auditor>             registeredAuditors =
      new HashMap<>();

  @Override public void start(BundleContext context) throws Exception {
    this.bundleContext = bundleContext;
    String auctionOrAuctioneerFilter =
        "(|" +
            "(objectClass=" + Auctioneer.class.getName() + ")" +
            "(objectClass=" + Auditor.class.getName() + ")" +
            ")";
    ServiceReference[] references =
        bundleContext.getServiceReferences((String) null,
            auctionOrAuctioneerFilter);
    if (references != null) {
      for (ServiceReference serviceReference : references) {
        registerService(serviceReference);
      }
    }
    bundleContext.addServiceListener(this, auctionOrAuctioneerFilter);
  }

  private void registerService(ServiceReference serviceReference) {
    Object serviceObject = bundleContext.getService(serviceReference);
    if (serviceObject instanceof Auctioneer) {
      registerAuctioneer(serviceReference, (Auctioneer)
          serviceObject);
    } else {
      registerAuditor(serviceReference, (Auditor) serviceObject);
    }
  }

  private void registerAuditor(ServiceReference serviceReference, Auditor serviceObject) {
    registeredAuditors.put(serviceReference,
        serviceObject);
  }

  private void registerAuctioneer(ServiceReference serviceReference, Auctioneer serviceObject) {
    Auction auction =
        new AuctionWrapper(serviceObject,
            registeredAuditors.values());
    ServiceRegistration auctionServiceRegistration =
        bundleContext.registerService(Auction.class.getName(),
            auction, serviceObject.getAuctionProperties());
    registeredAuctions.put(serviceReference,
        auctionServiceRegistration);
  }

  @Override public void stop(BundleContext context) throws Exception {
    bundleContext.removeServiceListener(this);
  }

  @Override public void serviceChanged(ServiceEvent event) {
    ServiceReference serviceReference = event.getServiceReference();
    switch (event.getType()) {
      case ServiceEvent.REGISTERED: {
        registerService(serviceReference);
        break;
      }
      case ServiceEvent.UNREGISTERING: {
        String[] servicesInterfaces = (String[]) serviceReference.getProperty("objectClass");
        if (Auctioneer.class.getName().equals(servicesInterfaces[0])) {
          unregisterAuctioneer(serviceReference);
        } else {
          unregisterAuditor(serviceReference);
        }
        bundleContext.ungetService(serviceReference);
        break;
      }
      default:
    }
  }

  private void unregisterAuditor(ServiceReference serviceReference) {
    registeredAuditors.remove(serviceReference);
  }

  private void unregisterAuctioneer(ServiceReference auctioneerServiceReference) {
    ServiceRegistration auctionServiceRegistration = registeredAuctions.remove(auctioneerServiceReference);
    if (auctionServiceRegistration != null) {
      auctionServiceRegistration.unregister();
    }
  }
}
