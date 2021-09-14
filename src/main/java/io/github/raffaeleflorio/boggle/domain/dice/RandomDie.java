/*
   Copyright 2021 Raffaele Florio

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package io.github.raffaeleflorio.boggle.domain.dice;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A {@link Die} backed by {@link Random}
 *
 * @param <T> The mark type
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
   * Builds an unbounded non cryptographically strong die with a custom minimum and initial value
   *
   * @param sidesFn The function used to map an integer to a side
   * @param min     The minimum and initial value
   * @since 1.0.0
   */
  public RandomDie(final Function<Integer, T> sidesFn, final Integer min) {
    this(sidesFn, min, Integer.MAX_VALUE);
  }

  /**
   * Builds a bounded non cryptographically strong die with a custom minimum and initial value
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
   * Builds a bounded die with a custom {@link Random} and a minimum and initial value
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
