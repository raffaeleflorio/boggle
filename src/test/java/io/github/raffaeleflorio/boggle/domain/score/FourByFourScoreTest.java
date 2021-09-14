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
