package com.one16.sixletterwordsapi.domain;

import com.one16.sixletterwordsapi.domain.dictionary.Woordensamenstelling;
import java.util.stream.Stream;

public interface WoordensamenstellingSolver {

  Stream<Woordensamenstelling> samengesteldeWoorden();

}
