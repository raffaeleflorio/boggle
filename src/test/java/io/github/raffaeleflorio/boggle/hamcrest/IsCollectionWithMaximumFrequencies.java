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
import org.hamcrest.TypeSafeMatcher;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public final class IsCollectionWithMaximumFrequencies<T> extends TypeSafeMatcher<Collection<T>> {
  public IsCollectionWithMaximumFrequencies(final Map<T, Long> frequencies) {
    this.frequencies = frequencies;
  }

  @Override
  protected boolean matchesSafely(final Collection<T> ts) {
    return ts
      .stream()
      .map(x -> Map.entry(x, Collections.frequency(ts, x)))
      .allMatch(entry -> frequencies.getOrDefault(entry.getKey(), -1L) >= entry.getValue());
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText(
      String.format(
        "maximum frequencies: %s",
        frequencies
      )
    );
  }

  public static <X> Matcher<Collection<X>> hasMaximumFrequencies(final Map<X, Long> frequencies) {
    return new IsCollectionWithMaximumFrequencies<>(frequencies);
  }

  private final Map<T, Long> frequencies;
}
