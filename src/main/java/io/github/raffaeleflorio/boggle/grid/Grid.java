package io.github.raffaeleflorio.boggle.grid;

import io.reactivex.rxjava3.core.MaybeSource;

import java.util.Map;

/**
 * A boggle grid
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Grid {
  /**
   * Shuffles the grid
   *
   * @return The shuffled grid
   * @since 1.0.0
   */
  Grid shuffled();

  /**
   * Builds an asynchronous score if the word is contained in the grid
   *
   * @param word The word
   * @return The asynchronous score
   * @since 1.0.0
   */
  MaybeSource<Integer> score(CharSequence word);

  /**
   * Builds the description
   *
   * @return The description
   * @since 1.0.0
   */
  Map<CharSequence, CharSequence> description();
}
