package io.github.raffaeleflorio.boggle.domain.score;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.vocabulary.Vocabulary;
import io.smallrye.mutiny.Uni;

/**
 * {@link Score} function that also verify word against a {@link Vocabulary}
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
    this(origin, Uni.createFrom().item(vocabulary));
  }

  /**
   * Builds a score
   *
   * @param origin     The score to decorate
   * @param vocabulary The vocabulary emitter to use
   * @since 1.0.0
   */
  public IfInVocabulary(final Score<T> origin, final Uni<Vocabulary<T>> vocabulary) {
    this(origin, vocabulary, 0);
  }

  /**
   * Builds a score
   *
   * @param origin     The score to decorate
   * @param vocabulary The vocabulary emitter to use
   * @param def        The score emitter when a word is not in vocabulary
   * @since 1.0.0
   */
  public IfInVocabulary(final Score<T> origin, final Uni<Vocabulary<T>> vocabulary, final Integer def) {
    this(origin, vocabulary, Uni.createFrom().item(def));
  }

  /**
   * Builds a score
   *
   * @param origin     The score to decorate
   * @param vocabulary The vocabulary emitter to use
   * @param def        The score emitter when a word is not in vocabulary
   * @since 1.0.0
   */
  public IfInVocabulary(final Score<T> origin, final Uni<Vocabulary<T>> vocabulary, final Uni<Integer> def) {
    this.origin = origin;
    this.vocabulary = vocabulary;
    this.def = def;
  }

  @Override
  public Uni<Integer> score(final Dice<T> word) {
    return validWord(word).chain(validWord -> validWord ? origin.score(word) : def);
  }

  private Uni<Boolean> validWord(final Dice<T> word) {
    return vocabulary.chain(x -> x.contains(word));
  }

  private final Score<T> origin;
  private final Uni<Vocabulary<T>> vocabulary;
  private final Uni<Integer> def;
}
