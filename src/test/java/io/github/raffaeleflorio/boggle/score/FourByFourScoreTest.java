package io.github.raffaeleflorio.boggle.score;

import io.github.raffaeleflorio.boggle.dice.Dice;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class FourByFourScoreTest {
  @Test
  void testZeroScoreWithZeroSide() {
    assertThat(
      new FourByFourScore<>().apply(new Dice.Fake<>()),
      equalTo(0)
    );
  }

  @Test
  void testZeroScoreWithOneSide() {
    assertThat(
      new FourByFourScore<>().apply(new Dice.Fake<>(List.of(1))),
      equalTo(0)
    );
  }

  @Test
  void testZeroScoreWithTwoSides() {
    assertThat(
      new FourByFourScore<>().apply(new Dice.Fake<>(List.of(2))),
      equalTo(0)
    );
  }

  @Test
  void testOneScoreWithThreeSides() {
    assertThat(
      new FourByFourScore<>().apply(new Dice.Fake<>(List.of(1, 2, 3))),
      equalTo(1)
    );
  }

  @Test
  void testOneScoreWithFourSides() {
    assertThat(
      new FourByFourScore<>().apply(new Dice.Fake<>(List.of(1, 2, 3, 4))),
      equalTo(1)
    );
  }

  @Test
  void testTwoScoreWithFiveSides() {
    assertThat(
      new FourByFourScore<>().apply(new Dice.Fake<>(List.of(1, 2, 3, 4, 5))),
      equalTo(2)
    );
  }

  @Test
  void testThreeScoreWithSixSides() {
    assertThat(
      new FourByFourScore<>().apply(new Dice.Fake<>(List.of(1, 2, 3, 4, 5, 6))),
      equalTo(3)
    );
  }

  @Test
  void testFiveScoreWithSevenSides() {
    assertThat(
      new FourByFourScore<>().apply(new Dice.Fake<>(List.of(1, 2, 3, 4, 5, 6, 7))),
      equalTo(5)
    );
  }

  @Test
  void testElevenScoreWithEightSides() {
    assertThat(
      new FourByFourScore<>().apply(new Dice.Fake<>(List.of(1, 2, 3, 4, 5, 6, 7, 8))),
      equalTo(11)
    );
  }

  @Test
  void testElevenScoreWithTenSides() {
    assertThat(
      new FourByFourScore<>().apply(new Dice.Fake<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))),
      equalTo(11)
    );
  }
}
