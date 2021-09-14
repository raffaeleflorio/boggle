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
package io.github.raffaeleflorio.boggle.infrastructure.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import io.github.raffaeleflorio.boggle.hamcrest.AreEmitted;
import io.github.raffaeleflorio.boggle.hamcrest.AreEmittedFailure;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class InMemoryMatchTest {
  @Test
  void testId() {
    var expected = UUID.randomUUID();
    assertThat(
      new InMemoryMatch<>(
        new Description.Fake("id", expected.toString()),
        new Grid.Fake<>()
      ).id(),
      equalTo(expected)
    );
  }

  @Test
  void testGrid() {
    assertThat(
      new InMemoryMatch<>(
        new Description.Fake(),
        new Grid.Fake<>(
          new Dice.Fake<>(List.of("A", "B"))
        )
      ).grid().onItem().transform(Grid::values),
      emits(
        contains("A", "B")
      )
    );
  }

  @Test
  void testDescription() {
    assertThat(
      new InMemoryMatch<>(
        new Description.Fake("feature", "value"),
        new Grid.Fake<>()
      ).description().feature("feature"),
      contains("value")
    );
  }

  @Test
  void testSheet() {
    assertThat(
      new InMemoryMatch<>(
        new Description.Fake(),
        new Grid.Fake<>()
      ).sheet(UUID.randomUUID()),
      emits(notNullValue())
    );
  }

  @Test
  void testPlayers() {
    var match = new InMemoryMatch<>(new Description.Fake(), new Grid.Fake<>());
    assertThat(
      match.sheet(UUID.randomUUID()).onItem().transformToMulti(x -> match.players()),
      AreEmitted.emits(hasSize(1))
    );
  }

  @Test
  void testScore() {
    assertThat(
      new InMemoryMatch<>(
        new Description.Fake(),
        new Grid.Fake<>()
      ).score(),
      AreEmittedFailure.emits(IllegalStateException.class, "Unable to build score")
    );
  }
}
