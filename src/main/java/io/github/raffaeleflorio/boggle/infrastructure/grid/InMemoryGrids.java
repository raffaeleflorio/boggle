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
package io.github.raffaeleflorio.boggle.infrastructure.grid;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import io.github.raffaeleflorio.boggle.domain.grid.Grids;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.Map;
import java.util.function.Predicate;

/**
 * An in memory implementation of {@link Grids}
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class InMemoryGrids<T> implements Grids<T> {
  /**
   * Builds an in memory grids
   *
   * @param grids The description to grids map
   * @since 1.0.0
   */
  public InMemoryGrids(final Map<Predicate<Description>, Grid<T>> grids) {
    this.grids = grids;
  }

  @Override
  public Uni<Grid<T>> grid(final Description description) {
    return Multi.createFrom().iterable(grids.entrySet())
      .filter(entry -> entry.getKey().test(description)).collect().first()
      .onItem().ifNotNull().transform(Map.Entry::getValue);
  }

  private final Map<Predicate<Description>, Grid<T>> grids;
}
