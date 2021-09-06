package io.github.raffaeleflorio.boggle.infrastructure.match;

import io.github.raffaeleflorio.boggle.domain.match.Match;
import io.vertx.core.json.JsonObject;

public final class MatchAsJson extends JsonObject {
  public MatchAsJson(final Match<CharSequence> match) {
    super(
      new JsonObject()
        .put("id", match.id())
        .toBuffer()
    );
  }
}
