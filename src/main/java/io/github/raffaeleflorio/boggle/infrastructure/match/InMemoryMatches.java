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
package io.github.raffaeleflorio.boggle.infrastructure.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import io.github.raffaeleflorio.boggle.domain.grid.Grids;
import io.github.raffaeleflorio.boggle.domain.match.Match;
import io.github.raffaeleflorio.boggle.domain.match.Matches;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * In memory {@link Matches} implementation
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class InMemoryMatches<T> implements Matches<T> {
  /**
   * Builds an empty in memory repo
   *
   * @param grids The grids
   * @since 1.0.0
   */
  public InMemoryMatches(final Grids<T> grids) {
    this(grids, new ConcurrentHashMap<>(), UUID::randomUUID, InMemoryMatch::new);
  }

  /**
   * Builds an in memory repo
   *
   * @param grids    The grids
   * @param map      The backed map
   * @param randomId The random UUID supplier
   * @param matchFn  The function to build match
   * @since 1.0.0
   */
  InMemoryMatches(
    final Grids<T> grids,
    final ConcurrentMap<UUID, Match<T>> map,
    final Supplier<UUID> randomId,
    final BiFunction<Description, Grid<T>, Match<T>> matchFn
  ) {
    this.grids = grids;
    this.map = map;
    this.randomId = randomId;
    this.matchFn = matchFn;
  }

  @Override
  public Uni<Match<T>> match(final Description description) {
    var id = randomId.get();
    return grids
      .grid(description)
      .onItem().ifNotNull().transform(Grid::shuffled)
      .onItem().ifNotNull().transform(grid -> matchFn.apply(
          description.feature("id", List.of(id.toString())),
          grid
        )
      )
      .onItem().ifNotNull().invoke(match -> map.put(id, match));
  }

  @Override
  public Uni<Match<T>> match(final UUID id) {
    return Uni.createFrom().item(map.get(id));
  }

  private final Grids<T> grids;
  private final ConcurrentMap<UUID, Match<T>> map;
  private final Supplier<UUID> randomId;
  private final BiFunction<Description, Grid<T>, Match<T>> matchFn;
}
