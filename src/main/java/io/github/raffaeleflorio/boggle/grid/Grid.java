package io.github.raffaeleflorio.boggle.grid;

import java.util.Map;
import java.util.function.Function;

/**
 * A boggle grid
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Grid {
  /**
   * Shuffles the grid
   *
   * @return The shuffled grid
   * @since 1.0.0
   */
  Grid shuffled();

  /**
   * Builds a non-negative score of a word
   *
   * @param word The word
   * @return The score
   * @since 1.0.0
   */
  Integer score(CharSequence word);

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
  final class Fake implements Grid {
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
    public Fake(final Function<CharSequence, Integer> scoreFn) {
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
      final Function<CharSequence, Integer> scoreFn,
      final Map<CharSequence, CharSequence> description
    ) {
      this.scoreFn = scoreFn;
      this.description = description;
    }

    @Override
    public Grid shuffled() {
      return this;
    }

    @Override
    public Integer score(final CharSequence word) {
      return scoreFn.apply(word);
    }

    @Override
    public Map<CharSequence, CharSequence> description() {
      return description;
    }

    private final Function<CharSequence, Integer> scoreFn;
    private final Map<CharSequence, CharSequence> description;
  }
}
