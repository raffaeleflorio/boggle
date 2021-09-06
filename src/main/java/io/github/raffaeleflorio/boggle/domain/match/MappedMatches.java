package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.smallrye.mutiny.Uni;

import java.util.UUID;
import java.util.function.Function;

/**
 * Asynchronously mapped {@link Matches}
 *
 * @param <T> The word type
 * @since 1.0.0
 */
public final class MappedMatches<T> implements Matches<T> {
  /**
   * Builds mapped matches
   *
   * @param origin The matches to decorate
   * @param mapFn  The asynchronous map function
   * @since 1.0.0
   */
  public MappedMatches(final Matches<T> origin, final Function<Match<T>, Uni<Match<T>>> mapFn) {
    this.origin = origin;
    this.mapFn = mapFn;
  }

  @Override
  public Uni<Match<T>> match(final Description description) {
    return mapped(origin.match(description));
  }

  private Uni<Match<T>> mapped(final Uni<Match<T>> match) {
    return match.onItem().transformToUni(mapFn::apply);
  }

  @Override
  public Uni<Match<T>> match(final UUID id) {
    return mapped(origin.match(id));
  }

  private final Matches<T> origin;
  private final Function<Match<T>, Uni<Match<T>>> mapFn;
}
