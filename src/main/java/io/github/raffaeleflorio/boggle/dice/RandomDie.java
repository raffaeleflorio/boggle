package io.github.raffaeleflorio.boggle.dice;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A {@link Die} backed by {@link Random}
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class RandomDie<T> implements Die<T> {
  /**
   * Builds an unbounded non cryptographically strong random die with zero as minimum and initial value
   *
   * @param sidesFn The function used to map an integer to a side
   * @since 1.0.0
   */
  public RandomDie(final Function<Integer, T> sidesFn) {
    this(sidesFn, 0);
  }

  /**
   * Builds an unbounded non cryptographically strong die
   *
   * @param sidesFn The function used to map an integer to a side
   * @param min     The minimum and initial value
   * @since 1.0.0
   */
  public RandomDie(final Function<Integer, T> sidesFn, final Integer min) {
    this(sidesFn, min, Integer.MAX_VALUE);
  }

  /**
   * Builds a bounded non cryptographically strong die
   *
   * @param sidesFn The function used to map an integer to a side
   * @param min     The minimum and initial value
   * @param bound   The bound value
   * @since 1.0.0
   */
  public RandomDie(final Function<Integer, T> sidesFn, final Integer min, final Integer bound) {
    this(sidesFn, min, bound, ThreadLocalRandom.current());
  }

  /**
   * Builds a bounded die
   *
   * @param sidesFn The function used to map an integer to a side
   * @param min     The initial value
   * @param bound   The bound
   * @param random  The random source
   * @since 1.0.0
   */
  public RandomDie(
    final Function<Integer, T> sidesFn,
    final Integer min,
    final Integer bound,
    final Random random
  ) {
    this(sidesFn, min, () -> random.nextInt(bound - min) + min);
  }

  private RandomDie(
    final Function<Integer, T> sidesFn,
    final Integer initial,
    final Supplier<Integer> nexFn
  ) {
    this.sidesFn = sidesFn;
    this.initial = initial;
    this.nextFn = nexFn;
  }

  @Override
  public T value() {
    return sidesFn.apply(initial);
  }

  @Override
  public Die<T> rolled() {
    return new RandomDie<>(sidesFn, nextFn.get(), nextFn);
  }

  private final Function<Integer, T> sidesFn;
  private final Integer initial;
  private final Supplier<Integer> nextFn;
}
