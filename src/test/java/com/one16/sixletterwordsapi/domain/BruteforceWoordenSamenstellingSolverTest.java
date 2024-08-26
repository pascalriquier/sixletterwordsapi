package com.one16.sixletterwordsapi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.one16.sixletterwordsapi.domain.dictionary.InMemoryWoordenboek;
import com.one16.sixletterwordsapi.domain.dictionary.InMemoryWoordenboekFactory;
import com.one16.sixletterwordsapi.domain.dictionary.Woord;
import com.one16.sixletterwordsapi.domain.dictionary.Woordenboek;
import com.one16.sixletterwordsapi.domain.dictionary.Woordensamenstelling;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

class BruteforceWoordenSamenstellingSolverTest {

  @Test
  void kanWoordensamenstellingenUitGegevenWoordenboekBepalen() {
    InMemoryWoordenboek woordenboek =
        new InMemoryWoordenboek(Set.of(new Woord("AB"), new Woord("ABCD"), new Woord("CD"),
            new Woord("foobar"), new Woord("bar"), new Woord("foo")));

    Set<Woordensamenstelling> samengesteldeWoorden =
        new BruteforceWoordenSamenstellingSolver(woordenboek).samengesteldeWoorden();

    assertThat(samengesteldeWoorden).containsExactlyInAnyOrder(
        new Woordensamenstelling(List.of(new Woord("AB"), new Woord("CD"))),
        new Woordensamenstelling(List.of(new Woord("foo"), new Woord("bar"))));
  }

  @Test
  void kanWoordensamenstellingenUitWoordenboekVanOne16() {
    Woordenboek woordenboek =
        new InMemoryWoordenboekFactory().create(new ClassPathResource("input.txt"));

    Set<Woordensamenstelling> samengesteldeWoorden =
        new BruteforceWoordenSamenstellingSolver(woordenboek).samengesteldeWoorden();

    samengesteldeWoorden.forEach(samenstelling -> {
      System.out.println(
          samenstelling.onderdelen().stream().map(Woord::tekst).collect(Collectors.joining("+"))
              + "=" + samenstelling.woord().tekst());
    });
  }

}
