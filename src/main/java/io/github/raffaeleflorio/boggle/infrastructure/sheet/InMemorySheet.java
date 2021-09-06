package io.github.raffaeleflorio.boggle.infrastructure.sheet;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.*;
import java.util.function.Function;

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
    this(
      description,
      words,
      equalityCmp,
      list -> Collections.synchronizedSet(new HashSet<>(list))
    );
  }

  /**
   * Builds an in memory sheet
   *
   * @param description The description
   * @param words       The word set
   * @param equalityCmp The equality comparator
   * @param cloneFn     The function to clone set
   * @since 1.0.0
   */
  InMemorySheet(
    final Description description,
    final Set<Dice<T>> words,
    final Comparator<Dice<T>> equalityCmp,
    final Function<List<Dice<T>>, Set<Dice<T>>> cloneFn
  ) {
    this.description = description;
    this.words = words;
    this.equalityCmp = equalityCmp;
    this.cloneFn = cloneFn;
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
  public Uni<Sheet<T>> diff(final Sheet<T> other) {
    return uniqueWords(other)
      .onItem().transform(words -> new InMemorySheet<>(description, words, equalityCmp, cloneFn));
  }

  private Uni<Set<Dice<T>>> uniqueWords(final Sheet<T> other) {
    return words().select().when(myWord -> other
      .words().map(otherWord -> equals(myWord, otherWord))
      .filter(Boolean::booleanValue).collect().first()
      .onItem().ifNotNull().transform(x -> false)
      .onItem().ifNull().continueWith(true)
    ).collect().asList().onItem().transform(cloneFn);
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
  private final Function<List<Dice<T>>, Set<Dice<T>>> cloneFn;
}
