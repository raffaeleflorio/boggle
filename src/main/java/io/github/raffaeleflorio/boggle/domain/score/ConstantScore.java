package io.github.raffaeleflorio.boggle.domain.score;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.smallrye.mutiny.Uni;

import java.util.function.Function;

/**
 * Emitter of a constant score
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class ConstantScore<T> implements Score<T> {
  /**
   * Builds a constant score of zero
   *
   * @since 1.0.0
   */
  public ConstantScore() {
    this(0);
  }

  /**
   * Builds the score
   *
   * @param score The score
   * @since 1.0.0
   */
  public ConstantScore(final Integer score) {
    this(score, x -> Uni.createFrom().item(x));
  }

  /**
   * Builds the score
   *
   * @param score The score
   * @param uniFn The function to build Uni
   * @since 1.0.0
   */
  ConstantScore(final Integer score, final Function<Integer, Uni<Integer>> uniFn) {
    this.score = score;
    this.uniFn = uniFn;
  }

  @Override
  public Uni<Integer> score(final Dice<T> word) {
    return uniFn.apply(score);
  }

  private final Integer score;
  private final Function<Integer, Uni<Integer>> uniFn;
}
