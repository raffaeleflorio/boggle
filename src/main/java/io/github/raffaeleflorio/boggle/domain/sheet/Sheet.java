/*
   Copyright 2021 Raffaele Florio

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package io.github.raffaeleflorio.boggle.domain.sheet;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

/**
 * A sheet
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
      this(id, description, Multi.createFrom().empty());
    }

    /**
     * Builds a fake with random id
     *
     * @param words The words
     * @since 1.0.0
     */
    public Fake(final List<Dice<T>> words) {
      this(UUID.randomUUID(), new Description.Fake(), Multi.createFrom().items(words::stream));
    }

    /**
     * Builds a fake
     *
     * @param id          The id
     * @param description The description
     * @param words       The words
     * @since 1.0.0
     */
    public Fake(final UUID id, final Description description, final Multi<Dice<T>> words) {
      this(id, description, words, x -> Uni.createFrom().voidItem());
    }

    /**
     * Builds a fake
     *
     * @param id          The id
     * @param description The description
     * @param words       The words
     * @param wordFn      The function to build word
     * @since 1.0.0
     */
    public Fake(
      final UUID id,
      final Description description,
      final Multi<Dice<T>> words,
      final Function<Dice<T>, Uni<Void>> wordFn
    ) {
      this.id = id;
      this.description = description;
      this.words = words;
      this.wordFn = wordFn;
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
    public Uni<Void> word(final Dice<T> word) {
      return wordFn.apply(word);
    }

    @Override
    public Description description() {
      return description;
    }

    private final UUID id;
    private final Description description;
    private final Multi<Dice<T>> words;
    private final Function<Dice<T>, Uni<Void>> wordFn;
  }
}
