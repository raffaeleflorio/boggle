package io.github.raffaeleflorio.boggle.dice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Unordered {@link Dice}, it does not preserve {@link Die} ordering
 *
 * @param <T> The mark side type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
final class UnorderedDice<T> implements Dice<T> {
  /**
   * Builds an unordered dice
   *
   * @param dice The dice
   * @since 1.0.0
   */
  UnorderedDice(final Collection<Die<T>> dice) {
    this(
      dice,
      max -> ThreadLocalRandom.current().nextInt(max)
    );
  }

  /**
   * Builds an unordered dice
   *
   * @param dice     The dice
   * @param randomFn The function to builds bounded random ints
   * @since 1.0.0
   */
  UnorderedDice(final Collection<Die<T>> dice, final Function<Integer, Integer> randomFn) {
    this(dice, randomFn, ArrayList::new);
  }

  /**
   * Builds an unordered dice
   *
   * @param dice     The dice
   * @param randomFn The function to build bounded random ints
   * @param cloneFn  The function to clone list of die
   * @since 1.0.0
   */
  UnorderedDice(
    final Collection<Die<T>> dice,
    final Function<Integer, Integer> randomFn,
    final Function<Collection<Die<T>>, List<Die<T>>> cloneFn
  ) {
    this.dice = dice;
    this.randomFn = randomFn;
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
      var j = randomFn.apply(i + 1);
      x.set(i, x.set(j, x.get(i)));
    }
    return x;
  }

  @Override
  public Dice<T> shuffled() {
    return new UnorderedDice<>(mappedDice(Die::rolled), randomFn);
  }

  private final Collection<Die<T>> dice;
  private final Function<Integer, Integer> randomFn;
  private final Function<Collection<Die<T>>, List<Die<T>>> cloneFn;
}
