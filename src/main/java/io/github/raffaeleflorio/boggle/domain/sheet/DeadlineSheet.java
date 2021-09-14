package io.github.raffaeleflorio.boggle.domain.sheet;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.sandtimer.SandTimer;
import io.github.raffaeleflorio.boggle.domain.sandtimer.SimpleSandTimer;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * A {@link Sheet} with a deadline
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class DeadlineSheet<T> implements Sheet<T> {
  /**
   * Builds a sheet
   *
   * @param origin   The sheet to decorate
   * @param deadline The deadline
   * @since 1.0.0
   */
  public DeadlineSheet(final Sheet<T> origin, final Instant deadline) {
    this(origin, new SimpleSandTimer(deadline, Instant::now));
  }

  /**
   * Builds a deadline sheet with a {@link SandTimer}
   *
   * @param origin    The sheet to decorate
   * @param sandTimer The sand timer
   * @since 1.0.0
   */
  public DeadlineSheet(final Sheet<T> origin, final SandTimer sandTimer) {
    this(origin, sandTimer, new IllegalStateException("Deadline reached"));
  }

  /**
   * Builds a deadline sheet with a sand timer and a custom exception
   *
   * @param origin    The sheet to decorate
   * @param sandTimer The sand timer
   * @param exception The exception to throw
   * @since 1.0.0
   */
  public DeadlineSheet(
    final Sheet<T> origin,
    final SandTimer sandTimer,
    final RuntimeException exception
  ) {
    this.origin = origin;
    this.sandTimer = sandTimer;
    this.exception = exception;
  }

  @Override
  public UUID id() {
    return origin.id();
  }

  @Override
  public Multi<Dice<T>> words() {
    return origin.words();
  }

  @Override
  public Uni<Void> word(final Dice<T> word) {
    return beforeExpiration(() -> origin.word(word));

  }

  private <X> Uni<X> beforeExpiration(final Supplier<Uni<X>> target) {
    if (sandTimer.expired()) {
      return Uni.createFrom().failure(exception);
    }
    return target.get();
  }

  @Override
  public Description description() {
    return origin.description();
  }

  private final Sheet<T> origin;
  private final SandTimer sandTimer;
  private final RuntimeException exception;
}
