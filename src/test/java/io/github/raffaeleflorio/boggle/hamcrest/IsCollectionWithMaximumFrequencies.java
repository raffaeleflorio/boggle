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
