package com.example.osgi.auction;

public interface Participant {
  String getName();

  /**
   * inform the seller that the seller’s bid was awarded the item
   * @param auction
   * @param item
   * @param price
   */
  void onAcceptance(Auction auction, String item, float price);
  void onRejection(Auction auction, String item, float bestBid);
}
