package io.github.raffaeleflorio.boggle.domain.score;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import io.smallrye.mutiny.Uni;

/**
 * {@link Score} function that also verify word against a {@link Grid}
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
   * @param grid   The grid to use
   * @since 1.0.0
   */
  public IfInGrid(final Score<T> origin, final Grid<T> grid) {
    this(origin, Uni.createFrom().item(grid));
  }

  /**
   * Builds a score
   *
   * @param origin The score to decorate
   * @param grid   The grid emitter to use
   * @since 1.0.0
   */
  public IfInGrid(final Score<T> origin, final Uni<Grid<T>> grid) {
    this(origin, grid, 0);
  }

  /**
   * Builds a score
   *
   * @param origin The score to decorate
   * @param grid   The grid emitter to use
   * @param def    The score to emit when a word i snot in grid
   * @since 1.0.0
   */
  public IfInGrid(final Score<T> origin, final Uni<Grid<T>> grid, final Integer def) {
    this(origin, grid, Uni.createFrom().item(def));
  }

  /**
   * Builds a score
   *
   * @param origin The score to decorate
   * @param grid   The grid emitter to use
   * @param def    The score emitter to use when a word is not in grid
   * @since 1.0.0
   */
  public IfInGrid(final Score<T> origin, final Uni<Grid<T>> grid, final Uni<Integer> def) {
    this.origin = origin;
    this.grid = grid;
    this.def = def;
  }

  @Override
  public Uni<Integer> score(final Dice<T> word) {
    return validWord(word).chain(validWord -> validWord ? origin.score(word) : def);
  }

  private Uni<Boolean> validWord(final Dice<T> word) {
    return grid.onItem().transform(x -> x.compatible(word));
  }

  private final Score<T> origin;
  private final Uni<? extends Grid<T>> grid;
  private final Uni<Integer> def;
}
