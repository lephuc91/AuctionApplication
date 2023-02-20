package com.example.osgi.auction.api.seller.simple;

import com.example.osgi.auction.api.Auction;
import com.example.osgi.auction.api.Participant;
import com.example.osgi.auction.api.InvalidOfferException;

public class Seller implements Participant, Runnable {
  private final String name;
  private Auction auction;

  public Seller(String name) {
    this.name = name;
  }

  @Override public String getName() {
    return this.name;
  }

  @Override public void onAcceptance(Auction auction, String item, float price) {
    System.out.println(this.name + " sold " + item + " for " + price);
  }

  @Override public void onRejection(Auction auction, String item, float bestBid) {
    System.out.println("No bidders accepted asked price for " + item
        + ", best bid was " + bestBid);
  }

  @Override public void run() {
    try {
      auction.ask("bicycle", 24.0f, this);
    } catch (InvalidOfferException e) {
      e.printStackTrace();
    }
    auction = null;
  }

  public void ask(Auction auction) {
    this.auction = auction;
    new Thread(this).start();
  }

  public Auction getAuction() {
    return auction;
  }

  public void setAuction(Auction auction) {
    this.auction = auction;
  }
}
