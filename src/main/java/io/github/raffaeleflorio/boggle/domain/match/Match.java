package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

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
   * @return The sheet or null if not found
   * @since 1.0.0
   */
  Uni<Sheet<T>> sheet(UUID id);

  /**
   * Builds asynchronously the final score per player
   *
   * @return The score per player
   * @since 1.0.0
   */
  Multi<Map.Entry<UUID, Integer>> score();

  /**
   * Builds the description
   *
   * @return The description
   * @since 1.0.0
   */
  Description description();

  /**
   * Builds asynchronously the grid
   *
   * @return The grid
   * @since 1.0.0
   */
  Uni<Grid<T>> grid();

  /**
   * Builds asynchronously the players playing the game
   *
   * @return The players
   * @since 1.0.0
   */
  Multi<UUID> players();

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
     * @param id          The id
     * @param description The description
     * @since 1.0.0
     */
    public Fake(final UUID id, final Description description) {
      this(id, description, Multi.createFrom().empty(), x -> Uni.createFrom().nullItem(), new Grid.Fake<>());
    }

    /**
     * Builds a fake
     *
     * @param score The score
     * @since 1.0.0
     */
    public Fake(final Multi<Map.Entry<UUID, Integer>> score) {
      this(score, x -> Uni.createFrom().nullItem());
    }

    /**
     * Builds a fake
     *
     * @param score   The score
     * @param sheetFn The function to build sheet
     * @since 1.0.0
     */
    public Fake(final Multi<Map.Entry<UUID, Integer>> score, final Function<UUID, Uni<Sheet<T>>> sheetFn) {
      this(UUID.randomUUID(), new Description.Fake(), score, sheetFn, new Grid.Fake<>());
    }

    /**
     * Builds a fake
     *
     * @param sheetFn The function to build sheet
     * @since 1.0.0
     */
    public Fake(final Function<UUID, Uni<Sheet<T>>> sheetFn) {
      this(Multi.createFrom().empty(), sheetFn);
    }

    /**
     * Builds a fake
     *
     * @param scoreMap The score per player
     * @param sheetMap The sheet per player
     * @since 1.0.0
     */
    public Fake(final Map<UUID, Integer> scoreMap, final Map<UUID, Sheet<T>> sheetMap) {
      this(
        Multi.createFrom().items(scoreMap.entrySet()::stream),
        player -> Uni.createFrom().item(sheetMap.get(player))
      );
    }

    /**
     * Builds a fake
     *
     * @param scoreMap The score per player
     * @since 1.0.0
     */
    public Fake(final Map<UUID, Integer> scoreMap) {
      this(scoreMap, Map.of());
    }

    /**
     * Builds a fake
     *
     * @param grid The grid
     * @since 1.0.0
     */
    public Fake(final Grid<T> grid) {
      this(UUID.randomUUID(), new Description.Fake(), Multi.createFrom().empty(), x -> Uni.createFrom().nullItem(), grid);
    }

    /**
     * Builds a fake
     *
     * @param id          The id
     * @param description The description
     * @param scoreMap    The score map
     * @param sheetMap    The sheet map
     * @param grid        The grid
     * @since 1.0.0
     */
    public Fake(
      final UUID id,
      final Description description,
      final Map<UUID, Integer> scoreMap,
      final Map<UUID, Sheet<T>> sheetMap,
      final Grid<T> grid
    ) {
      this(
        id,
        description,
        Multi.createFrom().items(scoreMap.entrySet()::stream),
        player -> Uni.createFrom().item(sheetMap.get(player)),
        grid
      );
    }

    /**
     * Builds a fake
     *
     * @param id          The id
     * @param description The description
     * @param score       The score
     * @param sheetFn     The function to build sheet
     * @param grid        The grid
     * @since 1.0.0
     */
    public Fake(
      final UUID id,
      final Description description,
      final Multi<Map.Entry<UUID, Integer>> score,
      final Function<UUID, Uni<Sheet<T>>> sheetFn,
      final Grid<T> grid
    ) {
      this.id = id;
      this.description = description;
      this.score = score;
      this.sheetFn = sheetFn;
      this.grid = grid;
    }

    @Override
    public UUID id() {
      return id;
    }

    @Override
    public Uni<Sheet<T>> sheet(final UUID id) {
      return sheetFn.apply(id);
    }

    @Override
    public Multi<Map.Entry<UUID, Integer>> score() {
      return score;
    }

    @Override
    public Description description() {
      return description;
    }

    @Override
    public Uni<Grid<T>> grid() {
      return Uni.createFrom().item(grid);
    }

    @Override
    public Multi<UUID> players() {
      return score.onItem().transform(Map.Entry::getKey);
    }

    private final UUID id;
    private final Description description;
    private final Multi<Map.Entry<UUID, Integer>> score;
    private final Function<UUID, Uni<Sheet<T>>> sheetFn;
    private final Grid<T> grid;
  }
}
