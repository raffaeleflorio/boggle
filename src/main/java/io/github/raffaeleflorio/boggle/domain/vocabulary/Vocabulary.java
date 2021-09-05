package io.github.raffaeleflorio.boggle.domain.vocabulary;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.smallrye.mutiny.Uni;

import java.util.function.Function;

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

  /**
   * A {@link Vocabulary} useful for testing
   *
   * @param <T> The word type
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  final class Fake<T> implements Vocabulary<T> {
    /**
     * Builds an empty fake
     *
     * @since 1.0.0
     */
    public Fake() {
      this(x -> false);
    }

    /**
     * Builds a fake
     *
     * @param contains The function map dice to contains
     * @since 1.0.0
     */
    public Fake(final Function<Dice<T>, Boolean> contains) {
      this.contains = contains;
    }

    @Override
    public Uni<Boolean> contains(final Dice<T> word) {
      return Uni.createFrom().item(contains.apply(word));
    }

    private final Function<Dice<T>, Boolean> contains;
  }
}
