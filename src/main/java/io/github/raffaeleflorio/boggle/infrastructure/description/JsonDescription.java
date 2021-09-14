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
package io.github.raffaeleflorio.boggle.infrastructure.description;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * {@link Description} that animates a JSON
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class JsonDescription implements Description {
  /**
   * Builds a description
   *
   * @param origin The JSON
   * @since 1.0.0
   */
  public JsonDescription(final JsonObject origin) {
    this.origin = origin;
  }

  @Override
  public List<CharSequence> feature(final CharSequence name) {
    return List.of(origin.getString(name.toString()));
  }

  @Override
  public Description feature(final CharSequence name, final List<CharSequence> values) {
    return new JsonDescription(origin.copy().put(name.toString(), values.get(0)));
  }

  private final JsonObject origin;
}
