package io.github.raffaeleflorio.boggle.domain.grid;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

class LayoutGridTest {
  @Test
  void testLayoutFeature() {
    assertThat(
      new LayoutGrid<>(
        new Grid.Fake<>(
          new Dice.Fake<>(List.of("A", "B", "C"))
        )
      ).description().feature("layout"),
      contains("A", "B", "C")
    );
  }

  @Test
  void testShuffledValues() {
    assertThat(
      new LayoutGrid<>(
        new Grid.Fake<>(
          new Dice.Fake<>(
            List.of(),
            x -> List.of(1, 2, 3)
          )
        )
      ).shuffled().values(),
      contains(1, 2, 3)
    );
  }

  @Test
  void testShuffledDescription() {
    assertThat(
      new LayoutGrid<>(
        new Grid.Fake<>(
          new Dice.Fake<>(
            List.of(),
            x -> List.of("S", "HU", "FFLED")
          )
        )
      ).shuffled().description().feature("layout"),
      contains("S", "HU", "FFLED")
    );
  }

  @Test
  void testCompatibility() {
    assertThat(
      new LayoutGrid<>(
        new Grid.Fake<>(x -> false)
      ).compatible(new Dice.Fake<>()),
      equalTo(false)
    );
  }
}
