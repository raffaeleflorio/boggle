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
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class MatchesTest {
  @Nested
  class FakeTest {
    @Test
    void testMatchCreation() {
      var expected = UUID.randomUUID();
      assertThat(
        new Matches.Fake<>(
          description -> Uni.createFrom().item(new Match.Fake<>(expected, description)),
          x -> Uni.createFrom().nullItem()
        ).match(
            new Description.Fake()
          )
          .onItem().transform(Match::id),
        emits(equalTo(expected))
      );
    }

    @Test
    void testExistingMatch() {
      var expected = UUID.randomUUID();
      assertThat(
        new Matches.Fake<>(
          x -> Uni.createFrom().nullItem(),
          id -> Uni.createFrom().item(new Match.Fake<>(id, new Description.Fake()))
        ).match(expected).onItem().transform(Match::id),
        emits(equalTo(expected))
      );
    }
  }
}
