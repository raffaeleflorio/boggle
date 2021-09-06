package io.github.raffaeleflorio.boggle.hamcrest;

import io.smallrye.mutiny.Uni;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.time.Duration;

public final class IsEmittedFailure<T> extends TypeSafeMatcher<Uni<T>> {
  public IsEmittedFailure(
    final Class<? extends Throwable> expectedClass,
    final String expectedMessage,
    final Duration duration
  ) {
    this(
      new IsThrowedWithMessage(expectedClass, expectedMessage),
      duration
    );
  }

  public IsEmittedFailure(final Matcher<Runnable> origin, final Duration duration) {
    this.origin = origin;
    this.duration = duration;
  }

  @Override
  protected boolean matchesSafely(final Uni<T> tUni) {
    Runnable runnable = () -> tUni.await().atMost(duration);
    return origin.matches(runnable);
  }

  @Override
  public void describeTo(final Description description) {
    origin.describeTo(description);
  }

  public static <T> Matcher<Uni<T>> emits(final Class<? extends Throwable> expectedClass, final String expectedMessage) {
    return new IsEmittedFailure<>(expectedClass, expectedMessage, Duration.ofMillis(100));
  }

  public static <T> Matcher<Uni<T>> emits(
    final Class<? extends Throwable> expectedClass,
    final String expectedMessage,
    final Duration duration
  ) {
    return new IsEmittedFailure<>(expectedClass, expectedMessage, duration);
  }

  private final Matcher<Runnable> origin;
  private final Duration duration;
}
