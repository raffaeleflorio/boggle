package io.github.raffaeleflorio.boggle.infrastructure.grid;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import io.github.raffaeleflorio.boggle.domain.grid.Grids;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.Map;
import java.util.function.Predicate;

/**
 * An in memory implementation of {@link Grids}
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class InMemoryGrids<T> implements Grids<T> {
  /**
   * Builds an in memory grids
   *
   * @param grids The description to grids map
   * @since 1.0.0
   */
  public InMemoryGrids(final Map<Predicate<Description>, Grid<T>> grids) {
    this.grids = grids;
  }

  @Override
  public Uni<Grid<T>> grid(final Description description) {
    return Multi.createFrom().iterable(grids.entrySet())
      .filter(entry -> entry.getKey().test(description)).collect().first()
      .onItem().ifNotNull().transform(Map.Entry::getValue)
      .onItem().ifNotNull().transform(Grid::shuffled);
  }

  private final Map<Predicate<Description>, Grid<T>> grids;
}
