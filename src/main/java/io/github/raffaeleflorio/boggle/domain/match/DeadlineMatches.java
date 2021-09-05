package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.smallrye.mutiny.Uni;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * {@link Matches} that understands duration feature and builds deadline feature
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class DeadlineMatches<T> implements Matches<T> {
  /**
   * Builds minutes based matches
   *
   * @param origin The matches to decorate
   * @since 1.0.0
   */
  public DeadlineMatches(final Matches<T> origin) {
    this(origin, Duration::ofMinutes);
  }

  /**
   * Builds matches
   *
   * @param origin     The matches to decorate
   * @param durationFn The function to build duration
   * @since 1.0.0
   */
  public DeadlineMatches(final Matches<T> origin, final Function<Integer, Duration> durationFn) {
    this(origin, durationFn, Instant::now);
  }

  /**
   * Builds matches
   *
   * @param origin     The matches to decorate
   * @param durationFn The function to build duration
   * @param now        The function that supply now
   * @since 1.0.0
   */
  DeadlineMatches(
    final Matches<T> origin,
    final Function<Integer, Duration> durationFn,
    final Supplier<Instant> now
  ) {
    this(origin, durationFn, now, Integer::parseUnsignedInt);
  }

  /**
   * Builds matches
   *
   * @param origin     The matches to decorate
   * @param durationFn The function to build duration
   * @param now        The function that supply now
   * @param stringFn   The function to convert string to int
   * @since 1.0.0
   */
  DeadlineMatches(
    final Matches<T> origin,
    final Function<Integer, Duration> durationFn,
    final Supplier<Instant> now,
    final Function<String, Integer> stringFn
  ) {
    this.origin = origin;
    this.durationFn = durationFn;
    this.now = now;
    this.stringFn = stringFn;
  }

  @Override
  public Uni<Match<T>> match(final Description description) {
    return origin.match(description.feature("deadline", deadline(description)));
  }

  private List<CharSequence> deadline(final Description description) {
    return List.of(now.get().plus(duration(description)).toString());
  }

  private TemporalAmount duration(final Description description) {
    return durationFn.apply(
      stringFn.apply(description.feature("duration").get(0).toString())
    );
  }

  @Override
  public Uni<Match<T>> match(final UUID id) {
    return origin.match(id);
  }

  private final Matches<T> origin;
  private final Function<Integer, Duration> durationFn;
  private final Supplier<Instant> now;
  private final Function<String, Integer> stringFn;
}
