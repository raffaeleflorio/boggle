package io.github.raffaeleflorio.boggle.domain.score;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.smallrye.mutiny.Uni;

/**
 * Constant {@link Score}
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class ConstantScore<T> implements Score<T> {
  /**
   * Builds a constant zero score
   *
   * @since 1.0.0
   */
  public ConstantScore() {
    this(0);
  }

  /**
   * Builds a constant score
   *
   * @param score The score
   * @since 1.0.0
   */
  public ConstantScore(final Integer score) {
    this(Uni.createFrom().item(score));
  }

  private ConstantScore(final Uni<Integer> origin) {
    this.origin = origin;
  }

  @Override
  public Uni<Integer> score(final Dice<T> word) {
    return origin;
  }

  private final Uni<Integer> origin;
}
