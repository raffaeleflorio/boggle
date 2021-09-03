package io.github.raffaeleflorio.boggle.domain.grid;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

class ChattierGridTest {
  @Test
  void testDescription() {
    assertThat(
      new ChattierGrid<>(
        new Grid.Fake<>(),
        Map.of(
          "new feature", List.of("feature value 0", "feature value 1")
        )
      ).description().feature("new feature"),
      contains("feature value 0", "feature value 1")
    );
  }

  @Test
  void testValues() {
    assertThat(
      new ChattierGrid<>(
        new Grid.Fake<>(
          new Dice.Fake<>(List.of(1, 2))
        ),
        Map.of()
      ).values(),
      contains(1, 2)
    );
  }

  @Test
  void testShuffled() {
    assertThat(
      new ChattierGrid<>(
        new Grid.Fake<>(
          new Dice.Fake<>(List.of(), x -> List.of(3, 4, 5)
          )
        ),
        Map.of()
      ).shuffled().values(),
      contains(3, 4, 5)
    );
  }

  @Test
  void testCompatibility() {
    assertThat(
      new ChattierGrid<>(
        new Grid.Fake<>(x -> true),
        Map.of()
      ).compatible(new Dice.Fake<>()),
      equalTo(true)
    );
  }
}
