package io.github.raffaeleflorio.boggle.domain.grid;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

class LangGridTest {
  @Test
  void testDescription() {
    assertThat(
      new LangGrid<>(new Grid.Fake<>(), "a language")
        .description()
        .feature("lang"),
      contains("a language")
    );
  }

  @Test
  void testShuffledValues() {
    assertThat(
      new LangGrid<>(
        new Grid.Fake<>(
          new Dice.Fake<>(
            List.of(),
            x -> List.of(1, 2, 3)
          )
        ),
        "any"
      ).shuffled().values(),
      contains(1, 2, 3)
    );
  }

  @Test
  void testCompatibility() {
    assertThat(
      new LangGrid<>(
        new Grid.Fake<>(x -> false),
        "any"
      ).compatible(new Dice.Fake<>()),
      equalTo(false)
    );
  }
}
