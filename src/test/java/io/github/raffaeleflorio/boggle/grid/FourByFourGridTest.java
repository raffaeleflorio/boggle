package io.github.raffaeleflorio.boggle.grid;

import io.github.raffaeleflorio.boggle.dice.Dice;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

class FourByFourGridTest {
  @Test
  void testDescription() {
    assertThat(
      new FourByFourGrid<>(
        new Dice.Fake<>(
          List.of(
            "1", "2", "3", "4",
            "5", "6", "7", "8",
            "9", "0", "A", "B",
            "C", "D", "E", "F"
          )
        )
      ).description(),
      equalTo(
        Map.of("size", "4x4")
      )
    );
  }

  @Test
  void testShuffled() {
    assertThat(
      new FourByFourGrid<>(
        new Dice.Fake<>(
          List.of(),
          x -> List.of(
            "A", "B", "C", "D",
            "E", "F", "G", "H",
            "I", "J", "K", "L",
            "M", "N", "O", "P"
          )
        )
      ).shuffled().description(),
      equalTo(
        Map.of("size", "4x4")
      )
    );
  }

  @Test
  void testScoreOfAMissingWord() {
    assertThat(
      new FourByFourGrid<>(
        new Dice.Fake<>(
          List.of(
            "D", "D", "D", "D",
            "D", "W", "D", "D",
            "D", "O", "-", "-",
            "D", "-", "R", "-"
          )
        )
      ).score(
        new Dice.Fake<>(
          List.of("W", "O", "R", "D")
        )
      ),
      equalTo(0)
    );
  }

  @Test
  void testScoreOfAWordOfTwoCharacters() {
    assertThat(
      new FourByFourGrid<>(
        new Dice.Fake<>(
          List.of(
            "O", "F", "-", "-",
            "-", "-", "-", "-",
            "-", "-", "-", "-",
            "-", "-", "-", "-"
          )
        )
      ).score(
        new Dice.Fake<>(
          List.of("O", "F")
        )
      ),
      equalTo(0)
    );
  }

  @Test
  void testOneScoreThreeCharacters() {
    assertThat(
      new FourByFourGrid<>(
        new Dice.Fake<>(
          List.of(
            "F", "O", "R", "-",
            "-", "-", "-", "-",
            "-", "-", "-", "-",
            "-", "-", "-", "-"
          )
        )
      ).score(
        new Dice.Fake<>(
          List.of("F", "O", "R")
        )
      ),
      equalTo(1)
    );
  }

  @Test
  void testOneScoreFourCharacters() {
    assertThat(
      new FourByFourGrid<>(
        new Dice.Fake<>(
          List.of(
            "W", "-", "-", "-",
            "-", "O", "-", "-",
            "-", "-", "R", "D",
            "-", "-", "-", "-"
          )
        )
      ).score(
        new Dice.Fake<>(
          List.of("W", "O", "R", "D")
        )
      ),
      equalTo(1)
    );
  }

  @Test
  void testTwoScore() {
    assertThat(
      new FourByFourGrid<>(
        new Dice.Fake<>(
          List.of(
            "W", "-", "-", "-",
            "-", "O", "-", "-",
            "R", "-", "S", "-",
            "-", "D", "-", "-"
          )
        )
      ).score(
        new Dice.Fake<>(
          List.of("W", "O", "R", "D", "S")
        )
      ),
      equalTo(2)
    );
  }

  @Test
  void testThreeScore() {
    assertThat(
      new FourByFourGrid<>(
        new Dice.Fake<>(
          List.of(
            "N", "E", "-", "-",
            "-", "A", "Y", "-",
            "-", "R", "L", "-",
            "-", "-", "-", "-"
          )
        )
      ).score(
        new Dice.Fake<>(
          List.of("N", "E", "A", "R", "L", "Y")
        )
      ),
      equalTo(3)
    );
  }

  @Test
  void testFiveScore() {
    assertThat(
      new FourByFourGrid<>(
        new Dice.Fake<>(
          List.of(
            "P", "Z", "L", "-",
            "U", "Z", "E", "-",
            "-", "-", "-", "S",
            "-", "-", "-", "-"
          )
        )
      ).score(
        new Dice.Fake<>(
          List.of("P", "U", "Z", "Z", "L", "E", "S")
        )
      ),
      equalTo(5)
    );
  }

  @Test
  void testElevenScore() {
    assertThat(
      new FourByFourGrid<>(
        new Dice.Fake<>(
          List.of(
            "I", "Qu", "E", "Y",
            "N", "E", "N", "L",
            "C", "S", "T", "L",
            "O", "N", "I", "A"
          )
        )
      ).score(
        new Dice.Fake<>(
          List.of(
            "I", "N", "C", "O", "N", "S", "E", "Qu", "E", "N", "T", "I", "A", "L", "L", "Y"
          )
        )
      ),
      equalTo(11)
    );
  }

  @Test
  void testThreeScoreWithNumbers() {
    assertThat(
      new FourByFourGrid<>(
        new Dice.Fake<>(
          List.of(
            1, 2, 3, 4,
            5, 6, 7, 8,
            9, 10, 11, 12,
            13, 14, 15, 16
          )
        )
      ).score(
        new Dice.Fake<>(
          List.of(6, 10, 15, 12, 8, 4)
        )
      ),
      equalTo(3)
    );
  }

  @Test
  void testValues() {
    assertThat(
      new FourByFourGrid<>(
        new Dice.Fake<>(
          List.of(
            1, 2, 3, 4,
            5, 6, 7, 8,
            9, 10, 11, 12,
            13, 14, 15, 0
          )
        )
      ).values(),
      contains(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0)
    );
  }
}
