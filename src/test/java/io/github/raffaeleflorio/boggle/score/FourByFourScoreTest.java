package io.github.raffaeleflorio.boggle.score;

import io.github.raffaeleflorio.boggle.dice.Dice;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class FourByFourScoreTest {
  @Test
  void testZeroScoreWithZeroSide() {
    assertThat(
      new FourByFourScore<>().score(new Dice.Fake<>()),
      emits(equalTo(0))
    );
  }

  @Test
  void testZeroScoreWithOneSide() {
    assertThat(
      new FourByFourScore<>().score(new Dice.Fake<>(List.of(1))),
      emits(equalTo(0))
    );
  }

  @Test
  void testZeroScoreWithTwoSides() {
    assertThat(
      new FourByFourScore<>().score(new Dice.Fake<>(List.of(2))),
      emits(equalTo(0))
    );
  }

  @Test
  void testOneScoreWithThreeSides() {
    assertThat(
      new FourByFourScore<>().score(new Dice.Fake<>(List.of(1, 2, 3))),
      emits(equalTo(1))
    );
  }

  @Test
  void testOneScoreWithFourSides() {
    assertThat(
      new FourByFourScore<>().score(new Dice.Fake<>(List.of(1, 2, 3, 4))),
      emits(equalTo(1))
    );
  }

  @Test
  void testTwoScoreWithFiveSides() {
    assertThat(
      new FourByFourScore<>().score(new Dice.Fake<>(List.of(1, 2, 3, 4, 5))),
      emits(equalTo(2))
    );
  }

  @Test
  void testThreeScoreWithSixSides() {
    assertThat(
      new FourByFourScore<>().score(new Dice.Fake<>(List.of(1, 2, 3, 4, 5, 6))),
      emits(equalTo(3))
    );
  }

  @Test
  void testFiveScoreWithSevenSides() {
    assertThat(
      new FourByFourScore<>().score(new Dice.Fake<>(List.of(1, 2, 3, 4, 5, 6, 7))),
      emits(equalTo(5))
    );
  }

  @Test
  void testElevenScoreWithEightSides() {
    assertThat(
      new FourByFourScore<>().score(new Dice.Fake<>(List.of(1, 2, 3, 4, 5, 6, 7, 8))),
      emits(equalTo(11))
    );
  }

  @Test
  void testElevenScoreWithTenSides() {
    assertThat(
      new FourByFourScore<>().score(new Dice.Fake<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))),
      emits(equalTo(11))
    );
  }
}
