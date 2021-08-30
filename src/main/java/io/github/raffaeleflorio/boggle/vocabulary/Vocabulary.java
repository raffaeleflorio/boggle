package io.github.raffaeleflorio.boggle.vocabulary;

import io.reactivex.rxjava3.core.SingleSource;

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
  SingleSource<Boolean> contains(CharSequence word);
}
