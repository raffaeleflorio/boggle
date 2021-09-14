package io.github.raffaeleflorio.boggle.domain.dice;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

class AlignedDiceTest {
  @Test
  void testInitialAlignment() {
    assertThat(
      new AlignedDice<>(
        List.of(
          new Die.Fake<>(1),
          new Die.Fake<>(2),
          new Die.Fake<>(3),
          new Die.Fake<>(4)
        )
      ).values(),
      contains(1, 2, 3, 4)
    );
  }

  @Test
  void testShuffledAlignment() {
    var inc = (Function<Integer, Integer>) x -> x + 1;
    assertThat(
      new AlignedDice<>(
        List.of(
          new Die.Fake<>(1, inc),
          new Die.Fake<>(2, inc),
          new Die.Fake<>(3, inc),
          new Die.Fake<>(4, inc),
          new Die.Fake<>(5, inc),
          new Die.Fake<>(6, inc)
        )
      ).shuffled().values(),
      contains(2, 3, 4, 5, 6, 7)
    );
  }
}
