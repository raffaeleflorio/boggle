package io.github.raffaeleflorio.boggle.domain.score;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import org.junit.jupiter.api.Test;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ConstantScoreTest {
  @Test
  void testDefaultEmission() {
    assertThat(
      new ConstantScore<>().score(new Dice.Fake<>()),
      emits(equalTo(0))
    );
  }

  @Test
  void testCustomEmission() {
    var expected = 42;
    assertThat(
      new ConstantScore<>(expected).score(new Dice.Fake<>()),
      emits(equalTo(expected))
    );
  }
}
