package com.one16.sixletterwordsapi.domain.dictionary;

import java.util.stream.Stream;

public interface Woordenboek {

  boolean bevat(Woord woord);

  long aantalWoorden();

  Stream<Woord> alleWoorden();

}
