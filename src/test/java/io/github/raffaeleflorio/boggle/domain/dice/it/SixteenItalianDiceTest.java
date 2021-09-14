package io.github.raffaeleflorio.boggle.domain.dice.it;

import org.junit.jupiter.api.RepeatedTest;

import java.util.Map;

import static io.github.raffaeleflorio.boggle.hamcrest.IsCollectionWithMaximumFrequencies.hasMaximumFrequencies;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasSize;

class SixteenItalianDiceTest {
  @RepeatedTest(256)
  void testShuffledLetterFrequencies() {
    assertThat(
      new SixteenItalianDice().shuffled().values(),
      allOf(
        hasSize(16),
        hasMaximumFrequencies(
          Map.ofEntries(
            Map.entry("A", 8L),
            Map.entry("B", 2L),
            Map.entry("C", 4L),
            Map.entry("D", 3L),
            Map.entry("E", 12L),
            Map.entry("F", 2L),
            Map.entry("G", 2L),
            Map.entry("H", 2L),
            Map.entry("I", 8L),
            Map.entry("L", 5L),
            Map.entry("M", 3L),
            Map.entry("N", 6L),
            Map.entry("O", 8L),
            Map.entry("P", 2L),
            Map.entry("Qu", 1L),
            Map.entry("R", 6L),
            Map.entry("S", 6L),
            Map.entry("T", 6L),
            Map.entry("U", 4L),
            Map.entry("V", 2L),
            Map.entry("Z", 1L)
          )
        )
      )
    );
  }
}
