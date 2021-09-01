package io.github.raffaeleflorio.boggle.grid;

import io.github.raffaeleflorio.boggle.dice.Dice;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
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
    void testShuffled() {
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
  }
}
