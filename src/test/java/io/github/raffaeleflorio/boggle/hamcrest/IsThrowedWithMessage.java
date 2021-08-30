package io.github.raffaeleflorio.boggle.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;


public final class IsThrowedWithMessage extends TypeSafeDiagnosingMatcher<Runnable> {
  public IsThrowedWithMessage(final Class<? extends Throwable> expectedClass, final String expectedMessage) {
    this.expectedClass = expectedClass;
    this.expectedMessage = expectedMessage;
  }

  @Override
  protected boolean matchesSafely(final Runnable runnable, final Description description) {
    try {
      runnable.run();
      description.appendText("none was thrown");
      return false;
    } catch (Throwable t) {
      var tClass = t.getClass();
      var tMessage = t.getMessage();
      var matches = tClass.equals(expectedClass) && tMessage.equals(expectedMessage);
      if (!matches) {
        description.appendText(
          message("was %s with \"%s\"", tClass, tMessage)
        );
      }
      return matches;
    }
  }

  private String message(final String fmt, final Class<? extends Throwable> clazz, final String message) {
    return String.format(fmt, clazz, message);
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText(
      message("%s with \"%s\"", expectedClass, expectedMessage)
    );
  }

  public static Matcher<Runnable> throwsWithMessage(
    final Class<? extends Throwable> expectedClass,
    final String expectedMessage
  ) {
    return new IsThrowedWithMessage(expectedClass, expectedMessage);
  }

  private final Class<? extends Throwable> expectedClass;
  private final String expectedMessage;
}
