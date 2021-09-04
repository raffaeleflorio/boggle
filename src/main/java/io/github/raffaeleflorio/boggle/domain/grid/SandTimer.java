package io.github.raffaeleflorio.boggle.domain.grid;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.function.Supplier;

/**
 * A {@link Grid} with a timer alongside
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class SandTimer<T> implements Grid<T> {
  /**
   * Builds a grid with a timer
   *
   * @param origin   The grid to decorate
   * @param duration The duration
   * @since 1.0.0
   */
  public SandTimer(final Grid<T> origin, final TemporalAmount duration) {
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
  public SandTimer(final Grid<T> origin, final TemporalAmount duration, final Supplier<Instant> now) {
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
  public SandTimer(final Grid<T> origin, final Instant deadline, final Supplier<Instant> now) {
    this(origin, deadline, now, new IllegalStateException("Deadline reached"));
  }

  /**
   * Builds a grid with a timer
   *
   * @param origin    The grid to decorate
   * @param deadline  The deadline
   * @param now       The supplier of now
   * @param exception The exception to throw
   * @since 1.0.0
   */
  public SandTimer(
    final Grid<T> origin,
    final Instant deadline,
    final Supplier<Instant> now,
    final RuntimeException exception
  ) {
    this.origin = origin;
    this.deadline = deadline;
    this.now = now;
    this.exception = exception;
  }

  @Override
  public List<T> values() {
    return beforeDeadline(origin::values);
  }

  private <X> X beforeDeadline(final Supplier<X> action) {
    if (deadlineReached()) {
      throw exception;
    }
    return action.get();
  }

  @Override
  public Grid<T> shuffled() {
    return beforeDeadline(origin::shuffled);
  }

  private Boolean deadlineReached() {
    return now.get().isAfter(deadline);
  }

  @Override
  public Boolean compatible(final Dice<T> word) {
    return beforeDeadline(() -> origin.compatible(word));
  }

  @Override
  public Description description() {
    return origin.description().feature("deadline", List.of(deadline.toString()));
  }

  private final Grid<T> origin;
  private final Instant deadline;
  private final Supplier<Instant> now;
  private final RuntimeException exception;
}
