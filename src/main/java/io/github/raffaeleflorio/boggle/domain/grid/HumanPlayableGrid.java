package io.github.raffaeleflorio.boggle.domain.grid;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * A grid playable by human
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class HumanPlayableGrid implements Grid<CharSequence> {
  /**
   * Builds a grid
   *
   * @param origin The origin to decorate
   * @since 1.0.0
   */
  public HumanPlayableGrid(final Grid<CharSequence> origin) {
    this(origin, AugmentedDescription::new);
  }

  /**
   * Builds a grid
   *
   * @param origin        The origin to decorate
   * @param descriptionFn The function used to append feature
   * @since 1.0.0
   */
  HumanPlayableGrid(
    final Grid<CharSequence> origin,
    final BiFunction<Description, Map.Entry<CharSequence, List<CharSequence>>, Description> descriptionFn
  ) {
    this.origin = origin;
    this.descriptionFn = descriptionFn;
  }

  @Override
  public Collection<CharSequence> values() {
    return origin.values();
  }

  @Override
  public Grid<CharSequence> shuffled() {
    return new HumanPlayableGrid(origin.shuffled(), descriptionFn);
  }

  @Override
  public Boolean compatible(final Dice<CharSequence> word) {
    return origin.compatible(word);
  }

  @Override
  public Description description() {
    return descriptionFn.apply(
      origin.description(),
      Map.entry("dice", asList(values()))
    );
  }

  private <T> List<T> asList(final Collection<T> values) {
    return values.stream().collect(Collectors.toUnmodifiableList());
  }

  private final Grid<CharSequence> origin;
  private final BiFunction<Description, Map.Entry<CharSequence, List<CharSequence>>, Description> descriptionFn;
}
