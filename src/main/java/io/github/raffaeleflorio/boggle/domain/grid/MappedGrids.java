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
import io.smallrye.mutiny.Uni;

import java.util.function.Function;

/**
 * {@link Grids} mapped according a map function
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class MappedGrids<T> implements Grids<T> {
  /**
   * Builds mapped grids
   *
   * @param origin The grids to decorate
   * @param mapFn  The map function
   * @since 1.0.0
   */
  public MappedGrids(final Grids<T> origin, final Function<Grid<T>, Grid<T>> mapFn) {
    this.origin = origin;
    this.mapFn = mapFn;
  }

  @Override
  public Uni<Grid<T>> grid(final Description description) {
    return origin.grid(description).onItem().ifNotNull().transform(mapFn);
  }

  private final Grids<T> origin;
  private final Function<Grid<T>, Grid<T>> mapFn;
}
