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

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class GridsTest {
  @Nested
  class FakeTest {
    @Test
    void testEmptyRepo() {
      assertThat(
        new Grids.Fake<>().grid(new Description.Fake()),
        emits(nullValue())
      );
    }

    @Test
    void testFullRepo() {
      assertThat(
        new Grids.Fake<>(description -> Uni.createFrom().item(new Grid.Fake<>()))
          .grid(new Description.Fake()),
        emits(notNullValue())
      );
    }
  }
}
