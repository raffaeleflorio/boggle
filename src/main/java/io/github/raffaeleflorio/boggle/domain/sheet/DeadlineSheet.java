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
import io.github.raffaeleflorio.boggle.domain.sandtimer.SandTimer;
import io.github.raffaeleflorio.boggle.domain.sandtimer.SimpleSandTimer;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A {@link Sheet} with a deadline
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class DeadlineSheet<T> implements Sheet<T> {
  /**
   * Builds a sheet
   *
   * @param origin   The sheet to decorate
   * @param deadline The deadline
   * @since 1.0.0
   */
  public DeadlineSheet(final Sheet<T> origin, final Instant deadline) {
    this(origin, new SimpleSandTimer(deadline, Instant::now));
  }

  /**
   * Builds a deadline sheet with a {@link SandTimer}
   *
   * @param origin    The sheet to decorate
   * @param sandTimer The sand timer
   * @since 1.0.0
   */
  public DeadlineSheet(final Sheet<T> origin, final SandTimer sandTimer) {
    this(origin, sandTimer, msg -> new IllegalStateException(msg.toString()));
  }

  /**
   * Builds a deadline sheet with a sand timer and a custom exception
   *
   * @param origin      The sheet to decorate
   * @param sandTimer   The sand timer
   * @param exceptionFn The function to build the exception to throw
   * @since 1.0.0
   */
  public DeadlineSheet(
    final Sheet<T> origin,
    final SandTimer sandTimer,
    final Function<CharSequence, RuntimeException> exceptionFn
  ) {
    this.origin = origin;
    this.sandTimer = sandTimer;
    this.exceptionFn = exceptionFn;
  }

  @Override
  public UUID id() {
    return origin.id();
  }

  @Override
  public Multi<Dice<T>> words() {
    return origin.words();
  }

  @Override
  public Uni<Void> word(final Dice<T> word) {
    return beforeExpiration(() -> origin.word(word));

  }

  private <X> Uni<X> beforeExpiration(final Supplier<Uni<X>> target) {
    if (sandTimer.expired()) {
      return Uni.createFrom().failure(exceptionFn.apply("Deadline reached"));
    }
    return target.get();
  }

  @Override
  public Description description() {
    return origin.description();
  }

  private final Sheet<T> origin;
  private final SandTimer sandTimer;
  private final Function<CharSequence, RuntimeException> exceptionFn;
}
