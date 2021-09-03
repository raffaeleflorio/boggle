package io.github.raffaeleflorio.boggle.domain.dice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Unordered {@link Dice}, it does not preserve {@link Die} ordering
 *
 * @param <T> The mark side type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class UnorderedDice<T> implements Dice<T> {
  /**
   * Builds a non cryptographically strong dice
   *
   * @param dice The dice
   * @since 1.0.0
   */
  public UnorderedDice(final Collection<Die<T>> dice) {
    this(
      dice,
      (min, bound) -> new RandomDie<>(Function.identity(), min, bound)
    );
  }

  /**
   * Builds an unordered dice
   *
   * @param dice  The dice
   * @param dieFn The function to builds a bounded die
   * @since 1.0.0
   */
  public UnorderedDice(final Collection<Die<T>> dice, final BiFunction<Integer, Integer, Die<Integer>> dieFn) {
    this(dice, dieFn, ArrayList::new);
  }

  /**
   * Builds an unordered dice
   *
   * @param dice    The dice
   * @param dieFn   The function to build bounded die
   * @param cloneFn The function to clone list of die
   * @since 1.0.0
   */
  UnorderedDice(
    final Collection<Die<T>> dice,
    final BiFunction<Integer, Integer, Die<Integer>> dieFn,
    final Function<Collection<Die<T>>, List<Die<T>>> cloneFn
  ) {
    this.dice = dice;
    this.dieFn = dieFn;
    this.cloneFn = cloneFn;
  }

  @Override
  public Collection<T> values() {
    return mappedDice(Die::value);
  }

  private <D> List<D> mappedDice(final Function<Die<T>, D> map) {
    return shuffledDice().stream().map(map).collect(Collectors.toUnmodifiableList());
  }

  private List<Die<T>> shuffledDice() {
    var x = cloneFn.apply(dice);
    for (var i = dice.size() - 1; i > 0; i--) {
      var j = rolledDie(i + 1).value();
      x.set(i, x.set(j, x.get(i)));
    }
    return x;
  }

  private Die<Integer> rolledDie(final Integer bound) {
    return dieFn.apply(0, bound).rolled();
  }

  @Override
  public Dice<T> shuffled() {
    return new UnorderedDice<>(mappedDice(Die::rolled), dieFn);
  }

  private final Collection<Die<T>> dice;
  private final BiFunction<Integer, Integer, Die<Integer>> dieFn;
  private final Function<Collection<Die<T>>, List<Die<T>>> cloneFn;
}
