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
package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.score.Score;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class ClassicRuledMatchesTest {
  @Test
  void testExistingMatchWithARuleAssigned() {
    assertThat(
      new ClassicRuledMatches<>(
        new Matches.Fake<>(
          x -> Uni.createFrom().nullItem(),
          id -> Uni.createFrom().item(new Match.Fake<>(id, new Description.Fake()))
        ),
        Map.of(
          x -> true,
          match -> new Score.Fake<>()
        )
      ).match(UUID.randomUUID()),
      emits(notNullValue())
    );
  }

  @Test
  void testExistingMatchWithoutARuleAssigned() {
    assertThat(
      new ClassicRuledMatches<>(
        new Matches.Fake<>(
          x -> Uni.createFrom().nullItem(),
          id -> Uni.createFrom().item(new Match.Fake<>(id, new Description.Fake()))
        ),
        Map.of()
      ).match(UUID.randomUUID()),
      emits(nullValue())
    );
  }

  @Test
  void testMissingMatch() {
    assertThat(
      new ClassicRuledMatches<>(
        new Matches.Fake<>(
          x -> Uni.createFrom().nullItem(),
          x -> Uni.createFrom().nullItem()
        ),
        Map.of()
      ).match(UUID.randomUUID()),
      emits(nullValue())
    );
  }

  @Test
  void testMatchCreationWithoutARuleAssigned() {
    assertThat(
      new ClassicRuledMatches<>(
        new Matches.Fake<>(
          description -> Uni.createFrom().item(new Match.Fake<>(UUID.randomUUID(), description)),
          id -> Uni.createFrom().nullItem()
        ),
        Map.of()
      ).match(new Description.Fake()),
      emits(nullValue())
    );
  }

  @Test
  void testMatchCreationWithARuleAssigned() {
    assertThat(
      new ClassicRuledMatches<>(
        new Matches.Fake<>(
          description -> Uni.createFrom().item(new Match.Fake<>(UUID.randomUUID(), description)),
          id -> Uni.createFrom().nullItem()
        ),
        Map.of(
          x -> true,
          match -> new Score.Fake<>()
        )
      ).match(new Description.Fake()),
      emits(notNullValue())
    );
  }
}
