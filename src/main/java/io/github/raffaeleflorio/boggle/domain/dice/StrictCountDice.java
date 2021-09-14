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

import java.util.List;
import java.util.function.BiFunction;

/**
 * A {@link ValidatedDice} according dice count
 *
 * @param <T> The mark type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class StrictCountDice<T> implements ValidatedDice<T> {
  /**
   * Builds a strict count dice
   *
   * @param origin   The dice to decorate
   * @param expected The expected count
   * @since 1.0.0
   */
  public StrictCountDice(final Dice<T> origin, final Integer expected) {
    this(
      origin,
      expected,
      (expectedCount, actualCount) -> new IllegalStateException(
        String.format("Expected a dice of count %s, but was %s", expectedCount, actualCount)
      )
    );
  }

  /**
   * Builds a strict count with a custom exception
   *
   * @param origin      The dice to decorate
   * @param expected    The expected count
   * @param exceptionFn The function to build the exception from expected and actual count
   * @since 1.0.0
   */
  public StrictCountDice(
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
    return new StrictCountDice<>(origin.shuffled(), expected, exceptionFn);
  }

  private final Dice<T> origin;
  private final Integer expected;
  private final BiFunction<Integer, Integer, RuntimeException> exceptionFn;
}
