package io.github.raffaeleflorio.boggle.domain.grid;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.smallrye.mutiny.Uni;

import java.util.function.Function;

/**
 * Mapped {@link Grids}
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
final class MappedGrids<T> implements Grids<T> {
  /**
   * Builds a grids
   *
   * @param origin The grids to decorate
   * @param mapFn  The map function
   * @since 1.0.0
   */
  public MappedGrids(final Grids<T> origin, final Function<Grid<T>, Grid<T>> mapFn) {
    this.origin = origin;
    this.mapFn = mapFn;
  }

  @Override
  public Uni<Grid<T>> grid(final Description description) {
    return origin.grid(description).onItem().ifNotNull().transform(mapFn);
  }

  private final Grids<T> origin;
  private final Function<Grid<T>, Grid<T>> mapFn;
}
