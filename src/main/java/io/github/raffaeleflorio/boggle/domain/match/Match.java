package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheets;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.Map;
import java.util.UUID;

/**
 * A boggle match
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Match<T> {
  /**
   * Builds the match id
   *
   * @return The id
   * @since 1.0.0
   */
  UUID id();

  /**
   * Builds asynchronously a new sheet to play
   *
   * @param id The player joining
   * @return The sheet
   * @since 1.0.0
   */
  Uni<Sheet<T>> sheet(UUID id);

  /**
   * Builds asynchronously the final score per player
   *
   * @return The score
   * @since 1.0.0
   */
  Multi<Map.Entry<UUID, Integer>> score();

  /**
   * Builds a match description
   *
   * @return The description
   * @since 1.0.0
   */
  Description description();

  /**
   * {@link Match} useful for testing
   *
   * @param <T> The word type
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  final class Fake<T> implements Match<T> {
    /**
     * Builds a fake with a random id and an empty description
     *
     * @since 1.0.0
     */
    public Fake() {
      this(UUID.randomUUID(), new Description.Fake());
    }

    /**
     * Builds a fake
     *
     * @param id The id
     * @since 1.0.0
     */
    public Fake(final UUID id, final Description description) {
      this(id, description, Map.of(), new Sheets.Fake<>());
    }

    /**
     * Builds a fake
     *
     * @param scores The scores
     * @since 1.0.0
     */
    public Fake(final Map<UUID, Integer> scores) {
      this(UUID.randomUUID(), new Description.Fake(), scores, new Sheets.Fake<>());
    }


    /**
     * Builds a fake
     *
     * @param sheets The sheets
     * @since 1.0.0
     */
    public Fake(final Sheets<T> sheets) {
      this(UUID.randomUUID(), new Description.Fake(), Map.of(), sheets);
    }

    /**
     * Builds a fake
     *
     * @param id          The id
     * @param description The description
     * @param scores      The scores
     * @param sheets      The sheets
     * @since 1.0.0
     */
    public Fake(
      final UUID id,
      final Description description,
      final Map<UUID, Integer> scores,
      final Sheets<T> sheets
    ) {
      this.id = id;
      this.description = description;
      this.scores = scores;
      this.sheets = sheets;
    }

    @Override
    public UUID id() {
      return id;
    }

    @Override
    public Uni<Sheet<T>> sheet(final UUID id) {
      return sheets.sheet(UUID.randomUUID());
    }

    @Override
    public Multi<Map.Entry<UUID, Integer>> score() {
      return Multi.createFrom().items(scores.entrySet()::stream);
    }

    @Override
    public Description description() {
      return description;
    }

    private final UUID id;
    private final Description description;
    private final Map<UUID, Integer> scores;
    private final Sheets<T> sheets;
  }
}
