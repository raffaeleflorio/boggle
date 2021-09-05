package io.github.raffaeleflorio.boggle.domain.score;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import io.smallrye.mutiny.Uni;

/**
 * A {@link Score} emitted if the word is in a grid
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class IfInGrid<T> implements Score<T> {
  /**
   * Builds a score
   *
   * @param origin The score to decorate
   * @param grid   The grid
   * @since 1.0.0
   */
  public IfInGrid(final Score<T> origin, final Grid<T> grid) {
    this(origin, grid, 0);
  }

  /**
   * Builds a score
   *
   * @param origin The score to decorate
   * @param grid   The grid to use
   * @param def    The score to emit when not in grid
   * @since 1.0.0
   */
  IfInGrid(final Score<T> origin, final Grid<T> grid, final Integer def) {
    this(origin, grid, Uni.createFrom().item(def));
  }

  /**
   * Builds a score
   *
   * @param origin The score to decorate
   * @param grid   The grid to use
   * @param def    The score to emit when not in grid
   * @since 1.0.0
   */
  IfInGrid(final Score<T> origin, final Grid<T> grid, final Uni<Integer> def) {
    this.origin = origin;
    this.grid = grid;
    this.def = def;
  }

  @Override
  public Uni<Integer> score(final Dice<T> word) {
    return grid.compatible(word) ? origin.score(word) : def;
  }

  private final Score<T> origin;
  private final Grid<T> grid;
  private final Uni<Integer> def;
}
