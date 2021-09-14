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
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * A {@link Predicate} about {@link Description} feature values
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class FeatureEqualityPredicate implements Predicate<Description> {
  /**
   * Builds a predicate
   *
   * @param expected The expected feature name to values map
   * @since 1.0.0
   */
  public FeatureEqualityPredicate(final Map<CharSequence, List<CharSequence>> expected) {
    this.expected = expected;
  }

  @Override
  public boolean test(final Description description) {
    return expectedFeatures().allMatch(containedIn(description));
  }

  private Stream<Map.Entry<CharSequence, List<CharSequence>>> expectedFeatures() {
    return expected.entrySet().stream();
  }

  private Predicate<Map.Entry<CharSequence, List<CharSequence>>> containedIn(final Description description) {
    return feature -> description.feature(feature.getKey()).equals(feature.getValue());
  }

  private final Map<CharSequence, List<CharSequence>> expected;
}
