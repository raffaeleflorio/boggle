package io.github.raffaeleflorio.boggle.domain.dice.it;

import io.github.raffaeleflorio.boggle.domain.dice.*;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Standard sixteen Italian boggle dice
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class SixteenItalianDice implements Dice<CharSequence> {
  /**
   * Builds non cryptographically strong sixteen Italian boggle dice
   *
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  public SixteenItalianDice() {
    this((min, bound) -> new RandomDie<>(Function.identity(), min, bound));
  }

  /**
   * Builds sixteen Italian boggle dice with a custom bounded die
   *
   * @param dieFn The function to build bounded die
   * @author Raffaele Florio (raffaeleflorio@protonmail.com)
   * @since 1.0.0
   */
  public SixteenItalianDice(final BiFunction<Integer, Integer, Die<Integer>> dieFn) {
    this(
      new UnorderedDice<>(
        List.of(
          new LoadableDie<>(
            List.of("A", "A", "E", "I", "O", "T"),
            dieFn
          ),
          new LoadableDie<>(
            List.of("A", "B", "O", "O", "M", "Qu"),
            dieFn
          ),
          new LoadableDie<>(
            List.of("A", "B", "I", "L", "R", "T"),
            dieFn
          ),
          new LoadableDie<>(
            List.of("A", "C", "E", "L", "R", "S"),
            dieFn
          ),
          new LoadableDie<>(
            List.of("A", "C", "D", "E", "M", "P"),
            dieFn
          ),
          new LoadableDie<>(
            List.of("A", "C", "F", "I", "O", "R"),
            dieFn
          ),
          new LoadableDie<>(
            List.of("A", "D", "E", "N", "V", "Z"),
            dieFn
          ),
          new LoadableDie<>(
            List.of("A", "I", "M", "O", "R", "S"),
            dieFn
          ),
          new LoadableDie<>(
            List.of("C", "E", "N", "O", "T", "U"),
            dieFn
          ),
          new LoadableDie<>(
            List.of("D", "E", "N", "O", "S", "T"),
            dieFn
          ),
          new LoadableDie<>(
            List.of("E", "E", "F", "H", "I", "S"),
            dieFn
          ),
          new LoadableDie<>(
            List.of("E", "G", "L", "N", "O", "U"),
            dieFn
          ),
          new LoadableDie<>(
            List.of("E", "G", "I", "N", "T", "V"),
            dieFn
          ),
          new LoadableDie<>(
            List.of("E", "H", "I", "N", "R", "S"),
            dieFn
          ),
          new LoadableDie<>(
            List.of("E", "I", "L", "O", "R", "U"),
            dieFn
          ),
          new LoadableDie<>(
            List.of("E", "L", "P", "S", "T", "U"),
            dieFn
          )
        ),
        dieFn
      )
    );
  }

  private SixteenItalianDice(final Dice<CharSequence> origin) {
    this.origin = origin;
  }

  @Override
  public List<CharSequence> values() {
    return origin.values();
  }

  @Override
  public Dice<CharSequence> shuffled() {
    return new SixteenItalianDice(origin.shuffled());
  }

  private final Dice<CharSequence> origin;
}
