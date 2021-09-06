package io.github.raffaeleflorio.boggle.domain.sheet;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.List;
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
   * A {@link Sheet} useful for testing
   *
   * @param <T> The word type
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
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
      this(id, new Description.Fake());
    }

    /**
     * Builds an empty fake
     *
     * @param id          The id
     * @param description The description
     * @since 1.0.0
     */
    public Fake(final UUID id, final Description description) {
      this(id, description, List.of());
    }

    /**
     * Builds a fake with random id
     *
     * @param words  The words
     * @param unique The unique words
     * @since 1.0.0
     */
    public Fake(final List<Dice<T>> words) {
      this(UUID.randomUUID(), new Description.Fake(), words);
    }

    /**
     * Builds a fake
     *
     * @param id          The id
     * @param description The description
     * @param words       The words
     * @since 1.0.0
     */
    public Fake(final UUID id, final Description description, final List<Dice<T>> words) {
      this.id = id;
      this.words = words;
      this.description = description;
    }

    @Override
    public UUID id() {
      return id;
    }

    @Override
    public Multi<Dice<T>> words() {
      return multi(words);
    }

    private Multi<Dice<T>> multi(final List<Dice<T>> words) {
      return Multi.createFrom().items(words::stream);
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
    private final Description description;
    private final List<Dice<T>> words;
  }
}
