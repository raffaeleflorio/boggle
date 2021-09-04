package io.github.raffaeleflorio.boggle.infrastructure.sheet;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.*;

/**
 * An in memory {@link io.github.raffaeleflorio.boggle.domain.sheet.Sheet} implementation
 *
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
    this(description, words, (o1, o2) -> o1.values().equals(o2.values()) ? 0 : 1);
  }

  /**
   * Builds an in memory sheet
   *
   * @param description The description
   * @param words       The word set
   * @param equalityCmp The equality comparator
   * @since 1.0.0
   */
  InMemorySheet(final Description description, final Set<Dice<T>> words, final Comparator<Dice<T>> equalityCmp) {
    this.description = description;
    this.words = words;
    this.equalityCmp = equalityCmp;
  }

  @Override
  public UUID id() {
    return UUID.fromString(description.feature("id").get(0).toString());
  }

  @Override
  public Multi<Dice<T>> words() {
    return Multi.createFrom().items(words::stream).select().distinct(equalityCmp);
  }

  @Override
  public Multi<Dice<T>> words(final Sheet<T> other) {
    return words().select().when(myWord -> other
      .words().map(otherWord -> equals(myWord, otherWord))
      .filter(Boolean::booleanValue).collect().first()
      .onItem().ifNotNull().transform(x -> false)
      .onItem().ifNull().continueWith(true)
    );
  }

  private Boolean equals(final Dice<T> one, final Dice<T> two) {
    return equalityCmp.compare(one, two) == 0;
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
  private final Comparator<Dice<T>> equalityCmp;
}
