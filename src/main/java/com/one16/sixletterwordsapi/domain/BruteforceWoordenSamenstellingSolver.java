package com.one16.sixletterwordsapi.domain;

import com.one16.sixletterwordsapi.domain.dictionary.Woordenboek;
import com.one16.sixletterwordsapi.domain.dictionary.Woordensamenstelling;
import java.util.Set;
import java.util.stream.Collectors;

public class BruteforceWoordenSamenstellingSolver implements WoordensamenstellingSolver {

  private final Woordenboek woordenboek;

  public BruteforceWoordenSamenstellingSolver(Woordenboek woordenboek) {
    this.woordenboek = woordenboek;
  }

  @Override
  public Set<Woordensamenstelling> samengesteldeWoorden() {
    return woordenboek.alleWoorden()
        .flatMap(woord -> woordenboek.alleWoorden()
            .map(anderWoord -> new Woordensamenstelling(woord, anderWoord)))
        .filter(woordensamenstelling -> woordenboek.bevat(woordensamenstelling.woord()))
        .collect(Collectors.toSet());
  }


}
