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
