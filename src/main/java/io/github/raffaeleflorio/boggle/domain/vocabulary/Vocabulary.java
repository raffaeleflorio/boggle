package io.github.raffaeleflorio.boggle.domain.vocabulary;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.smallrye.mutiny.Uni;

/**
 * Set of words
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Vocabulary<T> {
  /**
   * Builds an asynchronous boolean. True if the vocabulary contains the specified word
   *
   * @param word The word
   * @return The asynchronous boolean
   * @since 1.0.0
   */
  Uni<Boolean> contains(Dice<T> word);
}
