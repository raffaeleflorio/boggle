package io.github.raffaeleflorio.boggle.infrastructure.sheet;

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
   * @param id The id
   * @since 1.0.0
   */
  InMemorySheet(final UUID id) {
    this(
      id,
      Collections.synchronizedSet(new HashSet<>())
    );
  }

  /**
   * Builds an in memory sheet
   *
   * @param id    The id
   * @param words The word set
   * @since 1.0.0
   */
  InMemorySheet(final UUID id, final Set<Dice<T>> words) {
    this(id, words, (o1, o2) -> o1.values().equals(o2.values()) ? 0 : 1);
  }

  /**
   * Builds an in memory sheet
   *
   * @param id          The id
   * @param words       The word set
   * @param equalityCmp The equality comparator
   * @since 1.0.0
   */
  InMemorySheet(final UUID id, final Set<Dice<T>> words, final Comparator<Dice<T>> equalityCmp) {
    this.id = id;
    this.words = words;
    this.equalityCmp = equalityCmp;
  }

  @Override
  public UUID id() {
    return id;
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

  private final UUID id;
  private final Set<Dice<T>> words;
  private final Comparator<Dice<T>> equalityCmp;
}
