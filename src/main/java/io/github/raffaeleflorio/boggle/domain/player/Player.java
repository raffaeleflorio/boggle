package io.github.raffaeleflorio.boggle.domain.player;

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
     * Builds a fake
     *
     * @param id The id
     * @since 1.0.0
     */
    public Fake(final UUID id) {
      this.id = id;
    }

    @Override
    public UUID id() {
      return id;
    }

    private final UUID id;
  }
}
