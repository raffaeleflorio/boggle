package io.github.raffaeleflorio.boggle.dice;

/**
 * Rollable object with marked sides
 *
 * @param <T> The mark side type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Die<T> {
  /**
   * Builds the current mark side value
   *
   * @return The current value
   * @since 1.0.0
   */
  T value();

  /**
   * Rolls the die
   *
   * @return The rolled die
   * @since 1.0.0
   */
  Die<T> rolled();
}
