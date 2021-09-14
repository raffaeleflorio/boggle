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
package io.github.raffaeleflorio.boggle.domain.dice;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;

class DiceTest {
  @Nested
  class FakeTest {
    @Test
    void testValues() {
      assertThat(
        new Dice.Fake<>(List.of(1, 2, 3)).values(),
        contains(1, 2, 3)
      );
    }

    @Test
    void testShuffledValuesWithoutNextFn() {
      assertThat(
        new Dice.Fake<>(
          List.of("NO", "CH", "AN", "GE")
        ).shuffled().values(),
        contains("NO", "CH", "AN", "GE")
      );
    }

    @Test
    void testShuffledValuesWithNextFn() {
      assertThat(
        new Dice.Fake<>(
          List.of(1, 2, 3),
          x -> List.of(2, 4, 6)
        ).shuffled().values(),
        contains(2, 4, 6)
      );
    }

    @Test
    void testEmptyFake() {
      assertThat(
        new Dice.Fake<>().shuffled().values(),
        hasSize(0)
      );
    }
  }
}
