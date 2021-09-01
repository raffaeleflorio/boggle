package io.github.raffaeleflorio.boggle.grid;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A {@link Grid} with a timer alongside
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class SandTimer implements Grid {
  /**
   * Builds a grid with a timer
   *
   * @param origin   The grid to decorate
   * @param duration The duration
   * @since 1.0.0
   */
  public SandTimer(final Grid origin, final TemporalAmount duration) {
    this(origin, duration, Instant::now);
  }

  /**
   * Builds a grid with a timer
   *
   * @param origin   The grid to decorate
   * @param duration The duration
   * @param now      The supplier of now
   * @since 1.0.0
   */
  SandTimer(final Grid origin, final TemporalAmount duration, final Supplier<Instant> now) {
    this(origin, now.get().plus(duration), now);
  }

  /**
   * Builds a grid with a timer
   *
   * @param origin   The grid to decorate
   * @param deadline The deadline
   * @param now      The supplier of now
   * @since 1.0.0
   */
  SandTimer(final Grid origin, final Instant deadline, final Supplier<Instant> now) {
    this(origin, deadline, now, new IllegalStateException("Deadline reached"), HashMap::new);
  }

  /**
   * Builds a grid with a timer
   *
   * @param origin    The grid to decorate
   * @param deadline  The deadline
   * @param now       The supplier of now
   * @param exception The exception to throw
   * @param cloneFn   The function to clone a map
   * @since 1.0.0
   */
  SandTimer(
    final Grid origin,
    final Instant deadline,
    final Supplier<Instant> now,
    final RuntimeException exception,
    final Function<Map<CharSequence, CharSequence>, Map<CharSequence, CharSequence>> cloneFn
  ) {
    this.origin = origin;
    this.deadline = deadline;
    this.now = now;
    this.exception = exception;
    this.cloneFn = cloneFn;
  }

  @Override
  public Grid shuffled() {
    return beforeDeadline(origin::shuffled);
  }

  private <T> T beforeDeadline(final Supplier<T> action) {
    if (deadlineReached()) {
      throw exception;
    }
    return action.get();
  }

  private Boolean deadlineReached() {
    return now.get().isAfter(deadline);
  }

  @Override
  public Integer score(final CharSequence word) {
    return beforeDeadline(() -> origin.score(word));
  }

  @Override
  public Map<CharSequence, CharSequence> description() {
    var clone = cloneFn.apply(origin.description());
    clone.put("deadline", deadline.toString());
    return Collections.unmodifiableMap(clone);
  }

  private final Grid origin;
  private final Instant deadline;
  private final Supplier<Instant> now;
  private final RuntimeException exception;
  private final Function<Map<CharSequence, CharSequence>, Map<CharSequence, CharSequence>> cloneFn;
}
