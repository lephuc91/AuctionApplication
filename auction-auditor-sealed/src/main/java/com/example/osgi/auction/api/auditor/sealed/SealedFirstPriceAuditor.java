package com.example.osgi.auction.api.auditor.sealed;

import com.example.osgi.auction.api.Auction;
import com.example.osgi.auction.api.Participant;
import com.example.osgi.auction.spi.Auctioneer;
import com.example.osgi.auction.spi.Auditor;

public class SealedFirstPriceAuditor implements Auditor {
  @Override public void onAcceptance(Auctioneer auctioneer, Participant participant, String item, float ask, float acceptedBid, Float[] bids) {

  }

  @Override public void onRejection(Auctioneer auctioneer, Participant participant, String item, float ask, Float[] rejectedBids) {

  }

  private void verify(Auctioneer auctioneer, Participant participant,
                      Float[] bids) {
    if ("Sealed-First-Price".equals(auctioneer.getAuctionProperties().get(Auction.TYPE))) {
      for (int i = 0; i < bids.length -1; i++) {
        if ((bids[i+1] - bids[i]) <= 1.0) {
          System.out.println("Warning to '" +
              participant.getName()
              + "': bids (" + bids[i] + ", " + bids[i + 1] + ") are too close together,possible disclosure may have happened");
        }
      }
    }
  }
}
