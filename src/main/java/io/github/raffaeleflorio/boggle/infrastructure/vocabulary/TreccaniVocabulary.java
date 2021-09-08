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
