package io.github.raffaeleflorio.boggle.domain.grid;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.smallrye.mutiny.Uni;

import java.util.function.Function;

/**
 * {@link Grid} repository
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Grids<T> {
  /**
   * Builds asynchronously a grid from its description
   *
   * @param description The grid description
   * @return The grid otherwise null
   * @since 1.0.0
   */
  Uni<Grid<T>> grid(Description description);

  /**
   * {@link Grids} useful for testing
   *
   * @param <T> The word type
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  final class Fake<T> implements Grids<T> {
    /**
     * Builds an empty repository
     *
     * @since 1.0.0
     */
    public Fake() {
      this(x -> Uni.createFrom().nullItem());
    }

    /**
     * Builds a fake
     *
     * @param gridFn The function to map description to grid
     * @since 1.0.0
     */
    public Fake(final Function<Description, Uni<Grid<T>>> gridFn) {
      this.gridFn = gridFn;
    }

    @Override
    public Uni<Grid<T>> grid(final Description description) {
      return gridFn.apply(description);
    }

    private final Function<Description, Uni<Grid<T>>> gridFn;
  }
}
