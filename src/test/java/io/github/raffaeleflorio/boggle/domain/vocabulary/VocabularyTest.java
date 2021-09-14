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
package io.github.raffaeleflorio.boggle.domain.vocabulary;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class VocabularyTest {
  @Nested
  class FakeTest {
    @Test
    void testDefaultContains() {
      assertThat(
        new Vocabulary.Fake<>().contains(new Dice.Fake<>(List.of("ANY"))),
        emits(equalTo(false))
      );
    }

    @Test
    void testCustomContains() {
      assertThat(
        new Vocabulary.Fake<>(x -> true).contains(new Dice.Fake<>(List.of("ANY"))),
        emits(equalTo(true))
      );
    }
  }
}
