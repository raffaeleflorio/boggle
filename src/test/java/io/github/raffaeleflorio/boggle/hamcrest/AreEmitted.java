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
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.time.Duration;
import java.util.List;

public final class AreEmitted<T> extends TypeSafeDiagnosingMatcher<Multi<T>> {
  public AreEmitted(final Matcher<? super List<T>> origin, final Duration duration) {
    this(new IsEmitted<>(origin, duration));
  }

  public AreEmitted(final IsEmitted<? super List<T>> origin) {
    this.origin = origin;
  }

  @Override
  protected boolean matchesSafely(final Multi<T> tMulti, final Description description) {
    var emitted = tMulti.collect().asList();
    var matches = origin.matches(emitted);
    if (!matches) {
      origin.describeMismatch(emitted, description);
    }
    return matches;
  }

  @Override
  public void describeTo(final Description description) {
    origin.describeTo(description);
  }

  public static <T> Matcher<Multi<T>> emits(final Matcher<? super List<T>> matcher) {
    return new AreEmitted<>(matcher, Duration.ofMillis(100));
  }

  public static <T> Matcher<Multi<T>> emits(final Matcher<? super List<T>> matcher, final Duration duration) {
    return new AreEmitted<>(matcher, duration);
  }

  private final Matcher<? extends Uni<? super List<T>>> origin;
}
