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
 * {@link Matches} with score assigned to unique words
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
   * @param rules  The rules
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
   * @param rules   The rules
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
      .onItem().ifNotNull().transformToUni(rule -> ruledMatch(rule, description));
  }

  private Uni<Function<Match<T>, Score<T>>> rule(final Description description) {
    return Multi.createFrom().iterable(rules.entrySet())
      .filter(entry -> entry.getKey().test(description)).collect().first()
      .onItem().ifNotNull().transform(Map.Entry::getValue);
  }

  private Uni<Match<T>> ruledMatch(final Function<Match<T>, Score<T>> rule, final Description description) {
    return origin
      .match(description)
      .onItem()
      .transform(match -> matchFn.apply(match, rule.apply(match)));
  }

  @Override
  public Uni<Match<T>> match(final UUID id) {
    return origin
      .match(id)
      .onItem().ifNotNull().transformToUni(match ->
        rule(match.description()).onItem().transform(rule -> matchFn.apply(match, rule.apply(match)))
      );
  }

  private final Matches<T> origin;
  private final Map<Predicate<Description>, Function<Match<T>, Score<T>>> rules;
  private final BiFunction<Match<T>, Score<T>, Match<T>> matchFn;
}
