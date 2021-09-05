package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.smallrye.mutiny.Uni;

import java.util.UUID;

/**
 * {@link Match} repository
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Matches<T> {
  /**
   * Builds asynchronously a new match to play according its description
   *
   * @return The match
   * @since 1.0.0
   */
  Uni<Match<T>> match(Description description);

  /**
   * Builds asynchronously a match by its id
   *
   * @param id The match id
   * @return The match
   * @since 1.0.0
   */
  Uni<Match<T>> match(UUID id);
}
