package io.github.raffaeleflorio.boggle.infrastructure.player;

import io.github.raffaeleflorio.boggle.domain.player.Player;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheets;
import io.smallrye.mutiny.Uni;
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
   * @param sheets The sheets repository
   * @since 1.0.0
   */
  public JsonPlayer(final JsonObject origin, final Sheets<CharSequence> sheets) {
    this.origin = origin;
    this.sheets = sheets;
  }

  @Override
  public UUID id() {
    return UUID.fromString(origin.getString("id"));
  }

  @Override
  public Uni<Sheet<CharSequence>> sheet(final UUID id) {
    return sheets.sheet(id);
  }

  private final JsonObject origin;
  private final Sheets<CharSequence> sheets;
}
