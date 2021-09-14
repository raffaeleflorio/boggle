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
package io.github.raffaeleflorio.boggle.hamcrest;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.time.Duration;

public final class AreEmittedFailure<T> extends TypeSafeMatcher<Multi<T>> {
  public AreEmittedFailure(
    final Class<? extends Throwable> expectedClass,
    final String expectedMessage,
    final Duration duration
  ) {
    this(new IsEmittedFailure<>(expectedClass, expectedMessage, duration));
  }

  public AreEmittedFailure(Matcher<? extends Uni<? extends Runnable>> origin) {
    this.origin = origin;
  }

  @Override
  protected boolean matchesSafely(final Multi<T> tMulti) {
    return origin.matches(tMulti.collect().asList());
  }

  @Override
  public void describeTo(final Description description) {
    origin.describeTo(description);
  }

  public static <T> Matcher<Multi<T>> emits(final Class<? extends Throwable> expectedClass, final String expectedMessage) {
    return new AreEmittedFailure<>(expectedClass, expectedMessage, Duration.ofMillis(100));
  }

  public static <T> Matcher<Multi<T>> emits(
    final Class<? extends Throwable> expectedClass,
    final String expectedMessage,
    final Duration duration
  ) {
    return new AreEmittedFailure<>(expectedClass, expectedMessage, duration);
  }

  private final Matcher<? extends Uni<? extends Runnable>> origin;
}
