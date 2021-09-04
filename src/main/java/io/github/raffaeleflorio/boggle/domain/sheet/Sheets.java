package io.github.raffaeleflorio.boggle.domain.sheet;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.smallrye.mutiny.Uni;

import java.util.UUID;

/**
 * {@link Sheet} repository
 *
 * @param <T> The sheet word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Sheets<T> {
  /**
   * Builds asynchronously a new sheet by its description
   *
   * @param description The sheet description
   * @return The sheet
   * @since 1.0.0
   */
  Uni<Sheet<T>> sheet(Description description);

  /**
   * Builds asynchronously a sheet from its id
   *
   * @param id The sheet id
   * @return The sheet
   * @since 1.0.0
   */
  Uni<Sheet<T>> sheet(UUID id);
}
