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
package io.github.raffaeleflorio.boggle.domain.sandtimer;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.function.Supplier;

/**
 * A {@link SandTimer} backed by {@link Instant}
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class SimpleSandTimer implements SandTimer {
  /**
   * Builds a sand timer
   *
   * @param duration The sand timer duration
   * @since 1.0.0
   */
  public SimpleSandTimer(final TemporalAmount duration) {
    this(duration, Instant::now);
  }

  /**
   * Builds a sand timer
   *
   * @param duration The sand timer duration
   * @param now      The now supplier
   * @since 1.0.0
   */
  public SimpleSandTimer(final TemporalAmount duration, final Supplier<Instant> now) {
    this(now.get().plus(duration), now);
  }

  /**
   * Builds a sand timer with a deadline
   *
   * @param deadline The deadline
   * @param now      The now supplier
   * @since 1.0.0
   */
  public SimpleSandTimer(final Instant deadline, final Supplier<Instant> now) {
    this.deadline = deadline;
    this.now = now;
  }

  @Override
  public Boolean expired() {
    return now.get().isAfter(deadline);
  }

  private final Instant deadline;
  private final Supplier<Instant> now;
}
