package io.github.raffaeleflorio.boggle.domain.player;

import io.smallrye.mutiny.Uni;

import java.util.UUID;

/**
 * {@link Player} repository
 *
 * @param <T> The word player type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Players<T> {
  /**
   * Builds asynchronously a player by its id
   *
   * @param id The player id
   * @return The player
   * @since 1.0.0
   */
  Uni<Player<T>> player(UUID id);
}
