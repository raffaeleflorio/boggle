package io.github.raffaeleflorio.boggle.domain.grid;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.smallrye.mutiny.Uni;

/**
 * {@link Grid} repository
 *
 * @param <T> The grid word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Grids<T> {
  /**
   * Builds asynchronously a grid from its description
   *
   * @param description The grid description
   * @return The grid
   * @since 1.0.0
   */
  Uni<Grid<T>> grid(Description description);
}
