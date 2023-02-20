package com.example;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

import java.util.*;

public class Launcher {
  public static void main(String[] args) throws BundleException {
    FrameworkFactory frameworkFactory = ServiceLoader.load(FrameworkFactory.class).iterator().next();
    Map<String, String> config = new HashMap<>();
    Framework framework = frameworkFactory.newFramework(config);
    framework.start();
    BundleContext context = framework.getBundleContext();
    List<Bundle> bundles = new ArrayList<>();
    bundles.add(context.installBundle("file:bundles/jansi-1.18.jar"));
    bundles.add(context.installBundle("file:bundles/jline-3.13.2.jar"));
    bundles.add(context.installBundle("file:bundles/org.apache.felix.gogo.command-1.1.2.jar"));
    bundles.add(context.installBundle("file:bundles/org.apache.felix.gogo.jline-1.1.8.jar"));
    bundles.add(context.installBundle("file:bundles/org.apache.felix.gogo.runtime-1.1.4.jar"));
    bundles.add(context.installBundle("file:auction-api/build/libs/auction-api-1.0.jar"));


    for (Bundle bundle : bundles) {
      bundle.start();
    }
    // Ctr + D to stop framework
  }
}
