package com.example.osgi.auction.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.example.osgi.auction.Auction;
import com.example.osgi.auction.InvalidOfferException;
import com.example.osgi.auction.Participant;
import com.example.osgi.auction.spi.Auctioneer;
import com.example.osgi.auction.spi.Auditor;

public class AuctionWrapper implements Auction {
  private Collection<Auditor> auditors;
  private Auctioneer delegate;
  private Map<String, List<Float>> bidsPerItem = new HashMap<>();
  private Float asK;

  class ParticipantWrapper implements Participant {
    private Participant delegate;

    public ParticipantWrapper(Participant delegate) {
      this.delegate = delegate;
    }

    @Override public String getName() {
      return delegate.getName();
    }

    @Override public void onAcceptance(Auction auction, String item, float price) {
      delegate.onAcceptance(auction, item, price);

      Float[] bids = bidsPerItem.get(item).toArray(new Float[0]);
      for (Auditor auditor : auditors) {
        auditor.onAcceptance(AuctionWrapper.this.delegate, delegate, item, asK, price, bids);
      }
    }

    @Override public void onRejection(Auction auction, String item, float bestBid) {
      delegate.onRejection(auction, item, bestBid);
      Float[] bids = bidsPerItem.get(item).toArray(new Float[0]);
      for (Auditor auditor : auditors) {
        auditor.onRejection(AuctionWrapper.this.delegate, delegate, item, asK, bids);
      }
    }
  }
  public AuctionWrapper(Auctioneer delegate, Collection<Auditor> auditors) {
    this.delegate = delegate;
    this.auditors = auditors;
  }

  @Override public Float ask(String item, float price, Participant seller) throws InvalidOfferException {
    asK = price;
    return delegate.getAuction().ask(item, price, new ParticipantWrapper(seller));
  }

  @Override public Float bid(String item, float price, Participant buyer) throws InvalidOfferException {
    List<Float> bids = bidsPerItem.get(item);
    if (bids == null) {
      bids = new LinkedList<>();
      bidsPerItem.put(item, bids);
    }
    bids.add(price);
    return delegate.getAuction().bid(item, price, new ParticipantWrapper(buyer));
  }
}
