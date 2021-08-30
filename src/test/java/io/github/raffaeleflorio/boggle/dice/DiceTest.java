package io.github.raffaeleflorio.boggle.dice;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

class DiceTest {
  @Nested
  class FakeTest {
    @Test
    void testValues() {
      assertThat(
        new Dice.Fake<>(List.of(1, 2, 3)).values(),
        contains(1, 2, 3)
      );
    }

    @Test
    void testShuffledWithoutNextFn() {
      assertThat(
        new Dice.Fake<>(List.of("NO", "CH", "AN", "GE")).shuffled().values(),
        contains("NO", "CH", "AN", "GE")
      );
    }

    @Test
    void testShuffledWithNextFn() {
      assertThat(
        new Dice.Fake<>(List.of(1, 2, 3), x -> List.of(2, 4, 6)).shuffled().values(),
        contains(2, 4, 6)
      );
    }
  }
}
