package io.github.raffaeleflorio.boggle.domain.score;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class FourByFourScoreTest {
  @Test
  void testScoreWithEmptyWord() {
    assertThat(
      new FourByFourScore<>().score(new Dice.Fake<>()),
      emits(equalTo(0))
    );
  }

  @Test
  void testScoreWithAWordOfLengthOfOne() {
    assertThat(
      new FourByFourScore<>().score(new Dice.Fake<>(List.of(1))),
      emits(equalTo(0))
    );
  }

  @Test
  void testScoreWithAWordOfLengthOfTwo() {
    assertThat(
      new FourByFourScore<>().score(new Dice.Fake<>(List.of(2))),
      emits(equalTo(0))
    );
  }

  @Test
  void testScoreWithAWordOfLengthOfThree() {
    assertThat(
      new FourByFourScore<>().score(new Dice.Fake<>(List.of(1, 2, 3))),
      emits(equalTo(1))
    );
  }

  @Test
  void testScoreWithAWordOfLengthOfFour() {
    assertThat(
      new FourByFourScore<>().score(new Dice.Fake<>(List.of(1, 2, 3, 4))),
      emits(equalTo(1))
    );
  }

  @Test
  void testScoreWithAWordOfLengthOfFive() {
    assertThat(
      new FourByFourScore<>().score(new Dice.Fake<>(List.of(1, 2, 3, 4, 5))),
      emits(equalTo(2))
    );
  }

  @Test
  void testScoreWithAWordOfLengthOfSix() {
    assertThat(
      new FourByFourScore<>().score(new Dice.Fake<>(List.of(1, 2, 3, 4, 5, 6))),
      emits(equalTo(3))
    );
  }

  @Test
  void testScoreWithAWordOfLengthOfSeven() {
    assertThat(
      new FourByFourScore<>().score(new Dice.Fake<>(List.of(1, 2, 3, 4, 5, 6, 7))),
      emits(equalTo(5))
    );
  }

  @Test
  void testScoreWithAWordOfLengthOfEigth() {
    assertThat(
      new FourByFourScore<>().score(new Dice.Fake<>(List.of(1, 2, 3, 4, 5, 6, 7, 8))),
      emits(equalTo(11))
    );
  }

  @Test
  void testScoreWithAWordOfLengthOfTen() {
    assertThat(
      new FourByFourScore<>().score(new Dice.Fake<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))),
      emits(equalTo(11))
    );
  }
}
