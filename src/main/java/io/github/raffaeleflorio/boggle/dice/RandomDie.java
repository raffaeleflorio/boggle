package io.github.raffaeleflorio.boggle.dice;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

/**
 * A {@link Die} backed by {@link Random}
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class RandomDie<T> implements Die<T> {
  /**
   * Builds an unbounded non cryptographically strong random die with zero as initial side value
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
   * @param initial The initial side value
   * @since 1.0.0
   */
  public RandomDie(final Function<Integer, T> sidesFn, final Integer initial) {
    this(sidesFn, initial, Integer.MAX_VALUE);
  }

  /**
   * Builds a bounded non cryptographically strong die
   *
   * @param sidesFn The function used to map an integer to a side
   * @param initial The initial value
   * @param bound   The bound value
   * @since 1.0.0
   */
  public RandomDie(final Function<Integer, T> sidesFn, final Integer initial, final Integer bound) {
    this(sidesFn, initial, bound, ThreadLocalRandom.current());
  }

  /**
   * Builds a die that emits bounded ints
   *
   * @param sidesFn The function used to map an integer to a side
   * @param initial The initial value
   * @param bound   The bound
   * @param random  The random source
   * @since 1.0.0
   */
  public RandomDie(
    final Function<Integer, T> sidesFn,
    final Integer initial,
    final Integer bound,
    final Random random
  ) {
    this.sidesFn = sidesFn;
    this.random = random;
    this.initial = initial;
    this.bound = bound;
  }

  @Override
  public T value() {
    return sidesFn.apply(initial);
  }

  @Override
  public Die<T> rolled() {
    return new RandomDie<>(sidesFn, random.nextInt(bound), bound, random);
  }

  private final Function<Integer, T> sidesFn;
  private final Integer initial;
  private final Integer bound;
  private final Random random;
}
