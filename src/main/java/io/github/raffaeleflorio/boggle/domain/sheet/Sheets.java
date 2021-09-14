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
package io.github.raffaeleflorio.boggle.domain.sheet;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.smallrye.mutiny.Uni;

import java.util.UUID;
import java.util.function.Function;

/**
 * {@link Sheet} repository
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Sheets<T> {
  /**
   * Builds asynchronously a new sheet by its description
   *
   * @param description The sheet description
   * @return The sheet or null if not found
   * @since 1.0.0
   */
  Uni<Sheet<T>> sheet(Description description);

  /**
   * Builds asynchronously a sheet from its id
   *
   * @param id The sheet id
   * @return The sheet or null if not found
   * @since 1.0.0
   */
  Uni<Sheet<T>> sheet(UUID id);

  /**
   * A {@link Sheets} useful for testing
   *
   * @param <T> The word type
   * @since 1.0.0
   */
  final class Fake<T> implements Sheets<T> {
    /**
     * Builds a fake without elements
     *
     * @since 1.0.0
     */
    public Fake() {
      this(x -> Uni.createFrom().nullItem(), x -> Uni.createFrom().nullItem());
    }

    /**
     * Builds a fake
     *
     * @param sheetFn    The function to build existing sheet
     * @param newSheetFn The function to build new sheet
     * @since 1.0.0
     */
    public Fake(
      final Function<UUID, Uni<Sheet<T>>> sheetFn,
      final Function<Description, Uni<Sheet<T>>> newSheetFn
    ) {
      this.sheetFn = sheetFn;
      this.newSheetFn = newSheetFn;
    }

    @Override
    public Uni<Sheet<T>> sheet(final Description description) {
      return newSheetFn.apply(description);
    }

    @Override
    public Uni<Sheet<T>> sheet(final UUID id) {
      return sheetFn.apply(id);
    }

    private final Function<UUID, Uni<Sheet<T>>> sheetFn;
    private final Function<Description, Uni<Sheet<T>>> newSheetFn;
  }
}
