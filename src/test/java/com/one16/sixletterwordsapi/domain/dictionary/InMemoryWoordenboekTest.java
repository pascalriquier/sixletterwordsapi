package com.one16.sixletterwordsapi.domain.dictionary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class InMemoryWoordenboekTest {

  @Test
  void kanNietGemaaktWordenMetNullLijstVanWoorden() {
    assertThatThrownBy(() -> new InMemoryWoordenboek(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void kanAantalWoordenTeruggeven() {
    assertThat(new InMemoryWoordenboek(Set.of(new Woord("A"), new Woord("B"), new Woord("C")))
        .aantalWoorden()).isEqualTo(3);
  }

  @Test
  void kanTeruggevenOfWoordenboekWoordBevat() {
    InMemoryWoordenboek woordenboek = new InMemoryWoordenboek(Set.of(new Woord("A"), new Woord("B"), new Woord("C")));
    assertThat(woordenboek.bevat(new Woord("A"))).isTrue();
    assertThat(woordenboek.bevat(new Woord("D"))).isFalse();
  }
  
  @Test
  void kanStreamVanAlleWoordenTeruggeven() {
    Set<Woord> woorden = Set.of(new Woord("A"), new Woord("B"), new Woord("C"));
    InMemoryWoordenboek woordenboek = new InMemoryWoordenboek(woorden);
    assertThat(woordenboek.alleWoorden().collect(Collectors.toSet())).isEqualTo(woorden);
  }
  
}
