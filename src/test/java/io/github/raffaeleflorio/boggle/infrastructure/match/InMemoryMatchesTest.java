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
import io.github.raffaeleflorio.boggle.domain.grid.Grids;
import io.github.raffaeleflorio.boggle.domain.match.Match;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class InMemoryMatchesTest {
  @Test
  void testMatchCreation() {
    assertThat(
      new InMemoryMatches<>(
        new Grids.Fake<>(
          x -> Uni.createFrom().item(new Grid.Fake<>())
        )
      ).match(new Description.Fake()),
      emits(notNullValue())
    );
  }

  @Test
  void testExistingMatch() {
    var matches = new InMemoryMatches<>(x -> Uni.createFrom().item(new Grid.Fake<>()));
    assertThat(
      matches.match(new Description.Fake())
        .onItem().transform(Match::id)
        .onItem().transform(matches::match),
      emits(notNullValue())
    );
  }

  @Test
  void testMissingMatch() {
    assertThat(
      new InMemoryMatches<>(new Grids.Fake<>()).match(UUID.randomUUID()),
      emits(nullValue())
    );
  }

  @Test
  void testMissingBackedGrid() {
    assertThat(
      new InMemoryMatches<>(
        new Grids.Fake<>(x -> Uni.createFrom().nullItem())
      ).match(new Description.Fake()),
      emits(nullValue())
    );
  }

  @Test
  void testMatchCreationShuffledGrid() {
    assertThat(
      new InMemoryMatches<>(
        new Grids.Fake<>(
          x -> Uni.createFrom().item(
            new Grid.Fake<>(
              new Dice.Fake<>(
                List.of(),
                y -> List.of("SHUFFLED")
              )
            )
          )
        )
      ).match(
          new Description.Fake()
        )
        .onItem().transformToUni(Match::grid)
        .onItem().transform(Grid::values),
      emits(contains("SHUFFLED"))
    );
  }
}
