package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import io.github.raffaeleflorio.boggle.hamcrest.IsEmitted;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.AreEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MatchTest {
  @Nested
  class FakeTest {
    @Test
    void testId() {
      var expected = UUID.randomUUID();
      assertThat(
        new Match.Fake<>(expected, new Description.Fake()).id(),
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
        new Match.Fake<>(Map.of(expected.getKey(), expected.getValue())).score(),
        emits(contains(expected))
      );
    }

    @Test
    void testSheet() {
      assertThat(
        new Match.Fake<>(x -> Uni.createFrom().nullItem()).sheet(UUID.randomUUID()),
        IsEmitted.emits(nullValue())
      );
    }

    @Test
    void testPlayers() {
      var expected = UUID.randomUUID();
      assertThat(
        new Match.Fake<>(Map.of(expected, 123)).players(),
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
