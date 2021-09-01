package io.github.raffaeleflorio.boggle.grid;

import io.github.raffaeleflorio.boggle.dice.Dice;

import java.util.Map;
import java.util.function.Function;

/**
 * A boggle grid
 *
 * @param <T> The grid dice mark type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Grid<T> {
  /**
   * Shuffles the grid
   *
   * @return The shuffled grid
   * @since 1.0.0
   */
  Grid<T> shuffled();

  /**
   * Builds a non-negative score of a word
   *
   * @param word The word
   * @return The score
   * @since 1.0.0
   */
  Integer score(Dice<T> word);

  /**
   * Builds the description
   *
   * @return The description
   * @since 1.0.0
   */
  Map<CharSequence, CharSequence> description();

  /**
   * A {@link Grid} useful for testing purpose
   *
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  final class Fake<T> implements Grid<T> {
    /**
     * Builds a fake
     *
     * @since 1.0.0
     */
    public Fake() {
      this(x -> 0);
    }

    /**
     * Builds a fake
     *
     * @param scoreFn The function to build score
     * @since 1.0.0
     */
    public Fake(final Function<Dice<T>, Integer> scoreFn) {
      this(scoreFn, Map.of("id", "fake grid"));
    }

    /**
     * Builds a fake
     *
     * @param scoreFn     The function to build score
     * @param description The description
     * @since 1.0.0
     */
    public Fake(
      final Function<Dice<T>, Integer> scoreFn,
      final Map<CharSequence, CharSequence> description
    ) {
      this.scoreFn = scoreFn;
      this.description = description;
    }

    @Override
    public Grid<T> shuffled() {
      return this;
    }

    @Override
    public Integer score(final Dice<T> word) {
      return scoreFn.apply(word);
    }

    @Override
    public Map<CharSequence, CharSequence> description() {
      return description;
    }

    private final Function<Dice<T>, Integer> scoreFn;
    private final Map<CharSequence, CharSequence> description;
  }
}
