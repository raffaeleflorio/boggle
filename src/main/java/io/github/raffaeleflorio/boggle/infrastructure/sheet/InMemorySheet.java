package io.github.raffaeleflorio.boggle.infrastructure.sheet;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * In memory {@link Sheet} implementation
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
final class InMemorySheet<T> implements Sheet<T> {
  /**
   * Builds an in memory sheet
   *
   * @param description The description
   * @since 1.0.0
   */
  InMemorySheet(final Description description) {
    this(
      description,
      Collections.synchronizedSet(new HashSet<>())
    );
  }

  /**
   * Builds an in memory sheet
   *
   * @param description The description
   * @param words       The word set
   * @since 1.0.0
   */
  InMemorySheet(final Description description, final Set<Dice<T>> words) {
    this.description = description;
    this.words = words;
  }

  @Override
  public UUID id() {
    return UUID.fromString(description.feature("id").get(0).toString());
  }

  @Override
  public Multi<Dice<T>> words() {
    return Multi.createFrom().items(words::stream);
  }

  @Override
  public Uni<Void> word(final Dice<T> word) {
    words.add(word);
    return Uni.createFrom().voidItem();
  }

  @Override
  public Description description() {
    return description;
  }

  private final Description description;
  private final Set<Dice<T>> words;
}
