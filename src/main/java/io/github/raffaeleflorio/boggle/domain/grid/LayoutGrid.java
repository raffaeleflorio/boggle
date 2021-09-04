package io.github.raffaeleflorio.boggle.domain.grid;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A grid tagged with its layout
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class LayoutGrid<T> implements Grid<T> {
  /**
   * Builds a grid
   *
   * @param origin The grid to decorate
   * @since 1.0.0
   */
  public LayoutGrid(final Grid<T> origin) {
    this(
      origin,
      layout -> layout.stream().map(Object::toString).collect(Collectors.toUnmodifiableList())
    );
  }

  /**
   * Builds a grid
   *
   * @param origin   The grid to decorate
   * @param layoutFn A function to build layout tag
   * @since 1.0.0
   */
  public LayoutGrid(final Grid<T> origin, final Function<List<T>, List<CharSequence>> layoutFn) {
    this.origin = origin;
    this.layoutFn = layoutFn;
  }

  @Override
  public List<T> values() {
    return origin.values();
  }

  @Override
  public Grid<T> shuffled() {
    return new LayoutGrid<>(origin.shuffled());
  }

  @Override
  public Boolean compatible(final Dice<T> word) {
    return origin.compatible(word);
  }

  @Override
  public Description description() {
    return origin.description().feature("layout", layoutFn.apply(values()));
  }

  private final Grid<T> origin;
  private final Function<List<T>, List<CharSequence>> layoutFn;
}
