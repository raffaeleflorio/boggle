package io.github.raffaeleflorio.boggle.dice;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

class UnorderedDiceTest {
  @Test
  void testInitialValues() {
    assertThat(
      new UnorderedDice<>(
        List.of(
          new Die.Fake<>(1),
          new Die.Fake<>(2),
          new Die.Fake<>(3)
        )
      ).values(),
      containsInAnyOrder(1, 2, 3)
    );
  }

  @Test
  void testShuffled() {
    var inc = (Function<Integer, Integer>) x -> x + 1;
    assertThat(
      new UnorderedDice<>(
        List.of(
          new Die.Fake<>(6, inc),
          new Die.Fake<>(7, inc),
          new Die.Fake<>(8, inc),
          new Die.Fake<>(14, inc),
          new Die.Fake<>(17, inc),
          new Die.Fake<>(41, inc)
        )
      ).shuffled().values(),
      containsInAnyOrder(7, 8, 9, 15, 18, 42)
    );
  }
}
