package com.one16.sixletterwordsapi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.one16.sixletterwordsapi.domain.dictionary.InMemoryWoordenboek;
import com.one16.sixletterwordsapi.domain.dictionary.Woord;
import com.one16.sixletterwordsapi.domain.dictionary.Woordensamenstelling;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class BruteforceWoordenSamenstellingSolverTest {

  @Test
  void kanWoordensamenstellingenUitGegevenWoordenboekBepalen() {
    InMemoryWoordenboek woordenboek =
        new InMemoryWoordenboek(Set.of(new Woord("ABC"), new Woord("ABCDEF"), new Woord("DEF"),
            new Woord("foobar"), new Woord("bar"), new Woord("foo")));

    Set<Woordensamenstelling> samengesteldeWoorden =
        new BruteforceWoordenSamenstellingSolver(woordenboek,
            new WoordensamenstellingSolverConfiguration.Builder().build()).samengesteldeWoorden()
                .collect(Collectors.toSet());

    assertThat(samengesteldeWoorden).containsExactlyInAnyOrder(
        new Woordensamenstelling(List.of(new Woord("ABC"), new Woord("DEF"))),
        new Woordensamenstelling(List.of(new Woord("foo"), new Woord("bar"))));
  }

  @Test
  void kanWoordensamenstellingenVanMeerDan2WoordenMaken() {
    InMemoryWoordenboek woordenboek =
        new InMemoryWoordenboek(Set.of(new Woord("AB"), new Woord("ABCDEF"), new Woord("CD"),
            new Woord("EF"), new Woord("foobar"), new Woord("a"), new Woord("foob"), new Woord("r")));

    Set<Woordensamenstelling> samengesteldeWoorden =
        new BruteforceWoordenSamenstellingSolver(woordenboek,
            new WoordensamenstellingSolverConfiguration.Builder().metAantalWoorden(3).build())
                .samengesteldeWoorden().collect(Collectors.toSet());

    assertThat(samengesteldeWoorden).containsExactlyInAnyOrder(
        new Woordensamenstelling(List.of(new Woord("AB"), new Woord("CD"), new Woord("EF"))),
        new Woordensamenstelling(List.of(new Woord("foob"), new Woord("a"), new Woord("r"))));
  }

  @Test
  void kanLengteVanSamengesteldeWoordenMeegeven() {
    InMemoryWoordenboek woordenboek =
        new InMemoryWoordenboek(Set.of(new Woord("AB"), new Woord("ABCD"), new Woord("CD"),
            new Woord("foobar"), new Woord("bar"), new Woord("foo")));

    Set<Woordensamenstelling> samengesteldeWoorden =
        new BruteforceWoordenSamenstellingSolver(woordenboek,
            new WoordensamenstellingSolverConfiguration.Builder().metWoordLengte(4).build())
                .samengesteldeWoorden().collect(Collectors.toSet());

    assertThat(samengesteldeWoorden).containsExactlyInAnyOrder(
        new Woordensamenstelling(List.of(new Woord("AB"), new Woord("CD"))));
  }
}
