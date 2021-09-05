package io.github.raffaeleflorio.boggle.infrastructure.grid;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import io.github.raffaeleflorio.boggle.domain.grid.Grids;
import io.smallrye.mutiny.Uni;

import java.util.Map;

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
   * @param map The lang and size to grid map
   * @since 1.0.0
   */
  public InMemoryGrids(final Map<Map.Entry<CharSequence, CharSequence>, Grid<T>> map) {
    this.map = map;
  }

  @Override
  public Uni<Grid<T>> grid(final Description description) {
    return Uni.createFrom().item(map.get(entry(description)));
  }

  private Map.Entry<CharSequence, CharSequence> entry(final Description description) {
    return Map.entry(
      description.feature("lang").get(0),
      description.feature("size").get(0)
    );
  }

  private final Map<Map.Entry<CharSequence, CharSequence>, Grid<T>> map;
}
