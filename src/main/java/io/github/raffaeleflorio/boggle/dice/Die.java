package io.github.raffaeleflorio.boggle.dice;

import java.util.function.Function;

/**
 * Rollable object with marked sides
 *
 * @param <T> The mark side type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Die<T> {
  /**
   * Builds the current mark side value
   *
   * @return The current value
   * @since 1.0.0
   */
  T value();

  /**
   * Rolls the die
   *
   * @return The rolled die
   * @since 1.0.0
   */
  Die<T> rolled();

  /**
   * A {@link Die} useful for testing purpose
   *
   * @param <T> The mark side type
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  final class Fake<T> implements Die<T> {
    /**
     * Builds a constant fake
     *
     * @param value The value
     * @since 1.0.0
     */
    public Fake(final T value) {
      this(value, Function.identity());
    }

    /**
     * Builds a fake
     *
     * @param value  The value
     * @param nextFn The function to build the next value
     * @since 1.0.0
     */
    public Fake(final T value, final Function<T, T> nextFn) {
      this.value = value;
      this.nextFn = nextFn;
    }

    @Override
    public T value() {
      return value;
    }

    @Override
    public Die<T> rolled() {
      return new Die.Fake<>(nextFn.apply(value), nextFn);
    }

    private final T value;
    private final Function<T, T> nextFn;
  }
}
