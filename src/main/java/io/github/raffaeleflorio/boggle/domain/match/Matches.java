package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.smallrye.mutiny.Uni;

import java.util.UUID;
import java.util.function.Function;

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
   * @return The match or null if not found
   * @since 1.0.0
   */
  Uni<Match<T>> match(Description description);

  /**
   * Builds asynchronously a match by its id
   *
   * @param id The match id
   * @return The match or null if not found
   * @since 1.0.0
   */
  Uni<Match<T>> match(UUID id);

  /**
   * {@link Matches} useful for testing
   *
   * @param <T> The word type
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  final class Fake<T> implements Matches<T> {
    /**
     * Builds a fake
     *
     * @param newMatchFn The function to build new match
     * @param matchFn    The function to build existing match
     * @since 1.0.0
     */
    public Fake(final Function<Description, Uni<Match<T>>> newMatchFn, final Function<UUID, Uni<Match<T>>> matchFn) {
      this.newMatchFn = newMatchFn;
      this.matchFn = matchFn;
    }

    @Override
    public Uni<Match<T>> match(final Description description) {
      return newMatchFn.apply(description);
    }

    @Override
    public Uni<Match<T>> match(final UUID id) {
      return matchFn.apply(id);
    }

    private final Function<Description, Uni<Match<T>>> newMatchFn;
    private final Function<UUID, Uni<Match<T>>> matchFn;
  }
}
