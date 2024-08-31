package com.one16.sixletterwordsapi.domain;

import com.one16.sixletterwordsapi.domain.dictionary.InMemoryWoordenboekFactory;
import com.one16.sixletterwordsapi.domain.dictionary.Woordenboek;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

@Component
public class WoordensamenstellingSolverFactory {

  public WoordensamenstellingSolver solver(InputStreamResource resource, WoordensamenstellingSolverConfiguration config) {
    Woordenboek woordenboek = new InMemoryWoordenboekFactory().create(resource);
    return new BruteforceWoordenSamenstellingSolver(woordenboek, config);
  }

}
