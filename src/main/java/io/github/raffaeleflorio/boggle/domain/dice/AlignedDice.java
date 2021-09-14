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
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Aligned {@link Dice}
 *
 * @param <T> The mark type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class AlignedDice<T> implements Dice<T> {
  /**
   * Builds an aligned dice
   *
   * @param dice The dice
   * @since 1.0.0
   */
  public AlignedDice(final List<Die<T>> dice) {
    this.dice = dice;
  }

  @Override
  public List<T> values() {
    return mappedDice(Die::value);
  }

  private <D> List<D> mappedDice(final Function<Die<T>, D> map) {
    return dice.stream().map(map).collect(Collectors.toUnmodifiableList());
  }

  @Override
  public Dice<T> shuffled() {
    return new AlignedDice<>(mappedDice(Die::rolled));
  }

  private final List<Die<T>> dice;
}
