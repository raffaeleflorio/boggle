package io.github.raffaeleflorio.boggle.infrastructure.grid;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class InMemoryGridsTest {
  @Test
  void testExistingGrid() {
    assertThat(
      new InMemoryGrids<>(
        Map.of(
          description -> description.feature("feature").equals(List.of("value")),
          new Grid.Fake<>())
      ).grid(
        new Description.Fake("feature", "value")
      ),
      emits(notNullValue())
    );
  }

  @Test
  void testEmptyGrids() {
    assertThat(
      new InMemoryGrids<>(Map.of())
        .grid(new Description.Fake("any", "value")),
      emits(nullValue())
    );
  }
}
