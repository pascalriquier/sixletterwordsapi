package com.one16.sixletterwordsapi.domain.dictionary;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class WoordTest {

  @Test
  void kanWoordSamenstellenMetAnderWoord() {
    Woord eersteWoord = new Woord("foo");
    Woord tweedeWoord = new Woord("bar");
    assertThat(eersteWoord.samengesteldMet(tweedeWoord))
    .isEqualTo(new Woordensamenstelling(List.of(eersteWoord, tweedeWoord)));
  }
  
  @Test
  void kanBepalenOfWoordLengteHeeft() {
    assertThat(new Woord("foo").heeftLengte(3)).isTrue();
    assertThat(new Woord("foo").heeftLengte(2)).isFalse();
  }
  
}
