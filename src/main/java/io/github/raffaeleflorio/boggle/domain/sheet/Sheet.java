package io.github.raffaeleflorio.boggle.domain.sheet;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.UUID;

/**
 * A word sheet
 *
 * @param <T> The word type
 * @since 1.0.0
 */
public interface Sheet<T> {
  /**
   * Builds the sheet id
   *
   * @return The id
   * @since 1.0.0
   */
  UUID id();

  /**
   * Builds asynchronously the words
   *
   * @return The words
   * @since 1.0.0
   */
  Multi<Dice<T>> words();

  /**
   * Builds asynchronously the unique words
   *
   * @param other The sheet to compare
   * @return The words
   * @since 1.0.0
   */
  Multi<Dice<T>> words(Sheet<T> other);

  /**
   * Writes asynchronously a new word
   *
   * @param word The word
   * @return Emitter of completion or failure
   * @since 1.0.0
   */
  Uni<Void> word(Dice<T> word);
}
