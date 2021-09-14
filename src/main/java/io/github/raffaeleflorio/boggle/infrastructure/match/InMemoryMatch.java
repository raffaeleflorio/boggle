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
import io.github.raffaeleflorio.boggle.domain.description.SimpleDescription;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import io.github.raffaeleflorio.boggle.domain.match.Match;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheets;
import io.github.raffaeleflorio.boggle.infrastructure.sheet.InMemorySheets;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * In memory {@link Match} implementation
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
final class InMemoryMatch<T> implements Match<T> {
  /**
   * Builds an in memory match
   *
   * @param description The description
   * @param grid        The grid
   * @since 1.0.0
   */
  InMemoryMatch(final Description description, final Grid<T> grid) {
    this(description, grid, new InMemorySheets<>(), new ConcurrentHashMap<>(), new SimpleDescription());
  }

  /**
   * Builds an in memory match
   *
   * @param description The description
   * @param grid        The grid
   * @param sheets      The sheets
   * @param map         The backed map
   * @param empty       The empty description
   * @since 1.0.0
   */
  InMemoryMatch(
    final Description description,
    final Grid<T> grid,
    final Sheets<T> sheets,
    final ConcurrentMap<UUID, UUID> map,
    final Description empty
  ) {
    this.description = description;
    this.grid = grid;
    this.sheets = sheets;
    this.map = map;
    this.empty = empty;
  }

  @Override
  public UUID id() {
    return UUID.fromString(description.feature("id").get(0).toString());
  }

  @Override
  public Uni<Sheet<T>> sheet(final UUID id) {
    return Uni.createFrom().item(map.get(id))
      .onItem().ifNotNull().transformToUni(sheets::sheet)
      .onItem().ifNull().switchTo(() -> sheets.sheet(empty))
      .onItem().invoke(sheet -> map.put(id, sheet.id()));
  }

  @Override
  public Multi<Map.Entry<UUID, Integer>> score() {
    return Multi.createFrom().failure(new IllegalStateException("Unable to build score"));
  }

  @Override
  public Description description() {
    return description;
  }

  @Override
  public Uni<Grid<T>> grid() {
    return Uni.createFrom().item(grid);
  }

  @Override
  public Multi<UUID> players() {
    return Multi.createFrom().items(map.keySet()::stream);
  }

  private final Description description;
  private final Grid<T> grid;
  private final Sheets<T> sheets;
  private final ConcurrentMap<UUID, UUID> map;
  private final Description empty;
}
