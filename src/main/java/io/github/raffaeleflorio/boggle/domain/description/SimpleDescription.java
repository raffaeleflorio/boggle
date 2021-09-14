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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * An immutable {@link Description} that eventually overwrites existing feature
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class SimpleDescription implements Description {
  /**
   * Builds an empty description
   *
   * @since 1.0.0
   */
  public SimpleDescription() {
    this(Map.of(), HashMap::new);
  }

  /**
   * Builds a description
   *
   * @param features The backed map
   * @param cloneFn  The function to clone a map
   * @since 1.0.0
   */
  SimpleDescription(
    final Map<CharSequence, List<CharSequence>> features,
    final Function<Map<CharSequence, List<CharSequence>>, Map<CharSequence, List<CharSequence>>> cloneFn
  ) {
    this.features = features;
    this.cloneFn = cloneFn;
  }

  @Override
  public List<CharSequence> feature(final CharSequence name) {
    return features.getOrDefault(name, List.of());
  }

  @Override
  public Description feature(final CharSequence name, final List<CharSequence> values) {
    var clone = cloneFn.apply(features);
    clone.put(name, values);
    return new SimpleDescription(clone, cloneFn);
  }

  private final Map<CharSequence, List<CharSequence>> features;
  private final Function<Map<CharSequence, List<CharSequence>>, Map<CharSequence, List<CharSequence>>> cloneFn;
}
