package io.github.raffaeleflorio.boggle.hamcrest;

import io.smallrye.mutiny.Multi;
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
    this(
      new IsThrowedWithMessage(expectedClass, expectedMessage),
      duration
    );
  }

  public AreEmittedFailure(final Matcher<Runnable> origin, final Duration duration) {
    this.origin = origin;
    this.duration = duration;
  }

  @Override
  protected boolean matchesSafely(final Multi<T> tMulti) {
    Runnable runnable = () -> tMulti.collect().asList().await().atMost(duration);
    return origin.matches(runnable);
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

  private final Matcher<Runnable> origin;
  private final Duration duration;
}
