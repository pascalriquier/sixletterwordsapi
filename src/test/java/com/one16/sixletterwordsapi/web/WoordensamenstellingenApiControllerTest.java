package com.one16.sixletterwordsapi.web;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.one16.sixletterwordsapi.domain.dictionary.InMemoryWoordenboek;
import com.one16.sixletterwordsapi.domain.dictionary.InMemoryWoordenboekFactory;
import com.one16.sixletterwordsapi.domain.dictionary.Woord;
import com.one16.sixletterwordsapi.domain.dictionary.Woordenboek;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.stream.Collectors;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class WoordensamenstellingenApiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void kanAlleWoordsamenstellingenUitInputbestandTerugKrijgen() throws Exception {
    InMemoryWoordenboek woordenboek =
        new InMemoryWoordenboek(Set.of(new Woord("AB"), new Woord("ABCD"), new Woord("CD"),
            new Woord("foobar"), new Woord("bar"), new Woord("foo")));

    mockMvc
        .perform(MockMvcRequestBuilders.multipart("/woordsamenstellingen")
            .file(toInputFile(woordenboek)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(content().string(containsString("AB+CD=ABCD")))
        .andExpect(content().string(containsString("foo+bar=foobar")));
  }

  @Test
  void badRequestIndienGeenBestandMeegegeven() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.multipart("/woordsamenstellingen"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void kanMeegevenDatEnkelSamenstellingenVanBepaaldeLengteWordenTeruggegeven() throws Exception {
    InMemoryWoordenboek woordenboek =
        new InMemoryWoordenboek(Set.of(new Woord("AB"), new Woord("ABCD"), new Woord("CD"),
            new Woord("foobar"), new Woord("bar"), new Woord("foo")));

    mockMvc
        .perform(MockMvcRequestBuilders.multipart("/woordsamenstellingen")
            .file(toInputFile(woordenboek)).param("lengte", "6"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(content().string(Matchers.not(containsString("AB+CD=ABCD"))))
        .andExpect(content().string(containsString("foo+bar=foobar")));
  }

  @Test
  void kanWoordensamenstellingenUitWoordenboekVanOne16()
      throws UnsupportedEncodingException, Exception {
    Woordenboek woordenboek =
        new InMemoryWoordenboekFactory().create(new ClassPathResource("input.txt"));

    String response = mockMvc.perform(
        MockMvcRequestBuilders.multipart("/woordsamenstellingen").file(toInputFile(woordenboek)))
        .andReturn().getResponse().getContentAsString();
    System.out.println(response);
  }


  private MockMultipartFile toInputFile(Woordenboek woordenboek) {
    return new MockMultipartFile("file",
        woordenboek.alleWoorden().map(Woord::tekst).collect(Collectors.joining("\r\n")).getBytes());
  }

}
