package com.one16.sixletterwordsapi.domain.dictionary;

public record Woord(String tekst) implements Comparable<Woord> {
  public Woord(String tekst) {
    if (tekst == null || tekst.isBlank()) {
      throw new IllegalArgumentException("geen lege woorden toegelaten");
    }
    this.tekst = tekst;
  }
  
  public Woordensamenstelling samengesteldMet(Woord woord) {
    return new Woordensamenstelling(this, woord);
  }

  public boolean heeftLengte(int length) {
    return tekst().length() == length;
  }

  @Override
  public int compareTo(Woord o) {
    return tekst.compareTo(o.tekst);
  }
}
