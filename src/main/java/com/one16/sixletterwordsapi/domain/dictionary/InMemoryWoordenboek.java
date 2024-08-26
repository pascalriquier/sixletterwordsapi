package com.one16.sixletterwordsapi.domain.dictionary;

import java.util.Set;
import java.util.stream.Stream;

public class InMemoryWoordenboek implements Woordenboek {
  private final Set<Woord> woorden;

  public InMemoryWoordenboek(Set<Woord> woorden) {
    if (woorden == null) {
      throw new IllegalArgumentException("geen lege collectie van woorden toegelaten");
    }
    this.woorden = woorden;
  }

  @Override
  public boolean bevat(Woord woord) {
    return woorden.contains(woord);
  }

  @Override
  public long aantalWoorden() {
    return woorden.size();
  }

  @Override
  public Stream<Woord> alleWoorden() {
    return woorden.stream();
  }

}
