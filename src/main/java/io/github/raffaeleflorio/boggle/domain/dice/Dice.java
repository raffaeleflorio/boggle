package io.github.raffaeleflorio.boggle.domain.dice;

import java.util.List;
import java.util.function.Function;

/**
 * A group of {@link Die}
 *
 * @param <T> The die mark type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Dice<T> {
  /**
   * Builds the current marked side values
   *
   * @return The current values
   * @since 1.0.0
   */
  List<T> values();

  /**
   * Shuffles the dice
   *
   * @return The shuffled dice
   * @since 1.0.0
   */
  Dice<T> shuffled();

  /**
   * A {@link Dice} useful for testing
   *
   * @param <T> The mark side type
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  final class Fake<T> implements Dice<T> {
    /**
     * Builds an empty fake
     *
     * @since 1.0.0
     */
    public Fake() {
      this(List.of());
    }

    /**
     * Builds a fake
     *
     * @param values The values
     * @since 1.0.0
     */
    public Fake(final List<T> values) {
      this(values, Function.identity());
    }

    /**
     * Builds a fake
     *
     * @param values The values
     * @param nextFn The function used to builds the next value
     * @since 1.0.0
     */
    public Fake(final List<T> values, final Function<List<T>, List<T>> nextFn) {
      this.values = values;
      this.nextFn = nextFn;
    }

    @Override
    public List<T> values() {
      return values;
    }

    @Override
    public Dice<T> shuffled() {
      return new Dice.Fake<>(nextFn.apply(values), nextFn);
    }

    private final List<T> values;
    private final Function<List<T>, List<T>> nextFn;
  }
}
