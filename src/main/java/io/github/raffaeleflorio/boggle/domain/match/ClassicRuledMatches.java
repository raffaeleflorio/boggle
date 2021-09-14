package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.score.Score;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * {@link Matches} with score assigned only to unique words
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class ClassicRuledMatches<T> implements Matches<T> {
  /**
   * Builds matches
   *
   * @param origin The matches to decorate
   * @param rules  The rules per match description
   * @since 1.0.0
   */
  public ClassicRuledMatches(
    final Matches<T> origin,
    final Map<Predicate<Description>, Function<Match<T>, Score<T>>> rules
  ) {
    this(origin, rules, ClassicRuledMatch::new);
  }

  /**
   * Builds matches
   *
   * @param origin  The matches to decorate
   * @param rules   The rules per match description
   * @param matchFn The function to build match
   * @since 1.0.0
   */
  ClassicRuledMatches(
    final Matches<T> origin,
    final Map<Predicate<Description>, Function<Match<T>, Score<T>>> rules,
    final BiFunction<Match<T>, Score<T>, Match<T>> matchFn
  ) {
    this.origin = origin;
    this.rules = rules;
    this.matchFn = matchFn;
  }

  @Override
  public Uni<Match<T>> match(final Description description) {
    return rule(description)
      .onItem().ifNotNull().transformToUni(rule -> origin.match(description)
        .onItem().ifNotNull().transform(match -> ruledMatch(rule, match)));
  }

  private Uni<Map.Entry<Predicate<Description>, Function<Match<T>, Score<T>>>> rule(final Description description) {
    return rules()
      .filter(rule -> rule.getKey().test(description)).collect().first();
  }

  private Multi<Map.Entry<Predicate<Description>, Function<Match<T>, Score<T>>>> rules() {
    return Multi.createFrom().items(rules.entrySet()::stream);
  }

  private Match<T> ruledMatch(
    final Map.Entry<Predicate<Description>, Function<Match<T>, Score<T>>> rule,
    final Match<T> match
  ) {
    return matchFn.apply(match, rule.getValue().apply(match));
  }

  @Override
  public Uni<Match<T>> match(final UUID id) {
    return origin.match(id)
      .onItem().ifNotNull().transformToUni(match -> rule(match.description())
        .onItem().ifNotNull().transform(rule -> ruledMatch(rule, match)));
  }

  private final Matches<T> origin;
  private final Map<Predicate<Description>, Function<Match<T>, Score<T>>> rules;
  private final BiFunction<Match<T>, Score<T>, Match<T>> matchFn;
}
