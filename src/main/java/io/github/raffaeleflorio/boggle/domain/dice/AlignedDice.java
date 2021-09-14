package io.github.raffaeleflorio.boggle.domain.dice;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Aligned {@link Dice}
 *
 * @param <T> The mark type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class AlignedDice<T> implements Dice<T> {
  /**
   * Builds an aligned dice
   *
   * @param dice The dice
   * @since 1.0.0
   */
  public AlignedDice(final List<Die<T>> dice) {
    this.dice = dice;
  }

  @Override
  public List<T> values() {
    return mappedDice(Die::value);
  }

  private <D> List<D> mappedDice(final Function<Die<T>, D> map) {
    return dice.stream().map(map).collect(Collectors.toUnmodifiableList());
  }

  @Override
  public Dice<T> shuffled() {
    return new AlignedDice<>(mappedDice(Die::rolled));
  }

  private final List<Die<T>> dice;
}
