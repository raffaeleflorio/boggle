package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import io.github.raffaeleflorio.boggle.domain.score.Score;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import io.github.raffaeleflorio.boggle.hamcrest.AreEmitted;
import io.github.raffaeleflorio.boggle.hamcrest.IsEmitted;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ClassicRuledMatchTest {
  @Test
  void testScoreWithSinglePlayer() {
    var player = UUID.randomUUID();
    assertThat(
      new ClassicRuledMatch<>(
        new Match.Fake<>(
          Map.of(
            player, 0
          ),
          Map.of(
            player, new Sheet.Fake<>(
              List.of(
                new Dice.Fake<>(List.of(1, 2, 3)),
                new Dice.Fake<>(List.of(1))
              )
            )
          )
        ),
        new Score.Fake<>(x -> 12)
      ).score(),
      AreEmitted.emits(
        contains(
          Map.entry(player, 24)
        )
      )
    );
  }

  @Test
  void testScoreWithSinglePlayerWithDuplicateWords() {
    var player = UUID.randomUUID();
    assertThat(
      new ClassicRuledMatch<>(
        new Match.Fake<>(
          Map.of(
            player, 0
          ),
          Map.of(
            player, new Sheet.Fake<>(
              List.of(
                new Dice.Fake<>(List.of(1, 2, 3)),
                new Dice.Fake<>(List.of(1)),
                new Dice.Fake<>(List.of(1)),
                new Dice.Fake<>(List.of(1)),
                new Dice.Fake<>(List.of(1, 2, 3))
              )
            )
          )
        ),
        new Score.Fake<>(x -> 12)
      ).score(),
      AreEmitted.emits(
        contains(
          Map.entry(player, 24)
        )
      )
    );
  }

  @Test
  void testScoreWithTwoPlayers() {
    var player1 = UUID.randomUUID();
    var player2 = UUID.randomUUID();
    assertThat(
      new ClassicRuledMatch<>(
        new Match.Fake<>(
          Map.of(
            player1, 123,
            player2, 123
          ),
          Map.of(
            player1, new Sheet.Fake<>(
              List.of(
                new Dice.Fake<>(List.of(1, 2, 3)),
                new Dice.Fake<>(List.of(1))
              )
            ),
            player2, new Sheet.Fake<>(
              List.of(
                new Dice.Fake<>(List.of(1))
              )
            )
          )
        ),
        new Score.Fake<>(x -> 1)
      ).score(),
      AreEmitted.emits(
        containsInAnyOrder(
          Map.entry(player1, 1),
          Map.entry(player2, 0)
        )
      )
    );
  }

  @Test
  void testScoreWithThreePlayers() {
    var player1 = UUID.randomUUID();
    var player2 = UUID.randomUUID();
    var player3 = UUID.randomUUID();
    assertThat(
      new ClassicRuledMatch<>(
        new Match.Fake<>(
          Map.of(
            player1, 123,
            player2, 123,
            player3, 123
          ),
          Map.of(
            player1, new Sheet.Fake<>(
              List.of(
                new Dice.Fake<>(List.of(1, 2, 3)),
                new Dice.Fake<>(List.of(1))
              )
            ),
            player2, new Sheet.Fake<>(
              List.of(
                new Dice.Fake<>(List.of(1))
              )
            ),
            player3, new Sheet.Fake<>(
              List.of(
                new Dice.Fake<>(List.of(1)),
                new Dice.Fake<>(List.of(1, 2, 3)),
                new Dice.Fake<>(List.of(1, 2, 3, 4)),
                new Dice.Fake<>(List.of(1, 2, 3, 5))
              )
            )
          )
        ),
        new Score.Fake<>(x -> 1)
      ).score(),
      AreEmitted.emits(
        containsInAnyOrder(
          Map.entry(player1, 0),
          Map.entry(player2, 0),
          Map.entry(player3, 2)
        )
      )
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
  void testMissingSheet() {
    assertThat(
      new ClassicRuledMatch<>(
        new Match.Fake<>(Map.of(), Map.of()),
        new Score.Fake<>()
      ).sheet(UUID.randomUUID()),
      IsEmitted.emits(nullValue())
    );
  }

  @Test
  void testExistingSheet() {
    var player = UUID.randomUUID();
    assertThat(
      new ClassicRuledMatch<>(
        new Match.Fake<>(
          Map.of(),
          Map.of(
            player, new Sheet.Fake<>()
          )
        ),
        new Score.Fake<>()
      ).sheet(player),
      IsEmitted.emits(notNullValue())
    );
  }

  @Test
  void testPlayers() {
    var expected = UUID.randomUUID();
    assertThat(
      new ClassicRuledMatch<>(
        new Match.Fake<>(
          Map.of(expected, 123),
          Map.of()
        ),
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
