package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import io.github.raffaeleflorio.boggle.domain.score.Score;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheets;
import io.github.raffaeleflorio.boggle.hamcrest.AreEmitted;
import io.github.raffaeleflorio.boggle.hamcrest.IsEmitted;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ClassicRuledMatchTest {
  @Test
  void testScoreUniqueWord() {
    var expected = Map.entry(UUID.randomUUID(), 789);
    assertThat(
      new ClassicRuledMatch<>(
        new Match.Fake<>(
          Map.of(expected.getKey(), 123),
          new Sheets.Fake<>(
            id -> Uni.createFrom().item(new Sheet.Fake<>(List.of(new Dice.Fake<>()))),
            x -> Uni.createFrom().nullItem()
          )
        ),
        new Score.Fake<>(x -> 789)
      ).score(),
      AreEmitted.emits(contains(expected))
    );
  }

  @Test
  void testId() {
    var expected = UUID.randomUUID();
    assertThat(
      new ClassicRuledMatch<>(
        new Match.Fake<>(expected, new Description.Fake()),
        new Score.Fake<>()
      ).id(),
      equalTo(expected)
    );
  }

  @Test
  void testDescription() {
    assertThat(
      new ClassicRuledMatch<>(
        new Match.Fake<>(UUID.randomUUID(), new Description.Fake("feature", "Value")),
        new Score.Fake<>()
      ).description().feature("feature"),
      contains("Value")
    );
  }

  @Test
  void testSheet() {
    assertThat(
      new ClassicRuledMatch<>(
        new Match.Fake<>(
          new Sheets.Fake<>(
            id -> Uni.createFrom().item(new Sheet.Fake<>(id)),
            x -> Uni.createFrom().nullItem()
          )
        ),
        new Score.Fake<>()
      ).sheet(UUID.randomUUID()),
      IsEmitted.emits(notNullValue())
    );
  }

  @Test
  void testPlayers() {
    var expected = UUID.randomUUID();
    assertThat(
      new ClassicRuledMatch<>(
        new Match.Fake<>(Map.of(expected, 123)),
        new Score.Fake<>()
      ).players(),
      AreEmitted.emits(contains(expected))
    );
  }

  @Test
  void testGrid() {
    assertThat(
      new ClassicRuledMatch<>(
        new Match.Fake<>(
          new Grid.Fake<>(
            new Dice.Fake<>(List.of("A", "B", "C"))
          )
        ),
        new Score.Fake<>()
      ).grid().onItem().transform(Dice::values),
      IsEmitted.emits(contains("A", "B", "C"))
    );
  }
}
