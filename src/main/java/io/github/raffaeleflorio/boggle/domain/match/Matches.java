package io.github.raffaeleflorio.boggle.domain.match;

import io.smallrye.mutiny.Uni;

import java.time.temporal.TemporalAmount;
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
   * Builds asynchronously a new match to play
   *
   * @param lang     The match language
   * @param size     The grid size
   * @param duration The match duration
   * @return The match
   * @since 1.0.0
   */
  Uni<Match<T>> match(CharSequence lang, CharSequence size, TemporalAmount duration);

  /**
   * Builds asynchronously a match by its id
   *
   * @param id The match id
   * @return The match
   * @since 1.0.0
   */
  Uni<Match<T>> match(UUID id);
}
