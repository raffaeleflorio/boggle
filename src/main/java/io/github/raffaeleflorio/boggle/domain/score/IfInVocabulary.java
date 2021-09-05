package io.github.raffaeleflorio.boggle.domain.score;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.vocabulary.Vocabulary;
import io.smallrye.mutiny.Uni;

/**
 * A {@link Score} emitted if the word is in a vocabulary
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class IfInVocabulary<T> implements Score<T> {
  /**
   * Builds a score
   *
   * @param origin     The score to decorate
   * @param vocabulary The vocabulary to use
   * @since 1.0.0
   */
  public IfInVocabulary(final Score<T> origin, final Vocabulary<T> vocabulary) {
    this(origin, vocabulary, 0);
  }

  /**
   * Builds a score
   *
   * @param origin     The score to decorate
   * @param vocabulary The vocabulary to use
   * @param def        The score to emit when not in vocabulary
   * @since 1.0.0
   */
  IfInVocabulary(final Score<T> origin, final Vocabulary<T> vocabulary, final Integer def) {
    this(origin, vocabulary, Uni.createFrom().item(def));
  }

  /**
   * Builds a score
   *
   * @param origin     The score to decorate
   * @param vocabulary The vocabulary to use
   * @param def        The score to emit when not in vocabulary
   * @since 1.0.0
   */
  IfInVocabulary(final Score<T> origin, final Vocabulary<T> vocabulary, final Uni<Integer> def) {
    this.origin = origin;
    this.vocabulary = vocabulary;
    this.def = def;
  }

  @Override
  public Uni<Integer> score(final Dice<T> word) {
    return vocabulary.contains(word)
      .chain(x -> x ? origin.score(word) : def);
  }

  private final Score<T> origin;
  private final Vocabulary<T> vocabulary;
  private final Uni<Integer> def;
}
