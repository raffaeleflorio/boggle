package io.github.raffaeleflorio.boggle.infrastructure.grid;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import io.vertx.core.json.JsonObject;

/**
 * {@link Grid} {@link Description} as JSON
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class GridDescriptionAsJson extends JsonObject {
  public GridDescriptionAsJson(final Description description) {
    super(
      new JsonObject()
        .put("lang", description.feature("lang").get(0))
        .put("size", description.feature("size").get(0))
        .put("layout", description.feature("layout"))
        .toBuffer()
    );
  }
}
