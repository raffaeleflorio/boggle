package io.github.raffaeleflorio.boggle.domain.grid;

import java.util.List;

/**
 * A grid description
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Description {
  /**
   * Builds feature by its name
   *
   * @param name The feature name
   * @return The feature values or an empty list if absent
   */
  List<CharSequence> feature(CharSequence name);

  /**
   * A permanent empty {@link Description}, useful for testing
   *
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  final class Fake implements Description {
    @Override
    public List<CharSequence> feature(final CharSequence name) {
      return List.of();
    }
  }
}
