package com.one16.sixletterwordsapi.web;

import com.one16.sixletterwordsapi.domain.WoordensamenstellingSolver;
import com.one16.sixletterwordsapi.domain.WoordensamenstellingSolverConfiguration;
import com.one16.sixletterwordsapi.domain.WoordensamenstellingSolverConfiguration.Builder;
import com.one16.sixletterwordsapi.domain.WoordensamenstellingSolverFactory;
import com.one16.sixletterwordsapi.domain.dictionary.Woord;
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
public class WoordensamenstellingenApiController {
  private final WoordensamenstellingSolverFactory woordensamenstellingSolverFactory;

  public WoordensamenstellingenApiController(
      WoordensamenstellingSolverFactory woordensamenstellingSolverFactory) {
    this.woordensamenstellingSolverFactory = woordensamenstellingSolverFactory;
  }

  @PostMapping(path = "/woordsamenstellingen", consumes = "multipart/form-data",
      produces = "text/plain")
  @ResponseBody
  public String woordsamenstellingen(@RequestParam("file") MultipartFile bestand,
      HttpServletResponse response,
      @RequestParam(name = "lengte", required = false) Optional<Integer> lengteParameter,
      @RequestParam(name = "aantalWoorden", required = false) Optional<Integer> aantalWoordenParameter)
      throws IOException {

    Builder configurationBuilder =
        new WoordensamenstellingSolverConfiguration.Builder();
    lengteParameter.ifPresent(configurationBuilder::metWoordLengte);
    aantalWoordenParameter.ifPresent(configurationBuilder::metAantalWoorden);
    
    WoordensamenstellingSolver woordsamenstellingenSolver = woordensamenstellingSolverFactory
        .solver(new InputStreamResource(bestand.getInputStream()), configurationBuilder.build());

    return woordsamenstellingenSolver.samengesteldeWoorden().map(WoordensamenstellingDTO::new)
        .map(WoordensamenstellingDTO::oplossing).collect(Collectors.joining("\n"));
  }

  private static record WoordensamenstellingDTO(Woordensamenstelling samenstelling) {
    public String oplossing() {
      return samenstelling.onderdelen().stream().map(Woord::tekst).collect(Collectors.joining("+"))
          + "=" + samenstelling.tekst();
    }
  }
}
