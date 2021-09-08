package io.github.raffaeleflorio.boggle.infrastructure.grid;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class InMemoryGridsTest {
  @Test
  void testExistingGrid() {
    assertThat(
      new InMemoryGrids<>(
        Map.of(
          description -> description.feature("feature").equals(List.of("value")),
          new Grid.Fake<>()
        )
      ).grid(
        new Description.Fake("feature", "value")
      ),
      emits(notNullValue())
    );
  }

  @Test
  void testEmptyGrids() {
    assertThat(
      new InMemoryGrids<>(Map.of()).grid(new Description.Fake("any", "value")),
      emits(nullValue())
    );
  }

  @Test
  void testExistingGridNotShuffled() {
    assertThat(
      new InMemoryGrids<>(
        Map.of(
          x -> true,
          new Grid.Fake<>(
            new Dice.Fake<>(
              List.of(
                "NOT", "SHUFFLED"
              ),
              x -> List.of(
                "SHUFFLED", "NOT"
              )
            )
          )
        )
      ).grid(
          new Description.Fake()
        )
        .onItem().transform(Grid::values),
      emits(contains("NOT", "SHUFFLED"))
    );
  }
}
