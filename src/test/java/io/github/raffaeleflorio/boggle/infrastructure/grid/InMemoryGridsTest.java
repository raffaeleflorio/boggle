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
          Map.entry("a language", "a size"), new Grid.Fake<>()
        )
      ).grid(
        new Description.Fake(
          Map.of(
            "lang", List.of("a language"),
            "size", List.of("a size")
          )
        )
      ),
      emits(notNullValue())
    );
  }

  @Test
  void testMissingGridPerLanguage() {
    assertThat(
      new InMemoryGrids<>(
        Map.of(
          Map.entry("a language", "a size"), new Grid.Fake<>()
        )
      ).grid(
        new Description.Fake(
          Map.of(
            "lang", List.of("any"),
            "size", List.of("a size")
          )
        )
      ),
      emits(nullValue())
    );
  }

  @Test
  void testMissingGridPerSize() {
    assertThat(
      new InMemoryGrids<>(
        Map.of(
          Map.entry("a language", "a size"), new Grid.Fake<>()
        )
      ).grid(
        new Description.Fake(
          Map.of(
            "lang", List.of("a language"),
            "size", List.of("any")
          )
        )
      ),
      emits(nullValue())
    );
  }
}
