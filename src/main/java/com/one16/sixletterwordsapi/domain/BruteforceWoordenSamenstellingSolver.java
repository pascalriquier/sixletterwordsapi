package com.one16.sixletterwordsapi.domain;

import com.one16.sixletterwordsapi.domain.dictionary.Woordenboek;
import com.one16.sixletterwordsapi.domain.dictionary.Woordensamenstelling;
import java.util.stream.Stream;

public class BruteforceWoordenSamenstellingSolver implements WoordensamenstellingSolver {

  private final Woordenboek woordenboek;
  private final WoordensamenstellingSolverConfiguration configuration;

  public BruteforceWoordenSamenstellingSolver(Woordenboek woordenboek, WoordensamenstellingSolverConfiguration configuration) {
    this.woordenboek = woordenboek;
    this.configuration = configuration;
  }

  @Override
  public Stream<Woordensamenstelling> samengesteldeWoorden() {
    return alleWoordsamenstellingenVanGevraagdeLengte()
        .filter(woordensamenstelling -> woordensamenstelling.staatIn(woordenboek))
        .filter(woordensamenstelling -> woordensamenstelling.heeftLengte(configuration.woordLengte()));
  }

  private Stream<Woordensamenstelling> alleWoordsamenstellingenVanGevraagdeLengte() {
    Stream<Woordensamenstelling> samenstellingen = alleWoordsamenstellingenVanTweeWoorden();
    return verderSamengesteldMetAlleWoorden(samenstellingen, configuration.aantalWoorden() - 2);
  }

  private Stream<Woordensamenstelling> verderSamengesteldMetAlleWoorden(
      Stream<Woordensamenstelling> samenstellingen, int aantalWoorden) {
    if (aantalWoorden == 0) {
      return samenstellingen;
    } else {
      return verderSamengesteldMetAlleWoorden(samenstellingen.flatMap(
          samenstelling -> woordenboek.alleWoorden().map(woord -> samenstelling.metWoord(woord))),
          aantalWoorden - 1);
    }
  }

  private Stream<Woordensamenstelling> alleWoordsamenstellingenVanTweeWoorden() {
    return woordenboek.alleWoorden().flatMap(woord -> woordenboek.alleWoorden()
        .map(anderWoord -> new Woordensamenstelling(woord, anderWoord)));
  }

}
