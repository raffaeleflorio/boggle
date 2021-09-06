package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.smallrye.mutiny.Uni;

import java.time.Instant;
import java.util.UUID;
import java.util.function.BiFunction;

/**
 * {@link Matches} that understands deadline feature
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class DeadlineMatches<T> implements Matches<T> {
  public DeadlineMatches(final Matches<T> origin) {
    this(origin, SandTimerMatch::new);
  }

  public DeadlineMatches(final Matches<T> origin, final BiFunction<Match<T>, Instant, Match<T>> matchFn) {
    this.origin = origin;
    this.matchFn = matchFn;
  }

  @Override
  public Uni<Match<T>> match(final Description description) {
    return mapped(origin.match(description));
  }

  private Uni<Match<T>> mapped(final Uni<Match<T>> match) {
    return match.onItem().transform(x -> matchFn.apply(x, deadline(x.description())));
  }

  private Instant deadline(final Description description) {
    return Instant.parse(description.feature("deadline").get(0));
  }

  @Override
  public Uni<Match<T>> match(final UUID id) {
    return mapped(origin.match(id));
  }

  private final Matches<T> origin;
  private final BiFunction<Match<T>, Instant, Match<T>> matchFn;
}
