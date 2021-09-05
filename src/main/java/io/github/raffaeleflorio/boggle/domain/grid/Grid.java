package io.github.raffaeleflorio.boggle.domain.grid;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;

import java.util.List;
import java.util.function.Predicate;

/**
 * A boggle grid
 *
 * @param <T> The grid dice mark type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Grid<T> extends Dice<T> {
  /**
   * Shuffles the grid
   *
   * @return The shuffled grid
   * @since 1.0.0
   */
  @Override
  Grid<T> shuffled();

  /**
   * Builds a true boolean if the grid is compatible with the word
   *
   * @param word The word
   * @return True if compatible
   * @since 1.0.0
   */
  Boolean compatible(Dice<T> word);

  /**
   * Builds the description
   *
   * @return The description
   * @since 1.0.0
   */
  Description description();

  /**
   * A {@link Grid} useful for testing
   *
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  final class Fake<T> implements Grid<T> {
    /**
     * Builds a fake
     *
     * @since 1.0.0
     */
    public Fake() {
      this(new Dice.Fake<>());
    }

    /**
     * Builds a fake
     *
     * @param dice The dice
     * @since 1.0.0
     */
    public Fake(final Dice<T> dice) {
      this(dice, x -> false);
    }

    /**
     * Builds a fake
     *
     * @param dice         The dice
     * @param compatibleFn The function to build compatibility
     * @since 1.0.0
     */
    public Fake(final Dice<T> dice, final Predicate<Dice<T>> compatibleFn) {
      this(dice, compatibleFn, new Description.Fake("id", "fake grid"));
    }

    /**
     * Builds a fake
     *
     * @param compatibleFn The function to build compatibility
     * @since 1.0.0
     */
    public Fake(final Predicate<Dice<T>> compatibleFn) {
      this(new Dice.Fake<>(), compatibleFn);
    }

    /**
     * Builds a fake
     *
     * @param dice         The dice
     * @param compatibleFn The function to build compatibility
     * @param description  The description
     * @since 1.0.0
     */
    public Fake(
      final Dice<T> dice,
      final Predicate<Dice<T>> compatibleFn,
      final Description description
    ) {
      this.dice = dice;
      this.compatibleFn = compatibleFn;
      this.description = description;
    }

    @Override
    public List<T> values() {
      return dice.values();
    }

    @Override
    public Grid<T> shuffled() {
      return new Grid.Fake<>(dice.shuffled(), compatibleFn, description);
    }

    @Override
    public Boolean compatible(final Dice<T> word) {
      return compatibleFn.test(word);
    }

    @Override
    public Description description() {
      return description;
    }

    private final Dice<T> dice;
    private final Predicate<Dice<T>> compatibleFn;
    private final Description description;
  }
}
