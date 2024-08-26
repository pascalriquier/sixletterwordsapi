package com.one16.sixletterwordsapi.domain.dictionary;

public record Woord(String tekst) {
  public Woord(String tekst) {
    if (tekst == null || tekst.isBlank()) {
      throw new IllegalArgumentException("geen lege woorden toegelaten");
    }
    this.tekst = tekst;
  }
  
  public Woordensamenstelling samengesteldMet(Woord woord) {
    return new Woordensamenstelling(this, woord);
  }
}
