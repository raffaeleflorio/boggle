package io.github.raffaeleflorio.boggle.dice;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

/**
 * Standard italian boggle 16 dice
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class Italian16Dice implements Dice<CharSequence> {
  /**
   * Builds an italian boggle 16 dice
   *
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  public Italian16Dice() {
    this(max -> ThreadLocalRandom.current().nextInt(max));
  }

  /**
   * Builds an italian boggle 16 dice
   *
   * @param randomFn The function to build bounded random ints
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  Italian16Dice(final Function<Integer, Integer> randomFn) {
    this(
      new UnorderedDice<>(
        List.of(
          new LoadableDie<>(
            List.of("A", "A", "E", "I", "O", "T"),
            randomFn
          ),
          new LoadableDie<>(
            List.of("A", "B", "O", "O", "M", "Qu"),
            randomFn
          ),
          new LoadableDie<>(
            List.of("A", "B", "I", "L", "R", "T"),
            randomFn
          ),
          new LoadableDie<>(
            List.of("A", "C", "E", "L", "R", "S"),
            randomFn
          ),
          new LoadableDie<>(
            List.of("A", "C", "D", "E", "M", "P"),
            randomFn
          ),
          new LoadableDie<>(
            List.of("A", "C", "F", "I", "O", "R"),
            randomFn
          ),
          new LoadableDie<>(
            List.of("A", "D", "E", "N", "V", "Z"),
            randomFn
          ),
          new LoadableDie<>(
            List.of("A", "I", "M", "O", "R", "S"),
            randomFn
          ),
          new LoadableDie<>(
            List.of("C", "E", "N", "O", "T", "U"),
            randomFn
          ),
          new LoadableDie<>(
            List.of("D", "E", "N", "O", "S", "T"),
            randomFn
          ),
          new LoadableDie<>(
            List.of("E", "E", "F", "H", "I", "S"),
            randomFn
          ),
          new LoadableDie<>(
            List.of("E", "G", "L", "N", "O", "U"),
            randomFn
          ),
          new LoadableDie<>(
            List.of("E", "G", "I", "N", "T", "V"),
            randomFn
          ),
          new LoadableDie<>(
            List.of("E", "H", "I", "N", "R", "S"),
            randomFn
          ),
          new LoadableDie<>(
            List.of("E", "I", "L", "O", "R", "U"),
            randomFn
          ),
          new LoadableDie<>(
            List.of("E", "L", "P", "S", "T", "U"),
            randomFn
          )
        ),
        randomFn
      )
    );
  }

  private Italian16Dice(final Dice<CharSequence> origin) {
    this.origin = origin;
  }

  @Override
  public Collection<CharSequence> values() {
    return origin.values();
  }

  @Override
  public Dice<CharSequence> shuffled() {
    return new Italian16Dice(origin.shuffled());
  }

  private final Dice<CharSequence> origin;
}
