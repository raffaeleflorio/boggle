package io.github.raffaeleflorio.boggle.domain.score;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import org.junit.jupiter.api.Test;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class IfInGridTest {
  @Test
  void testScoreWithAWordCompatibleWithGrid() {
    assertThat(
      new IfInGrid<>(
        new Score.Fake<>(x -> 11),
        new Grid.Fake<>(x -> true)
      ).score(new Dice.Fake<>()),
      emits(equalTo(11))
    );
  }

  @Test
  void testScoreWithAWordIncompatibleWithGrid() {
    assertThat(
      new IfInGrid<>(
        new Score.Fake<>(x -> 1_000),
        new Grid.Fake<>(x -> false)
      ).score(new Dice.Fake<>()),
      emits(equalTo(0))
    );
  }
}
