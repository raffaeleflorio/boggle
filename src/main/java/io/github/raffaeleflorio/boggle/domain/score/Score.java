package io.github.raffaeleflorio.boggle.domain.score;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.smallrye.mutiny.Uni;

/**
 * An asynchronous function to calculate score
 *
 * @param <T> The mark side dice type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public interface Score<T> {
  Uni<Integer> score(Dice<T> word);
}
