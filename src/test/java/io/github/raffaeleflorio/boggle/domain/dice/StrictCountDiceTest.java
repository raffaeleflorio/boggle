package io.github.raffaeleflorio.boggle.domain.dice;

import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.raffaeleflorio.boggle.hamcrest.IsThrowedWithMessage.throwsWithMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

class StrictCountDiceTest {
  @Test
  void testBadCountWithInitialValues() {
    assertThat(
      () -> new StrictCountDice<>(
        new Dice.Fake<>(List.of("A", "B", "C")),
        4
      ).values(),
      throwsWithMessage(IllegalStateException.class, "Expected a dice of count 4, but was 3")
    );
  }

  @Test
  void testBadCountWithShuffledValue() {
    assertThat(
      () -> new StrictCountDice<>(
        new Dice.Fake<>(
          List.of("A", "B"),
          x -> List.of("A")
        ),
        2
      ).shuffled().values(),
      throwsWithMessage(IllegalStateException.class, "Expected a dice of count 2, but was 1")
    );
  }

  @Test
  void testCorrectCount() {
    assertThat(
      new StrictCountDice<>(
        new Dice.Fake<>(List.of(1, 2, 3)),
        3
      ).values(),
      contains(1, 2, 3)
    );
  }
}
