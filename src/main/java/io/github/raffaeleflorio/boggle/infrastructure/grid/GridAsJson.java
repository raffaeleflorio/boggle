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
import io.vertx.core.json.JsonObject;

/**
 * {@link Grid} as JSON
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class GridAsJson extends JsonObject {
  /**
   * Builds the json
   *
   * @param grid The grid
   * @since 1.0.0
   */
  public GridAsJson(final Grid<?> grid) {
    this(grid.description());
  }

  private GridAsJson(final Description description) {
    super(
      new JsonObject()
        .put("lang", description.feature("lang").get(0))
        .put("size", description.feature("size").get(0))
        .put("layout", description.feature("layout"))
        .toBuffer()
    );
  }
}
