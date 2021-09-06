package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import io.github.raffaeleflorio.boggle.hamcrest.IsEmitted;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.AreEmitted.emits;
import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MatchTest {
  @Nested
  class FakeTest {
    @Test
    void testId() {
      var expected = UUID.randomUUID();
      assertThat(
        new Match.Fake<>(
          expected,
          new Description.Fake()
        ).id(),
        equalTo(expected)
      );
    }

    @Test
    void testDescription() {
      assertThat(
        new Match.Fake<>(
          UUID.randomUUID(),
          new Description.Fake("feature", "fake")
        ).description().feature("feature"),
        contains("fake")
      );
    }

    @Test
    void testScore() {
      var expected = Map.entry(UUID.randomUUID(), 123);
      assertThat(
        new Match.Fake<>(
          Map.of(expected.getKey(), expected.getValue()),
          Map.of()
        ).score(),
        emits(contains(expected))
      );
    }

    @Test
    void testMissingSheet() {
      assertThat(
        new Match.Fake<>(Map.of(), Map.of()).sheet(UUID.randomUUID()),
        IsEmitted.emits(nullValue())
      );
    }

    @Test
    void testExistingSheet() {
      var player = UUID.randomUUID();
      assertThat(
        new Match.Fake<>(Map.of(), Map.of(player, new Sheet.Fake<>())).sheet(player),
        IsEmitted.emits(notNullValue())
      );
    }

    @Test
    void testPlayers() {
      var expected = UUID.randomUUID();
      assertThat(
        new Match.Fake<>(
          Map.of(expected, 123),
          Map.of()
        ).players(),
        emits(contains(expected))
      );
    }

    @Test
    void testGrid() {
      assertThat(
        new Match.Fake<>(new Grid.Fake<>()).grid(),
        IsEmitted.emits(notNullValue())
      );
    }
  }
}
