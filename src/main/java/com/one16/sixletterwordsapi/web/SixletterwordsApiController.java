package com.one16.sixletterwordsapi.web;

import com.one16.sixletterwordsapi.domain.BruteforceWoordenSamenstellingSolver;
import com.one16.sixletterwordsapi.domain.WoordensamenstellingSolver;
import com.one16.sixletterwordsapi.domain.dictionary.InMemoryWoordenboekFactory;
import com.one16.sixletterwordsapi.domain.dictionary.Woord;
import com.one16.sixletterwordsapi.domain.dictionary.Woordenboek;
import com.one16.sixletterwordsapi.domain.dictionary.Woordensamenstelling;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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
      HttpServletResponse response) throws IOException {
    Woordenboek woordenboek =
        new InMemoryWoordenboekFactory().create(new InputStreamResource(bestand.getInputStream()));
    WoordensamenstellingSolver woordsamenstellingenSolver =
        new BruteforceWoordenSamenstellingSolver(woordenboek);
    return woordsamenstellingenSolver.samengesteldeWoorden().stream()
        .map(WoordensamenstellingDTO::new)
        .map(WoordensamenstellingDTO::oplossing)
        .collect(Collectors.joining("\n"));
  }

  private static record WoordensamenstellingDTO(Woordensamenstelling samenstelling) {
    public String oplossing() {
      return samenstelling.onderdelen().stream().map(Woord::tekst).collect(Collectors.joining("+"))
          + "=" + samenstelling.woord().tekst();
    }
  }
}
