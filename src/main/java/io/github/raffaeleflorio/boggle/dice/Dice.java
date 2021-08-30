package io.github.raffaeleflorio.boggle.dice;

import java.util.Collection;
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
  Collection<T> values();

  /**
   * Shuffles the dice
   *
   * @return The shuffled dice
   * @since 1.0.0
   */
  Dice<T> shuffled();

  /**
   * A {@link Dice} useful for testing purpose
   *
   * @param <T> The mark side type
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  final class Fake<T> implements Dice<T> {
    /**
     * Builds a fake
     *
     * @param values The values
     * @since 1.0.0
     */
    public Fake(final Collection<T> values) {
      this(values, Function.identity());
    }

    /**
     * Builds a fake
     *
     * @param values The values
     * @param nextFn The function used to builds the next value
     * @since 1.0.0
     */
    public Fake(final Collection<T> values, final Function<Collection<T>, Collection<T>> nextFn) {
      this.values = values;
      this.nextFn = nextFn;
    }

    @Override
    public Collection<T> values() {
      return values;
    }

    @Override
    public Dice<T> shuffled() {
      return new Dice.Fake<>(nextFn.apply(values), nextFn);
    }

    private final Collection<T> values;
    private final Function<Collection<T>, Collection<T>> nextFn;
  }
}
