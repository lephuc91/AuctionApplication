package com.example.osgi.auction;

public class InvalidOfferException extends Exception{
  public InvalidOfferException() {
  }

  public InvalidOfferException(String message) {
    super(message);
  }

  public InvalidOfferException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidOfferException(Throwable cause) {
    super(cause);
  }

  public InvalidOfferException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
