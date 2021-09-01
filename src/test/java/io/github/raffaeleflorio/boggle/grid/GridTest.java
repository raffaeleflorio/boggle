package io.github.raffaeleflorio.boggle.grid;

import io.github.raffaeleflorio.boggle.dice.Dice;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

class GridTest {
  @Nested
  class FakeTest {
    @Test
    void testDescription() {
      assertThat(
        new Grid.Fake<>().description(),
        equalTo(Map.of("id", "fake grid"))
      );
    }

    @Test
    void testDefaultShuffled() {
      assertThat(
        new Grid.Fake<>().shuffled().description(),
        equalTo(Map.of("id", "fake grid"))
      );
    }

    @Test
    void testDefaultScore() {
      assertThat(
        new Grid.Fake<>().score(new Dice.Fake<>()),
        equalTo(0)
      );
    }

    @Test
    void testScoreWithScoreFn() {
      assertThat(
        new Grid.Fake<>(x -> 42).score(new Dice.Fake<>()),
        equalTo(42)
      );
    }

    @Test
    void testValues() {
      assertThat(
        new Grid.Fake<>(
          new Dice.Fake<>(
            List.of(1, 2, 3)
          )
        ).values(),
        contains(1, 2, 3)
      );
    }

    @Test
    void testShuffledWithDice() {
      assertThat(
        new Grid.Fake<>(
          new Dice.Fake<>(
            List.of(),
            x -> List.of(1, 2, 3)
          )
        ).shuffled().values(),
        contains(1, 2, 3)
      );
    }
  }
}
