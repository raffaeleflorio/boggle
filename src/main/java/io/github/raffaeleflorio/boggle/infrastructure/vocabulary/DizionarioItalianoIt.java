package io.github.raffaeleflorio.boggle.infrastructure.vocabulary;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.vocabulary.Vocabulary;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.ext.web.client.WebClient;

/**
 * A {@link Vocabulary} backed by Dizionario Italiano (https://www.dizionario-italiano.it)
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class DizionarioItalianoIt implements Vocabulary<CharSequence> {
  /**
   * Builds a vocabulary
   *
   * @param webClient The webclient
   * @since 1.0.0
   */
  public DizionarioItalianoIt(final WebClient webClient) {
    this(
      new HttpVocabulary(
        webClient,
        "https://www.dizionario-italiano.it/dizionario-italiano.php?parola=%s",
        "non ha prodotto alcun risultato."
      )
    );
  }

  private DizionarioItalianoIt(final Vocabulary<CharSequence> origin) {
    this.origin = origin;
  }

  @Override
  public Uni<Boolean> contains(final Dice<CharSequence> word) {
    return origin.contains(word);
  }

  private final Vocabulary<CharSequence> origin;
}
