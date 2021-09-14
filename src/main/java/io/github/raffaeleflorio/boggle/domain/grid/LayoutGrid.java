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
package io.github.raffaeleflorio.boggle.domain.grid;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A {@link Grid} with layout feature
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class LayoutGrid<T> implements Grid<T> {
  /**
   * Builds a grid
   *
   * @param origin The grid to decorate
   * @since 1.0.0
   */
  public LayoutGrid(final Grid<T> origin) {
    this(origin, Object::toString);
  }

  /**
   * Builds a grid with
   *
   * @param origin         The grid to decorate
   * @param charSequenceFn The function to build char sequence from word type
   * @since 1.0.0
   */
  public LayoutGrid(final Grid<T> origin, final Function<T, CharSequence> charSequenceFn) {
    this.origin = origin;
    this.charSequenceFn = charSequenceFn;
  }

  @Override
  public List<T> values() {
    return origin.values();
  }

  @Override
  public Grid<T> shuffled() {
    return new LayoutGrid<>(origin.shuffled(), charSequenceFn);
  }

  @Override
  public Boolean compatible(final Dice<T> word) {
    return origin.compatible(word);
  }

  @Override
  public Description description() {
    return origin.description().feature("layout", layout());
  }

  private List<CharSequence> layout() {
    return origin.values().stream().map(charSequenceFn).collect(Collectors.toUnmodifiableList());
  }

  private final Grid<T> origin;
  private final Function<T, CharSequence> charSequenceFn;
}
