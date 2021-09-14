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
package io.github.raffaeleflorio.boggle.infrastructure.sheet;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.hamcrest.AreEmitted;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class InMemorySheetTest {
  @Test
  void testId() {
    var expected = UUID.randomUUID();
    assertThat(
      new InMemorySheet<>(
        new Description.Fake("id", expected.toString())
      ).id(),
      equalTo(expected)
    );
  }

  @Test
  void testWord() {
    assertThat(
      new InMemorySheet<>(new Description.Fake()).word(new Dice.Fake<>()),
      emits(nullValue())
    );
  }

  @Test
  void testWordsWithDuplicates() {
    assertThat(
      new InMemorySheet<>(
        new Description.Fake(),
        Set.of(
          new Dice.Fake<>(
            List.of(1, 2, 3)
          ),
          new Dice.Fake<>(
            List.of(1, 2, 3)
          )
        )
      ).words(),
      AreEmitted.emits(hasSize(2))
    );
  }

  @Test
  void testWordsAfterAddingAWord() {
    var sheets = new InMemorySheet<>(new Description.Fake());
    assertThat(
      sheets
        .word(new Dice.Fake<>())
        .onItem().transformToMulti(x -> sheets.words()),
      AreEmitted.emits(hasSize(1))
    );
  }

  @Test
  void testDescription() {
    assertThat(
      new InMemorySheet<>(new Description.Fake("fake", "value"))
        .description()
        .feature("fake"),
      contains("value")
    );
  }
}
