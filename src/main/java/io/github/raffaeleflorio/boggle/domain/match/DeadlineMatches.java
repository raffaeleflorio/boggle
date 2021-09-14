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
package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.smallrye.mutiny.Uni;

import java.time.Instant;
import java.util.UUID;
import java.util.function.BiFunction;

/**
 * {@link Matches} that understands deadline feature
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class DeadlineMatches<T> implements Matches<T> {
  /**
   * Builds matches
   *
   * @param origin The origin to decorate
   * @since 1.0.0
   */
  public DeadlineMatches(final Matches<T> origin) {
    this(origin, DeadlineMatch::new);
  }

  /**
   * Builds matches
   *
   * @param origin  The origin to decorate
   * @param matchFn The function to build a deadline match
   */
  DeadlineMatches(final Matches<T> origin, final BiFunction<Match<T>, Instant, Match<T>> matchFn) {
    this.origin = origin;
    this.matchFn = matchFn;
  }

  @Override
  public Uni<Match<T>> match(final Description description) {
    return mapped(origin.match(description));
  }

  private Uni<Match<T>> mapped(final Uni<Match<T>> match) {
    return match.onItem().ifNotNull().transform(x -> matchFn.apply(x, deadline(x.description())));
  }

  private Instant deadline(final Description description) {
    return Instant.parse(description.feature("deadline").get(0));
  }

  @Override
  public Uni<Match<T>> match(final UUID id) {
    return mapped(origin.match(id));
  }

  private final Matches<T> origin;
  private final BiFunction<Match<T>, Instant, Match<T>> matchFn;
}
