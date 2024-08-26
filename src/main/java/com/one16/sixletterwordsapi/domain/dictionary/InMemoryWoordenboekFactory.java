package com.one16.sixletterwordsapi.domain.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;

public class InMemoryWoordenboekFactory {
  public Woordenboek create(Resource resource) {
    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
      Set<Woord> words = bufferedReader.lines()
          .map(String::trim)
          .filter(s -> !s.isBlank())
          .map(Woord::new)
          .collect(Collectors.toSet());
      return new InMemoryWoordenboek(words);
    } catch (IOException e) {
      throw new IllegalArgumentException(resource + "is geen geldige woordenlijst");
    }
    
  }
}
