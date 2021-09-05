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
 * A {@link Sheet} that expires according its description
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class SandTimerSheet<T> implements Sheet<T> {
  /**
   * Builds a sand timer sheet
   *
   * @param origin   The sheet to decorate
   * @param deadline The deadline
   * @since 1.0.0
   */
  public SandTimerSheet(final Sheet<T> origin, final Instant deadline) {
    this(origin, new SimpleSandTimer(deadline, Instant::now));
  }

  /**
   * Builds a sand timer sheet
   *
   * @param origin    The sheet to decorate
   * @param sandTimer The sandtimer
   * @since 1.0.0
   */
  public SandTimerSheet(final Sheet<T> origin, final SandTimer sandTimer) {
    this(origin, sandTimer, new IllegalStateException("Deadline reached"));
  }

  /**
   * Builds a sand timer sheet
   *
   * @param origin    The sheet to decorate
   * @param sandTimer The sandtimer
   * @param exception The exception to throw
   * @since 1.0.0
   */
  public SandTimerSheet(
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
    return beforeExpiration(origin::id);
  }

  private <X> X beforeExpiration(final Supplier<X> fn) {
    if (sandTimer.expired()) {
      throw exception;
    }
    return fn.get();
  }

  @Override
  public Multi<Dice<T>> words() {
    return beforeExpiration(origin::words);
  }

  @Override
  public Multi<Dice<T>> words(final Sheet<T> other) {
    return beforeExpiration(() -> origin.words(other));
  }

  @Override
  public Uni<Void> word(final Dice<T> word) {
    return beforeExpiration(() -> origin.word(word));
  }

  @Override
  public Description description() {
    return beforeExpiration(origin::description);
  }

  private final Sheet<T> origin;
  private final SandTimer sandTimer;
  private final RuntimeException exception;
}
