package io.github.raffaeleflorio.boggle.domain.sandtimer;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.function.Supplier;

/**
 * A simple sandtimer
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class SimpleSandTimer implements SandTimer {
  /**
   * Builds a sand timer
   *
   * @param duration The duration
   * @since 1.0.0
   */
  public SimpleSandTimer(final TemporalAmount duration) {
    this(duration, Instant::now);
  }

  /**
   * Builds a sand timer
   *
   * @param duration The duration
   * @param now      The supplier of now
   * @since 1.0.0
   */
  public SimpleSandTimer(final TemporalAmount duration, final Supplier<Instant> now) {
    this(now.get().plus(duration), now);
  }

  /**
   * Builds a sand timer
   *
   * @param deadline The deadline
   * @since 1.0.0
   */
  SimpleSandTimer(final Instant deadline, final Supplier<Instant> now) {
    this.deadline = deadline;
    this.now = now;
  }

  @Override
  public Boolean expired() {
    return now.get().isAfter(deadline);
  }

  private final Instant deadline;
  private final Supplier<Instant> now;
}
