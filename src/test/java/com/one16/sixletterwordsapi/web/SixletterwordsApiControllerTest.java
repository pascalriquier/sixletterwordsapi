package com.one16.sixletterwordsapi.web;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.one16.sixletterwordsapi.domain.dictionary.InMemoryWoordenboek;
import com.one16.sixletterwordsapi.domain.dictionary.Woord;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class SixletterwordsApiControllerTest {

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
        .andExpect(content().string(containsString("AB+CD=ABCD")))
        .andExpect(content().string(containsString("foo+bar=foobar")));
  }

  private MockMultipartFile toInputFile(InMemoryWoordenboek woordenboek) {
    return new MockMultipartFile("file",
        woordenboek.alleWoorden().map(Woord::tekst).collect(Collectors.joining("\r\n")).getBytes());
  }

}
