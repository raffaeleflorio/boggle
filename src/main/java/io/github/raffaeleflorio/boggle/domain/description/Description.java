/*
   Copyright 2021 Raffaele Florio

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package io.github.raffaeleflorio.boggle.domain.description;

import java.util.List;
import java.util.Map;

/**
 * An object description
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
   * {@link Description} useful for testing
   *
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  final class Fake implements Description {
    /**
     * Builds an empty fake
     *
     * @since 1.0.0
     */
    public Fake() {
      this("", List.of());
    }

    /**
     * Builds a fake with a feature with a single value
     *
     * @param name  The feature name
     * @param value The feature value
     * @since 1.0.0
     */
    public Fake(final CharSequence name, final CharSequence value) {
      this(name, List.of(value));
    }

    /**
     * Builds a fake with a feature with multiple values
     *
     * @param name   The feature name
     * @param values The feature values
     * @since 1.0.0
     */
    public Fake(final CharSequence name, final List<CharSequence> values) {
      this(Map.of(name, values));
    }

    /**
     * Builds a fake with multiple feature
     *
     * @param features The features
     * @since 1.0.0
     */
    public Fake(final Map<CharSequence, List<CharSequence>> features) {
      this.features = features;
    }

    @Override
    public List<CharSequence> feature(final CharSequence name) {
      return features.getOrDefault(name, List.of());
    }

    @Override
    public Description feature(final CharSequence name, final List<CharSequence> values) {
      return new Description.Fake(name, values);
    }

    private final Map<CharSequence, List<CharSequence>> features;
  }
}
