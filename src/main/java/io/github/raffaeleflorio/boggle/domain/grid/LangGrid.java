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

/**
 * A {@link Grid} with lang feature
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class LangGrid<T> implements Grid<T> {
  /**
   * Builds a grid
   *
   * @param origin The grid to decorate
   * @param lang   The language
   * @since 1.0.0
   */
  public LangGrid(final Grid<T> origin, final CharSequence lang) {
    this.origin = origin;
    this.lang = lang;
  }

  @Override
  public List<T> values() {
    return origin.values();
  }

  @Override
  public Grid<T> shuffled() {
    return new LangGrid<>(origin.shuffled(), lang);
  }

  @Override
  public Boolean compatible(final Dice<T> word) {
    return origin.compatible(word);
  }

  @Override
  public Description description() {
    return origin.description().feature("lang", List.of(lang));
  }

  private final Grid<T> origin;
  private final CharSequence lang;
}
