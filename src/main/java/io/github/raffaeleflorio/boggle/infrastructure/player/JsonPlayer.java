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
    return UUID.fromString(origin.getString("player"));
  }

  private final JsonObject origin;
}
