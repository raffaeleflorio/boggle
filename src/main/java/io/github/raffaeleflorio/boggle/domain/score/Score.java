package io.github.raffaeleflorio.boggle.domain.score;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.smallrye.mutiny.Uni;

import java.util.function.Function;

/**
 * An asynchronous score function
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Score<T> {
  /**
   * Builds asynchronously a non-negative score
   *
   * @param word The word
   * @return The score emitter
   * @since 1.0.0
   */
  Uni<Integer> score(Dice<T> word);

  /**
   * A {@link Score} useful for testing
   *
   * @param <T> The word type
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  final class Fake<T> implements Score<T> {
    /**
     * Builds a fake that always emits zero
     *
     * @since 1.0.0
     */
    public Fake() {
      this(x -> 0);
    }

    /**
     * Builds a fake with a custom score function
     *
     * @param scoreFn The score function
     * @since 1.0.0
     */
    public Fake(final Function<Dice<T>, Integer> scoreFn) {
      this.scoreFn = scoreFn;
    }

    @Override
    public Uni<Integer> score(final Dice<T> word) {
      return Uni.createFrom().item(scoreFn.apply(word));
    }

    private final Function<Dice<T>, Integer> scoreFn;
  }
}
