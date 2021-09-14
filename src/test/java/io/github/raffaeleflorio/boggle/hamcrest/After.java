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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.concurrent.TimeUnit;

public final class After<T> extends TypeSafeDiagnosingMatcher<T> {
  public After(final Matcher<T> origin, final Long duration, final TimeUnit unit) {
    this.origin = origin;
    this.duration = duration;
    this.unit = unit;
  }

  @Override
  protected boolean matchesSafely(final T t, final Description description) {
    try {
      unit.sleep(duration);
      var matches = origin.matches(t);
      if (!matches) {
        origin.describeMismatch(t, description);
      }
      return matches;
    } catch (InterruptedException e) {
      description.appendText(String.format("was thrown %s", e));
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
  }

  @Override
  public void describeTo(final Description description) {
    origin.describeTo(description);
    description
      .appendText(
        String.format(
          " after %s %s",
          duration,
          unit.toString()
        )
      );
  }

  public static <T> Matcher<T> after(final Integer duration, final TimeUnit unit, final Matcher<T> origin) {
    return new After<>(origin, duration.longValue(), unit);
  }

  public static <T> Matcher<T> after(final Long duration, final TimeUnit unit, final Matcher<T> origin) {
    return new After<>(origin, duration, unit);
  }

  private final Matcher<T> origin;
  private final Long duration;
  private final TimeUnit unit;
}
