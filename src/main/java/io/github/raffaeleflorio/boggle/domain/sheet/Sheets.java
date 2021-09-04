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

  /**
   * A {@link Sheets} useful for testing
   *
   * @param <T> The word type
   * @since 1.0.0
   */
  final class Fake<T> implements Sheets<T> {
    /**
     * Builds a fake with one element
     *
     * @since 1.0.0
     */
    public Fake() {
      this(new Sheet.Fake<>());
    }

    /**
     * Builds a fake with one element
     *
     * @since 1.0.0
     */
    public Fake(final Sheet<T> sheet) {
      this(Uni.createFrom().item(sheet));
    }

    /**
     * Builds a fake with one element
     *
     * @param sheet The sheet
     * @since 1.0.0
     */
    public Fake(final Uni<Sheet<T>> sheet) {
      this.sheet = sheet;
    }

    @Override
    public Uni<Sheet<T>> sheet(final Description description) {
      return sheet;
    }

    @Override
    public Uni<Sheet<T>> sheet(final UUID id) {
      return sheet;
    }

    private final Uni<Sheet<T>> sheet;
  }
}
