package io.github.raffaeleflorio.boggle.infrastructure.sheet;

import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import io.vertx.core.json.JsonObject;

public final class SheetAsJson extends JsonObject {
  public SheetAsJson(final Sheet<CharSequence> origin) {
    super(
      new JsonObject()
        .put("id", origin.id().toString())
        .toBuffer()
    );
  }
}
