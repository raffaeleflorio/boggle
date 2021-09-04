package io.github.raffaeleflorio.boggle.hamcrest;

import io.smallrye.mutiny.Multi;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.time.Duration;
import java.util.List;

public final class AreEmitted<T> extends TypeSafeDiagnosingMatcher<Multi<T>> {
  public AreEmitted(final Matcher<? super List<T>> origin, final Duration duration) {
    this.origin = origin;
    this.duration = duration;
  }

  @Override
  protected boolean matchesSafely(final Multi<T> tMulti, final Description description) {
    var emitted = tMulti.collect().asList();
    var isEmitted = new IsEmitted<>(origin, duration);
    var matches = isEmitted.matches(emitted);
    if (!matches) {
      isEmitted.describeMismatch(emitted, description);
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

  private final Matcher<? super List<T>> origin;
  private final Duration duration;
}
