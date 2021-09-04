package io.github.raffaeleflorio.boggle.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.function.Supplier;

public final class IsSupplied<T> extends TypeSafeDiagnosingMatcher<Supplier<T>> {
  public IsSupplied(final Matcher<? super T> origin) {
    this.origin = origin;
  }

  @Override
  protected boolean matchesSafely(final Supplier<T> tSupplier, final Description description) {
    var emitted = tSupplier.get();
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

  public static <T> Matcher<Supplier<T>> supplies(final Matcher<? super T> matcher) {
    return new IsSupplied<>(matcher);
  }

  private final Matcher<? super T> origin;
}
