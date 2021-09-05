package io.github.raffaeleflorio.boggle.domain.description;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * A predicate about feature of a description
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class FeaturePredicate implements Predicate<Description> {
  /**
   * Builds a predicate
   *
   * @param expected The expected feature name to values map
   * @since 1.0.0
   */
  public FeaturePredicate(final Map<CharSequence, List<CharSequence>> expected) {
    this.expected = expected;
  }

  @Override
  public boolean test(final Description description) {
    return expected.entrySet()
      .stream()
      .allMatch(entry -> description.feature(entry.getKey()).equals(entry.getValue()));
  }

  private final Map<CharSequence, List<CharSequence>> expected;
}
