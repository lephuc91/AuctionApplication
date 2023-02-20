package com.example.osgi.auction.spi;

import com.example.osgi.auction.Participant;

public interface Auditor {
  /**
   * auction’s transaction is completed successfully; for every item that’s sold, the auction system implementation calls back all available auditors with interesting data, such as the
   * item that was sold, the selling price, the asked price, and all non-accepted bids
   *
   * @param auctioneer
   * @param participant
   * @param item
   * @param ask
   * @param acceptedBid
   * @param bids
   */
  void onAcceptance(Auctioneer auctioneer, Participant participant,
                    String item, float ask,
                    float acceptedBid, Float[] bids);

  /**
   * called when a transaction completes unsuccessfully, such as
   * when there are no bids for an offered item.
   *
   * @param auctioneer
   * @param participant
   * @param item
   * @param ask
   * @param rejectedBids
   */
  void onRejection(Auctioneer auctioneer, Participant participant,
                   String item, float ask,
                   Float[] rejectedBids);
}
