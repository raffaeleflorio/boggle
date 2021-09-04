package io.github.raffaeleflorio.boggle.domain.sheet;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.smallrye.mutiny.Uni;

import java.time.Instant;
import java.util.UUID;
import java.util.function.BiFunction;

/**
 * {@link Sheets} built with expiration if present in description
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class SandTimerSheets<T> implements Sheets<T> {
  /**
   * Builds a repo
   *
   * @param origin The sheets to decorate
   * @since 1.0.0
   */
  public SandTimerSheets(final Sheets<T> origin) {
    this(origin, new RuntimeException("Deadline reached"), SandTimerSheet::new);
  }

  /**
   * Builds a repo
   *
   * @param origin    The sheets to decorate
   * @param exception The exception to throw
   * @param sheetFn   The function to build sheet with deadline
   * @since 1.0.0
   */
  SandTimerSheets(
    final Sheets<T> origin,
    final RuntimeException exception,
    final BiFunction<Sheet<T>, Instant, Sheet<T>> sheetFn
  ) {
    this.origin = origin;
    this.exception = exception;
    this.sheetFn = sheetFn;
  }

  @Override
  public Uni<Sheet<T>> sheet(final Description description) {
    return sandTimerSheet(origin.sheet(description));
  }

  private Uni<Sheet<T>> sandTimerSheet(final Uni<Sheet<T>> sheet) {
    return sheet
      .onItem().transform(x -> sheetFn.apply(x, deadline(x.description())));
  }

  private Instant deadline(final Description description) {
    return Instant.parse(description.feature("deadline").get(0));
  }

  @Override
  public Uni<Sheet<T>> sheet(final UUID id) {
    return sandTimerSheet(origin.sheet(id));
  }

  private final Sheets<T> origin;
  private final RuntimeException exception;
  private final BiFunction<Sheet<T>, Instant, Sheet<T>> sheetFn;
}
