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
package io.github.raffaeleflorio.boggle.domain.player;

import java.util.UUID;

/**
 * A boggle player
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Player<T> {
  /**
   * Builds the player id
   *
   * @return The id
   * @since 1.0.0
   */
  UUID id();

  /**
   * {@link Player} useful for testing
   *
   * @param <T> The word type
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  final class Fake<T> implements Player<T> {
    /**
     * Builds a fake
     *
     * @param id The id
     * @since 1.0.0
     */
    public Fake(final UUID id) {
      this.id = id;
    }

    @Override
    public UUID id() {
      return id;
    }

    private final UUID id;
  }
}
