package io.github.raffaeleflorio.boggle.domain.grid;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A {@link Grid} with layout feature
 *
 * @param <T> The word type
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
    this(origin, Object::toString);
  }

  /**
   * Builds a grid with
   *
   * @param origin         The grid to decorate
   * @param charSequenceFn The function to build char sequence from word type
   * @since 1.0.0
   */
  public LayoutGrid(final Grid<T> origin, final Function<T, CharSequence> charSequenceFn) {
    this.origin = origin;
    this.charSequenceFn = charSequenceFn;
  }

  @Override
  public List<T> values() {
    return origin.values();
  }

  @Override
  public Grid<T> shuffled() {
    return new LayoutGrid<>(origin.shuffled(), charSequenceFn);
  }

  @Override
  public Boolean compatible(final Dice<T> word) {
    return origin.compatible(word);
  }

  @Override
  public Description description() {
    return origin.description().feature("layout", layout());
  }

  private List<CharSequence> layout() {
    return origin.values().stream().map(charSequenceFn).collect(Collectors.toUnmodifiableList());
  }

  private final Grid<T> origin;
  private final Function<T, CharSequence> charSequenceFn;
}
