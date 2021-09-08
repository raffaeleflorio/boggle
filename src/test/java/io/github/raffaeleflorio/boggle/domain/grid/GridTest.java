package io.github.raffaeleflorio.boggle.domain.grid;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

class GridTest {
  @Nested
  class FakeTest {
    @Test
    void testDefaultDescription() {
      assertThat(
        new Grid.Fake<>().description().feature("id"),
        contains("fake grid")
      );
    }

    @Test
    void testCustomDescription() {
      assertThat(
        new Grid.Fake<>(new Description.Fake("feature", "value"))
          .description()
          .feature("feature"),
        contains("value")
      );
    }

    @Test
    void testDefaultCompatibility() {
      assertThat(
        new Grid.Fake<>().compatible(new Dice.Fake<>()),
        equalTo(false)
      );
    }

    @Test
    void testCompatibilityWithCustomFn() {
      assertThat(
        new Grid.Fake<>(x -> true).compatible(new Dice.Fake<>()),
        equalTo(true)
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
