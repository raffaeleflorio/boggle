/*
   Copyright 2021 Raffaele Florio

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package io.github.raffaeleflorio.boggle.infrastructure.vocabulary;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.vocabulary.Vocabulary;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.ext.web.client.WebClient;

/**
 * A {@link io.github.raffaeleflorio.boggle.domain.vocabulary.Vocabulary} backed by Treccani (https://www.treccani.it)
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class TreccaniVocabulary implements Vocabulary<CharSequence> {
  /**
   * Builds a vocabulary
   *
   * @param webClient The webclient
   * @since 1.0.0
   */
  public TreccaniVocabulary(final WebClient webClient) {
    this(webClient, "https://www.treccani.it/vocabolario/");
  }

  /**
   * Builds a vocabulary
   *
   * @param webClient The webclient
   * @param baseUrl   The base url
   * @since 1.0.0
   */
  TreccaniVocabulary(final WebClient webClient, final String baseUrl) {
    this(
      new HttpVocabulary(
        webClient,
        baseUrl.concat("%s/"),
        "La tua ricerca non ha prodotto risultati in nessun documento"
      )
    );
  }

  private TreccaniVocabulary(final Vocabulary<CharSequence> origin) {
    this.origin = origin;
  }

  @Override
  public Uni<Boolean> contains(final Dice<CharSequence> word) {
    return origin.contains(word);
  }

  private final Vocabulary<CharSequence> origin;
}
