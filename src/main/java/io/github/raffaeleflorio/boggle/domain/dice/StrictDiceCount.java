package io.github.raffaeleflorio.boggle.domain.dice;

import java.util.List;
import java.util.function.BiFunction;

/**
 * A {@link Dice} decorator that verify lazily the dice count
 *
 * @param <T> The die mark type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class StrictDiceCount<T> implements ValidatedDice<T> {
  /**
   * Builds a strict dice number
   *
   * @param origin   The dice to decorate
   * @param expected The expected count
   * @since 1.0.0
   */
  public StrictDiceCount(final Dice<T> origin, final Integer expected) {
    this(
      origin,
      expected,
      (x, y) -> new IllegalStateException(
        String.format("Expected a dice of count %s, but was %s", x, y)
      )
    );
  }

  /**
   * Builds a strict dice number
   *
   * @param origin      The dice to decorate
   * @param expected    The expected count
   * @param exceptionFn The function used to build the exception
   * @since 1.0.0
   */
  public StrictDiceCount(
    final Dice<T> origin,
    final Integer expected,
    final BiFunction<Integer, Integer, RuntimeException> exceptionFn
  ) {
    this.origin = origin;
    this.expected = expected;
    this.exceptionFn = exceptionFn;
  }

  @Override
  public List<T> values() {
    var values = origin.values();
    if (expected.equals(values.size())) {
      return values;
    }
    throw exceptionFn.apply(expected, values.size());
  }

  @Override
  public Dice<T> shuffled() {
    return new StrictDiceCount<>(origin.shuffled(), expected, exceptionFn);
  }

  private final Dice<T> origin;
  private final Integer expected;
  private final BiFunction<Integer, Integer, RuntimeException> exceptionFn;
}
