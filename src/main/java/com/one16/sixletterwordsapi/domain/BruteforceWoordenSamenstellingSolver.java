package com.one16.sixletterwordsapi.domain;

import com.one16.sixletterwordsapi.domain.dictionary.Woordenboek;
import com.one16.sixletterwordsapi.domain.dictionary.Woordensamenstelling;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BruteforceWoordenSamenstellingSolver implements WoordensamenstellingSolver {

  private final Woordenboek woordenboek;
  private final Predicate<Woordensamenstelling> woordensamenstellingFilter;

  public BruteforceWoordenSamenstellingSolver(Woordenboek woordenboek) {
    this(woordenboek, samenstelling -> true);
  }

  public BruteforceWoordenSamenstellingSolver(Woordenboek woordenboek,
      Predicate<Woordensamenstelling> woordensamenstellingFilter) {
    this.woordenboek = woordenboek;
    this.woordensamenstellingFilter = woordensamenstellingFilter;
  }

  public BruteforceWoordenSamenstellingSolver metWoordsamenstellingenDieVoldoenAan(
      Predicate<Woordensamenstelling> woordensamenstellingFilter) {
    return new BruteforceWoordenSamenstellingSolver(woordenboek, woordensamenstellingFilter);
  }

  @Override
  public Set<Woordensamenstelling> samengesteldeWoorden() {
    return woordenboek.alleWoorden()
        .flatMap(woord -> woordenboek.alleWoorden()
            .map(anderWoord -> new Woordensamenstelling(woord, anderWoord)))
        .filter(woordensamenstellingFilter
            .and(woordensamenstelling -> woordensamenstelling.staatIn(woordenboek)))
        .collect(Collectors.toSet());
  }
}
