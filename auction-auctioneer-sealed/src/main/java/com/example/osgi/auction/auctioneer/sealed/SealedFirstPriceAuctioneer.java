package com.example.osgi.auction.auctioneer.sealed;

import java.util.Dictionary;
import java.util.Hashtable;

import com.example.osgi.auction.Auction;
import com.example.osgi.auction.spi.Auctioneer;

public class SealedFirstPriceAuctioneer implements Auctioneer {
  private static final String SEALED_FIRST_PRICE = "Sealed-First-Price";
  private final int DURATION = 3;
  private final Dictionary<String, Object> properties = new Hashtable<String, Object>();
  private final Auction auction;

  public SealedFirstPriceAuctioneer() {
    properties.put(Auction.TYPE, SEALED_FIRST_PRICE);
    properties.put(Auction.DURATION, DURATION);
    auction = new SealedFirstPriceAuction(DURATION);
  }

  @Override public Auction getAuction() {
    return auction;
  }

  @Override public Dictionary<String, Object> getAuctionProperties() {
    return properties;
  }
}
