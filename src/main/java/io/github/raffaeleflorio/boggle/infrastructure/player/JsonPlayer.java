package io.github.raffaeleflorio.boggle.infrastructure.player;

import io.github.raffaeleflorio.boggle.domain.player.Player;
import io.vertx.core.json.JsonObject;

import java.util.UUID;

/**
 * {@link Player} that animates a JSON
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class JsonPlayer implements Player<CharSequence> {
  /**
   * Builds a player
   *
   * @param origin The json
   * @since 1.0.0
   */
  public JsonPlayer(final JsonObject origin) {
    this.origin = origin;
  }

  @Override
  public UUID id() {
    return UUID.fromString(origin.getString("id"));
  }

  private final JsonObject origin;
}
