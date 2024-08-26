package com.one16.sixletterwordsapi.domain.dictionary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class WoordensamenstellingTest {

  @Test
  void kanGeenWoordenSamenstellenMetNullLijst() {
    assertThatThrownBy(() -> new Woordensamenstelling(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void kanGeenWoordenSamenstellenMetLegeLijstVanWoorden() {
    assertThatThrownBy(() -> new Woordensamenstelling(List.of()))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void kanGeenWoordenSamenstellenMetLijstVan1Woord() {
    assertThatThrownBy(() -> new Woordensamenstelling(List.of(new Woord("foo"))))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void kanGeenWoordenSamenstellenMetNullWoorden() {
    assertThatThrownBy(() -> new Woordensamenstelling(Stream.of(new Woord("foo"), null).toList()))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void samengesteldWoordIsConcatenatieVanDeOnderdelen() {
    assertThat(new Woordensamenstelling(new Woord("foo"), new Woord("bar")).woord())
        .isEqualTo(new Woord("foobar"));
  }

}
