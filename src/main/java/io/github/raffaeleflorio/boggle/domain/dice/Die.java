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

import java.util.function.Function;

/**
 * Rollable object with marked sides
 *
 * @param <T> The mark type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Die<T> {
  /**
   * Builds the current mark value
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
   * A {@link Die} useful for testing
   *
   * @param <T> The mark type
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
     * Builds a fake with an initial value and a function to build the next value
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
