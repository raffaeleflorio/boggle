package io.github.raffaeleflorio.boggle.domain.grid;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

class HumanPlayableGridTest {
  @Test
  void testDescription() {
    assertThat(
      new HumanPlayableGrid(
        new Grid.Fake<>(
          new Dice.Fake<>(
            List.of(
              "A", "B",
              "C", "D"
            )
          )
        )
      ).description().feature("dice"),
      contains("A", "B", "C", "D")
    );
  }

  @Test
  void testShuffled() {
    assertThat(
      new HumanPlayableGrid(
        new Grid.Fake<>(
          new Dice.Fake<>(
            List.of(),
            x -> List.of(
              "B", "C",
              "D", "A"
            )
          )
        )
      ).shuffled().description().feature("dice"),
      contains("B", "C", "D", "A")
    );
  }

  @Test
  void testCompatibility() {
    assertThat(
      new HumanPlayableGrid(
        new Grid.Fake<>(x -> true)
      ).compatible(new Dice.Fake<>()),
      equalTo(true)
    );
  }
}
