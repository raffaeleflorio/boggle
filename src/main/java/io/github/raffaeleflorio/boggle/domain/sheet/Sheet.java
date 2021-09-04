package io.github.raffaeleflorio.boggle.domain.sheet;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.UUID;

/**
 * A word sheet
 *
 * @param <T> The word type
 * @since 1.0.0
 */
public interface Sheet<T> {
  /**
   * Builds the sheet id
   *
   * @return The id
   * @since 1.0.0
   */
  UUID id();

  /**
   * Builds asynchronously the words
   *
   * @return The words
   * @since 1.0.0
   */
  Multi<Dice<T>> words();

  /**
   * Builds asynchronously the unique words
   *
   * @param other The sheet to compare
   * @return The words
   * @since 1.0.0
   */
  Multi<Dice<T>> words(Sheet<T> other);

  /**
   * Writes asynchronously a new word
   *
   * @param word The word
   * @return Emitter of completion or failure
   * @since 1.0.0
   */
  Uni<Void> word(Dice<T> word);

  /**
   * Builds the sheet description
   *
   * @return The description
   * @since 1.0.0
   */
  Description description();

  /**
   * A {@link Sheet} useful for testing purpose
   *
   * @param <T>
   */
  final class Fake<T> implements Sheet<T> {
    /**
     * Builds an empty fake with random id
     *
     * @since 1.0.0
     */
    public Fake() {
      this(UUID.randomUUID());
    }

    /**
     * Builds an empty fake
     *
     * @param id The id
     * @since 1.0.0
     */
    public Fake(final UUID id) {
      this(id, Multi.createFrom().empty(), Multi.createFrom().empty(), new Description.Fake());
    }

    /**
     * Builds a fake
     *
     * @param id          The id
     * @param words       The words
     * @param unique      The  unique word
     * @param description The description
     * @since 1.0.0
     */
    public Fake(
      final UUID id,
      final Multi<Dice<T>> words,
      final Multi<Dice<T>> unique,
      final Description description
    ) {
      this.id = id;
      this.words = words;
      this.unique = unique;
      this.description = description;
    }

    @Override
    public UUID id() {
      return id;
    }

    @Override
    public Multi<Dice<T>> words() {
      return words;
    }

    @Override
    public Multi<Dice<T>> words(final Sheet<T> other) {
      return unique;
    }

    @Override
    public Uni<Void> word(final Dice<T> word) {
      return Uni.createFrom().voidItem();
    }

    @Override
    public Description description() {
      return description;
    }

    private final UUID id;
    private final Multi<Dice<T>> words;
    private final Multi<Dice<T>> unique;
    private final Description description;
  }
}
