package io.github.raffaeleflorio.boggle.hamcrest;

import io.smallrye.mutiny.Uni;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.time.Duration;

public final class IsEmitted<T> extends TypeSafeDiagnosingMatcher<Uni<T>> {
  public IsEmitted(final Matcher<? super T> origin, final Duration duration) {
    this.origin = origin;
    this.duration = duration;
  }

  @Override
  protected boolean matchesSafely(final Uni<T> tUni, final Description description) {
    var emitted = tUni.await().atMost(duration);
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

  public static <T> Matcher<Uni<T>> emits(final Matcher<? super T> matcher) {
    return new IsEmitted<>(matcher, Duration.ofMillis(100));
  }

  public static <T> Matcher<Uni<T>> emits(final Matcher<? super T> matcher, final Duration duration) {
    return new IsEmitted<>(matcher, duration);
  }

  private final Matcher<? super T> origin;
  private final Duration duration;
}
