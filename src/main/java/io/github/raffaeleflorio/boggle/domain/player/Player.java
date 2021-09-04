package io.github.raffaeleflorio.boggle.domain.player;

import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import io.smallrye.mutiny.Uni;

import java.util.UUID;

/**
 * A boggle player
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Player<T> {
  /**
   * Builds the player id
   *
   * @return The id
   * @since 1.0.0
   */
  UUID id();

  /**
   * Builds asynchronously a sheet by its id
   *
   * @param id The sheet id
   * @return The sheet
   * @since 1.0.0
   */
  Uni<Sheet<T>> sheet(UUID id);

  /**
   * {@link Player} useful for testing
   *
   * @param <T> The word type
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  final class Fake<T> implements Player<T> {
    /**
     * Builds a fake without sheets and with random id
     *
     * @since 1.0.0
     */
    public Fake() {
      this(UUID.randomUUID());
    }

    /**
     * Builds a fake without sheets
     *
     * @since 1.0.0
     */
    public Fake(final UUID id) {
      this(id, Uni.createFrom().nullItem());
    }

    /**
     * Builds a fake
     *
     * @param id    The id
     * @param sheet The sheet
     * @since 1.0.0
     */
    public Fake(final UUID id, final Uni<Sheet<T>> sheet) {
      this.id = id;
      this.sheet = sheet;
    }

    @Override
    public UUID id() {
      return id;
    }

    @Override
    public Uni<Sheet<T>> sheet(final UUID id) {
      return sheet;
    }

    private final UUID id;
    private final Uni<Sheet<T>> sheet;
  }
}
