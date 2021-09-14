package io.github.raffaeleflorio.boggle.domain.score;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ScoreTest {
  @Nested
  class FakeTest {
    @Test
    void testScoreWithoutCustomFn() {
      assertThat(
        new Score.Fake<>().score(new Dice.Fake<>()),
        emits(equalTo(0))
      );
    }

    @Test
    void testScoreWithCustomFn() {
      assertThat(
        new Score.Fake<>(x -> 42).score(new Dice.Fake<>()),
        emits(equalTo(42))
      );
    }
  }
}
