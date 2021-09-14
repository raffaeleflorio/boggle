package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import io.github.raffaeleflorio.boggle.domain.sandtimer.SandTimer;
import io.github.raffaeleflorio.boggle.domain.sandtimer.SimpleSandTimer;
import io.github.raffaeleflorio.boggle.domain.sheet.DeadlineSheet;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * {@link Match} with a deadline
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class DeadlineMatch<T> implements Match<T> {
  /**
   * Builds a deadline match
   *
   * @param origin   The match to decorate
   * @param deadline The deadline
   */
  public DeadlineMatch(final Match<T> origin, final Instant deadline) {
    this(origin, new SimpleSandTimer(deadline, Instant::now));
  }

  /**
   * Builds a match with sand timer
   *
   * @param origin    The match to decorate
   * @param sandTimer The sand timer
   */
  public DeadlineMatch(final Match<T> origin, final SandTimer sandTimer) {
    this(origin, sandTimer, msg -> new IllegalStateException(msg.toString()));
  }

  /**
   * Builds a match with sand timer
   *
   * @param origin      The match to decorate
   * @param sandTimer   The sand timer
   * @param exceptionFn The function to build the exception to throw
   */
  public DeadlineMatch(
    final Match<T> origin,
    final SandTimer sandTimer,
    final Function<CharSequence, RuntimeException> exceptionFn
  ) {
    this(origin, sandTimer, exceptionFn, DeadlineSheet::new);
  }

  /**
   * Builds a match with sand timer
   *
   * @param origin      The match to decorate
   * @param sandTimer   The sand timer
   * @param exceptionFn The exception to throw
   * @param sheetFn     The function to build deadline sheet
   */
  DeadlineMatch(
    final Match<T> origin,
    final SandTimer sandTimer,
    final Function<CharSequence, RuntimeException> exceptionFn,
    final BiFunction<Sheet<T>, SandTimer, Sheet<T>> sheetFn
  ) {
    this.origin = origin;
    this.sandTimer = sandTimer;
    this.exceptionFn = exceptionFn;
    this.sheetFn = sheetFn;
  }

  @Override
  public UUID id() {
    return origin.id();
  }

  @Override
  public Uni<Sheet<T>> sheet(final UUID id) {
    return origin.sheet(id).onItem().transform(x -> sheetFn.apply(x, sandTimer));
  }

  @Override
  public Multi<Map.Entry<UUID, Integer>> score() {
    if (!sandTimer.expired()) {
      return Multi.createFrom().failure(exceptionFn.apply("Unable to build score of an in progress match"));
    }
    return origin.score();
  }

  @Override
  public Description description() {
    return origin.description();
  }

  @Override
  public Uni<Grid<T>> grid() {
    return origin.grid();
  }

  @Override
  public Multi<UUID> players() {
    return origin.players();
  }

  private final Match<T> origin;
  private final SandTimer sandTimer;
  private final Function<CharSequence, RuntimeException> exceptionFn;
  private final BiFunction<Sheet<T>, SandTimer, Sheet<T>> sheetFn;
}
