package com.one16.sixletterwordsapi.domain.dictionary;

import java.util.List;
import java.util.stream.Collectors;

public record Woordensamenstelling(List<Woord> onderdelen) {

  public Woordensamenstelling(List<Woord> onderdelen) {
    if (onderdelen == null || onderdelen.size() < 2
        || onderdelen.stream().anyMatch(w -> w == null)) {
      throw new IllegalArgumentException(
          "Kan alleen woorden samenstellen uit minstens 2 non null woorden");
    }
    this.onderdelen = onderdelen;
  }

  public Woordensamenstelling(Woord eerste, Woord tweede) {
    this(List.of(eerste, tweede));
  }

  public Woord woord() {
    return new Woord(this.onderdelen.stream().map(Woord::tekst).collect(Collectors.joining("")));
  }

}
