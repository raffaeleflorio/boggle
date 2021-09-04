package io.github.raffaeleflorio.boggle.domain.description;

import java.util.List;
import java.util.Map;

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
   * @since 1.0.0
   */
  List<CharSequence> feature(CharSequence name);

  /**
   * Attaches a new feature
   *
   * @param name   The feature name
   * @param values The feature values
   * @return The new description
   * @since 1.0.0
   */
  Description feature(CharSequence name, List<CharSequence> values);

  /**
   * A {@link Description} with always zero or one feature, useful for testing
   *
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  final class Fake implements Description {
    /**
     * Builds a fake with empty name and empty value
     *
     * @since 1.0.0
     */
    public Fake() {
      this("", List.of());
    }

    /**
     * Builds a fake
     *
     * @param name  The feature name
     * @param value The feature value
     * @since 1.0.0
     */
    public Fake(final CharSequence name, final CharSequence value) {
      this(name, List.of(value));
    }

    /**
     * Builds a fake
     *
     * @param name   The feature name
     * @param values The feature values
     * @since 1.0.0
     */
    public Fake(final CharSequence name, final List<CharSequence> values) {
      this(Map.entry(name, values));
    }

    /**
     * Builds a fake
     *
     * @param feature The feature
     * @since 1.0.0
     */
    public Fake(final Map.Entry<CharSequence, List<CharSequence>> feature) {
      this.feature = feature;
    }

    @Override
    public List<CharSequence> feature(final CharSequence name) {
      return feature.getKey().equals(name) ? feature.getValue() : List.of();
    }

    @Override
    public Description feature(final CharSequence name, final List<CharSequence> values) {
      return new Description.Fake(name, values);
    }

    private final Map.Entry<CharSequence, List<CharSequence>> feature;
  }
}
