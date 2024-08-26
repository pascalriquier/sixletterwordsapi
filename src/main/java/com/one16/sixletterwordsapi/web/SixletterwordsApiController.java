package com.one16.sixletterwordsapi.web;

import com.one16.sixletterwordsapi.domain.BruteforceWoordenSamenstellingSolver;
import com.one16.sixletterwordsapi.domain.WoordensamenstellingSolver;
import com.one16.sixletterwordsapi.domain.dictionary.InMemoryWoordenboekFactory;
import com.one16.sixletterwordsapi.domain.dictionary.Woord;
import com.one16.sixletterwordsapi.domain.dictionary.Woordenboek;
import com.one16.sixletterwordsapi.domain.dictionary.Woordensamenstelling;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SixletterwordsApiController {

  @PostMapping(path = "/woordsamenstellingen", consumes = "multipart/form-data",
      produces = "text/plain")
  @ResponseBody
  public String woordsamenstellingen(@RequestParam("file") MultipartFile bestand,
      HttpServletResponse response,
      @RequestParam(name = "lengte", required = false) Optional<Integer> lengte)
      throws IOException {
    WoordensamenstellingSolver woordsamenstellingenSolver = solver(bestand, lengte);
    return woordsamenstellingenSolver.samengesteldeWoorden().stream()
        .map(WoordensamenstellingDTO::new).map(WoordensamenstellingDTO::oplossing)
        .collect(Collectors.joining("\n"));
  }

  private WoordensamenstellingSolver solver(MultipartFile bestand, Optional<Integer> lengte)
      throws IOException {
    Woordenboek woordenboek =
        new InMemoryWoordenboekFactory().create(new InputStreamResource(bestand.getInputStream()));
    BruteforceWoordenSamenstellingSolver solver =
        new BruteforceWoordenSamenstellingSolver(woordenboek);
    return lengte.map(l -> solver.metWoordsamenstellingenDieVoldoenAan(
        samenstelling -> samenstelling.woord().heeftLengte(l))).orElse(solver);
  }

  private static record WoordensamenstellingDTO(Woordensamenstelling samenstelling) {
    public String oplossing() {
      return samenstelling.onderdelen().stream().map(Woord::tekst).collect(Collectors.joining("+"))
          + "=" + samenstelling.woord().tekst();
    }
  }
}
