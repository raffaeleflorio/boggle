package io.github.raffaeleflorio.boggle.domain.grid;

import java.util.List;
import java.util.Map;

/**
 * An augmented {@link Description}
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
final class AugmentedDescription implements Description {
  AugmentedDescription(final Description origin, final Map.Entry<CharSequence, List<CharSequence>> augmented) {
    this(origin, Map.of(augmented.getKey(), augmented.getValue()));
  }

  AugmentedDescription(final Description origin, final Map<CharSequence, List<CharSequence>> augmented) {
    this.origin = origin;
    this.augmented = augmented;
  }

  @Override
  public List<CharSequence> feature(final CharSequence name) {
    return augmented.containsKey(name) ? augmented.get(name) : origin.feature(name);
  }

  private final Description origin;
  private final Map<CharSequence, List<CharSequence>> augmented;
}
