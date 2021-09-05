package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.Map;
import java.util.UUID;

/**
 * A boggle match
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Match<T> {
  /**
   * Builds the match id
   *
   * @return The id
   * @since 1.0.0
   */
  UUID id();

  /**
   * Builds asynchronously a new sheet to play
   *
   * @param id The player joining
   * @return The sheet
   * @since 1.0.0
   */
  Uni<Sheet<T>> sheet(UUID id);

  /**
   * Builds asynchronously the final score per player
   *
   * @return The score
   * @since 1.0.0
   */
  Multi<Map.Entry<UUID, Integer>> score();

  /**
   * Builds a match description
   *
   * @return The description
   * @since 1.0.0
   */
  Description description();
}
