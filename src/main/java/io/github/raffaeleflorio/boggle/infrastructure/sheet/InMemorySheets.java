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
package io.github.raffaeleflorio.boggle.infrastructure.sheet;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheets;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * In memory {@link Sheets} implementation
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class InMemorySheets<T> implements Sheets<T> {
  /**
   * Builds an empty in memory repo
   *
   * @since 1.0.0
   */
  public InMemorySheets() {
    this(new ConcurrentHashMap<>());
  }

  /**
   * Builds an in memory repo
   *
   * @param map The backed map
   * @since 1.0.0
   */
  InMemorySheets(final ConcurrentMap<UUID, Sheet<T>> map) {
    this(map, UUID::randomUUID);
  }

  /**
   * Builds an in memory repo
   *
   * @param map      The backed map
   * @param randomId The supplier of random UUID
   * @since 1.0.0
   */
  InMemorySheets(final ConcurrentMap<UUID, Sheet<T>> map, final Supplier<UUID> randomId) {
    this(map, randomId, InMemorySheet::new);
  }

  /**
   * Builds an in memory repo
   *
   * @param map      The backed map
   * @param randomId The supplier of random UUID
   * @param sheetFn  The function to build a sheet
   * @since 1.0.0
   */
  InMemorySheets(
    final ConcurrentMap<UUID, Sheet<T>> map,
    final Supplier<UUID> randomId,
    final Function<Description, Sheet<T>> sheetFn
  ) {
    this.map = map;
    this.randomId = randomId;
    this.sheetFn = sheetFn;
  }

  @Override
  public Uni<Sheet<T>> sheet(final Description description) {
    var id = randomId.get();
    var sheet = sheetFn.apply(
      description.feature("id", List.of(id.toString()))
    );
    map.put(id, sheet);
    return Uni.createFrom().item(sheet);
  }

  @Override
  public Uni<Sheet<T>> sheet(final UUID id) {
    return Uni
      .createFrom()
      .item(map.get(id));
  }

  private final ConcurrentMap<UUID, Sheet<T>> map;
  private final Supplier<UUID> randomId;
  private final Function<Description, Sheet<T>> sheetFn;
}
