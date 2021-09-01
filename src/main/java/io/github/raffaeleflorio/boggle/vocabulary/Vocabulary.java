package io.github.raffaeleflorio.boggle.vocabulary;

import java.util.concurrent.Future;

/**
 * Set of words
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Vocabulary {
  /**
   * Builds an asynchronous boolean. True if the vocabulary contains the specified word
   *
   * @param word The word
   * @return The asynchronous boolean
   * @since 1.0.0
   */
  Future<Boolean> contains(CharSequence word);
}
