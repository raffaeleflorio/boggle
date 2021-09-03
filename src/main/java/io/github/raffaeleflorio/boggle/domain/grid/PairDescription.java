package io.github.raffaeleflorio.boggle.domain.grid;

import java.util.List;

/**
 * A {@link Description} with a single feature
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
final class PairDescription implements Description {
  PairDescription(final CharSequence name, final CharSequence value) {
    this(name, List.of(value));
  }

  PairDescription(final CharSequence name, final List<CharSequence> values) {
    this.name = name;
    this.values = values;
  }

  @Override
  public List<CharSequence> feature(final CharSequence name) {
    return this.name.equals(name) ? values : List.of();
  }

  private final CharSequence name;
  private final List<CharSequence> values;
}
