package com.example.osgi.auction.spi;

import java.util.Dictionary;

import com.example.osgi.auction.Auction;

/**
 * Vendors that would like to plug a new auction type into the auction application must
 * implement this class.
 */
public interface Auctioneer {
  /**
   * @return the auction service object that will be used by the sellers and buyers.
   */
  Auction getAuction();

  /**
   *
   * @return the service properties that are associated with the auction service object
   */
  Dictionary<String, Object> getAuctionProperties();
}
