/*
   Copyright 2021 Raffaele Florio

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package io.github.raffaeleflorio.boggle.domain.grid;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

class FourByFourGridTest {
  @Test
  void testDescriptionContainsSizeFeature() {
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
      ).description().feature("size"),
      contains("4x4")
    );
  }

  @Test
  void testShuffledValues() {
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
      ).shuffled().values(),
      contains(
        "A", "B", "C", "D",
        "E", "F", "G", "H",
        "I", "J", "K", "L",
        "M", "N", "O", "P"
      )
    );
  }

  @Test
  void testCompatibilityWithAMissingWord() {
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
      ).compatible(
        new Dice.Fake<>(
          List.of("W", "O", "R", "D")
        )
      ),
      equalTo(false)
    );
  }

  @Test
  void testCompatibilityWithAWordOfTwoCharacters() {
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
      ).compatible(
        new Dice.Fake<>(
          List.of("O", "F")
        )
      ),
      equalTo(true)
    );
  }

  @Test
  void testCompatibilityWithAWordOfThreeCharacters() {
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
      ).compatible(
        new Dice.Fake<>(
          List.of("F", "O", "R")
        )
      ),
      equalTo(true)
    );
  }

  @Test
  void testCompatibilityWithMaximumLengthWord() {
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
      ).compatible(
        new Dice.Fake<>(
          List.of(
            "I", "N", "C", "O", "N", "S", "E", "Qu", "E", "N", "T", "I", "A", "L", "L", "Y"
          )
        )
      ),
      equalTo(true)
    );
  }

  @Test
  void testCompatibilityWithNumbers() {
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
      ).compatible(
        new Dice.Fake<>(
          List.of(6, 10, 15, 12, 8, 4)
        )
      ),
      equalTo(true)
    );
  }

  @Test
  void testValuesWithNumbers() {
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

  @Test
  void testCompatibilityWithAWordOfSeventeenCharacters() {
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
      ).compatible(
        new Dice.Fake<>(
          List.of(
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 16
          )
        )
      ),
      equalTo(false)
    );
  }

  @Test
  void testCompatibilityWithAWordOfZeroCharacters() {
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
      ).compatible(
        new Dice.Fake<>()
      ),
      equalTo(true)
    );
  }
}
