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

import java.security.SecureRandom;
import java.util.function.Function;

/**
 * A cryptographically strong die
 *
 * @param <T> The mark type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class CryptoDie<T> implements Die<T> {
  /**
   * Builds an unbounded cryptographically strong random die with zero as minimum and initial value
   *
   * @param sidesFn The function used to map an integer to a side
   * @since 1.0.0
   */
  public CryptoDie(final Function<Integer, T> sidesFn) {
    this(sidesFn, 0);
  }

  /**
   * Builds an unbounded cryptographically strong die
   *
   * @param sidesFn The function used to map an integer to a side
   * @param min     The minimum and initial value
   * @since 1.0.0
   */
  public CryptoDie(final Function<Integer, T> sidesFn, final Integer min) {
    this(sidesFn, min, Integer.MAX_VALUE);
  }

  /**
   * Builds a bounded cryptographically strong die
   *
   * @param sidesFn The function used to map an integer to a side
   * @param min     The minimum and initial value
   * @param bound   The bound value
   * @since 1.0.0
   */
  public CryptoDie(final Function<Integer, T> sidesFn, final Integer min, final Integer bound) {
    this(new RandomDie<>(sidesFn, min, bound, new SecureRandom()));
  }

  private CryptoDie(final Die<T> origin) {
    this.origin = origin;
  }

  @Override
  public T value() {
    return origin.value();
  }

  @Override
  public Die<T> rolled() {
    return origin.rolled();
  }

  private final Die<T> origin;
}
