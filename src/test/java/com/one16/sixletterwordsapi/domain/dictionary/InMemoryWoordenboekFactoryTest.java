package com.one16.sixletterwordsapi.domain.dictionary;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;

class InMemoryWoordenboekFactoryTest {

  @Test
  void kanInMemoryDictionaryMakenUitBestandMetWoorden() {
    Woordenboek dictionary = new InMemoryWoordenboekFactory().create(new ByteArrayResource("""
            foo
            bar
            foobar
            """.getBytes()));
    assertThat(dictionary.bevat(new Woord("foo"))).isTrue();
    assertThat(dictionary.bevat(new Woord("bar"))).isTrue();
    assertThat(dictionary.bevat(new Woord("foobar"))).isTrue();
    assertThat(dictionary.aantalWoorden()).isEqualTo(3);
  }

  @Test
  void legeLijnenInBestandWordenGenegeerd() {
    Woordenboek dictionary = new InMemoryWoordenboekFactory().create(new ByteArrayResource("""
            foo

            bar
            """.getBytes()));
    assertThat(dictionary.bevat(new Woord("foo"))).isTrue();
    assertThat(dictionary.bevat(new Woord("bar"))).isTrue();
    assertThat(dictionary.aantalWoorden()).isEqualTo(2);
  }
  
  @Test
  void whitespaceWordtGetrimmed() {
    Woordenboek dictionary = new InMemoryWoordenboekFactory().create(new ByteArrayResource("""
            foo
              bar  
            """.getBytes()));
    assertThat(dictionary.bevat(new Woord("foo"))).isTrue();
    assertThat(dictionary.bevat(new Woord("bar"))).isTrue();
    assertThat(dictionary.aantalWoorden()).isEqualTo(2);
  }
  
}
