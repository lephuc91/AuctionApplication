package com.example.osgi.auction.api;

public interface Auction {
  String TYPE = "auction-type";
  String DURATION = "auction-duration";

  /**
   * inform the participants that the seller wishes to sell {@param item}
   * @param item
   * @param price
   * @param seller
   * @return depend on the type of auction. Return the highest available bid for the item being asked or null
   * if no bidders for this item exist or if it's a sealed auction
   * @throws InvalidOfferException
   */
  Float ask(String item, float price, Participant seller) throws InvalidOfferException;

  /**
   * inform the participants that the buyer wishes to buy {@param item} with the given {@param price}
   * @param item
   * @param price
   * @param buyer
   * @return if it's an open auction then returns the highest available bid for the item or null otherwise
   * @throws InvalidOfferException
   */
  Float bid(String item, float price, Participant buyer) throws InvalidOfferException;
}
