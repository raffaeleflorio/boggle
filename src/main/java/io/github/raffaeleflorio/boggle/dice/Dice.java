package io.github.raffaeleflorio.boggle.dice;

import java.util.Collection;

/**
 * A group of {@link Die}
 *
 * @param <T> The die mark type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Dice<T> {
  /**
   * Builds the current marked side values
   *
   * @return The current values
   * @since 1.0.0
   */
  Collection<T> values();

  /**
   * Shuffles the dice
   *
   * @return The shuffled dice
   * @since 1.0.0
   */
  Dice<T> shuffled();
}
