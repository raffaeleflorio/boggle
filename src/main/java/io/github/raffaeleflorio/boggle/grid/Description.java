package io.github.raffaeleflorio.boggle.grid;

import java.util.List;

/**
 * A grid description
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Description {
  /**
   * Builds a description with another feature
   *
   * @param id     The feature id
   * @param values The values
   * @return The new description
   */
  Description feature(CharSequence id, List<CharSequence> values);

  /**
   * Builds feature by its id
   *
   * @param id The feature id
   * @return The feature values or an empty list if absent
   */
  List<CharSequence> feature(CharSequence id);

  /**
   * A permanent empty {@link Description}, useful for testing
   *
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  final class Fake implements Description {
    @Override
    public Description feature(final CharSequence id, final List<CharSequence> values) {
      return this;
    }

    @Override
    public List<CharSequence> feature(final CharSequence id) {
      return List.of();
    }
  }
}
