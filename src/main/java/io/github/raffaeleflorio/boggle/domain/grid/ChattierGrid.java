package io.github.raffaeleflorio.boggle.domain.grid;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * A more descriptive grid
 *
 * @param <T> The grid mark die type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class ChattierGrid<T> implements Grid<T> {
  /**
   * Builds a grid
   *
   * @param origin   The grid to decorate
   * @param features The new features
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   */
  public ChattierGrid(final Grid<T> origin, final Map<CharSequence, List<CharSequence>> features) {
    this(origin, () -> features, AugmentedDescription::new);
  }

  /**
   * Builds a grid
   *
   * @param origin   The grid to decorate
   * @param features The new features supplier
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   */
  public ChattierGrid(
    final Grid<T> origin,
    final Supplier<Map<CharSequence, List<CharSequence>>> features
  ) {
    this(origin, features, AugmentedDescription::new);
  }

  /**
   * Builds a grid
   *
   * @param origin        The grid to decorate
   * @param features      The new features
   * @param descriptionFn The function to append description
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   */
  ChattierGrid(
    final Grid<T> origin,
    final Supplier<Map<CharSequence, List<CharSequence>>> features,
    final BiFunction<Description, Map<CharSequence, List<CharSequence>>, Description> descriptionFn
  ) {
    this.origin = origin;
    this.features = features;
    this.descriptionFn = descriptionFn;
  }

  @Override
  public Collection<T> values() {
    return origin.values();
  }

  @Override
  public Grid<T> shuffled() {
    return new ChattierGrid<>(origin.shuffled(), features, descriptionFn);
  }

  @Override
  public Boolean compatible(final Dice<T> word) {
    return origin.compatible(word);
  }

  @Override
  public Description description() {
    return descriptionFn.apply(origin.description(), features.get());
  }

  private final Grid<T> origin;
  private final Supplier<Map<CharSequence, List<CharSequence>>> features;
  private final BiFunction<Description, Map<CharSequence, List<CharSequence>>, Description> descriptionFn;
}
