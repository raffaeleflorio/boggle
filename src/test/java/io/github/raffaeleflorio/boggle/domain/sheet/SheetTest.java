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
package io.github.raffaeleflorio.boggle.domain.sheet;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.hamcrest.AreEmitted;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class SheetTest {
  @Nested
  class FakeTest {
    @Test
    void testId() {
      var expected = UUID.randomUUID();
      assertThat(
        new Sheet.Fake<>(expected).id(),
        equalTo(expected)
      );
    }

    @Test
    void testDefaultWordCreateion() {
      assertThat(
        new Sheet.Fake<>().word(new Dice.Fake<>()),
        emits(nullValue())
      );
    }

    @Test
    void testDefaultWords() {
      assertThat(
        new Sheet.Fake<>().words(),
        AreEmitted.emits(empty())
      );
    }

    @Test
    void testCustomWords() {
      assertThat(
        new Sheet.Fake<>(
          List.of(
            new Dice.Fake<>(List.of(1, 42)),
            new Dice.Fake<>(List.of(2, 43)),
            new Dice.Fake<>(List.of(3, 44))
          )
        ).words().onItem().transform(Dice::values),
        AreEmitted.emits(
          contains(
            List.of(1, 42),
            List.of(2, 43),
            List.of(3, 44)
          )
        )
      );
    }

    @Test
    void testDescription() {
      assertThat(
        new Sheet.Fake<>(
          UUID.randomUUID(),
          new Description.Fake("existing", List.of("feature", "value"))
        ).description().feature("existing"),
        contains("feature", "value")
      );
    }
  }
}
