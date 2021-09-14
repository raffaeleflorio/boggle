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
package io.github.raffaeleflorio.boggle.infrastructure.grid;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class GridAsJsonTest {
  @Test
  void testEncode() {
    assertThat(
      new GridAsJson(
        new Grid.Fake<>(
          new Description.Fake(
            Map.of(
              "lang", List.of("any language"),
              "size", List.of("any size"),
              "layout", List.of("A", "B", "C")
            )
          )
        )
      ).encode(),
      equalTo(
        new JsonObject()
          .put("lang", "any language")
          .put("size", "any size")
          .put("layout", new JsonArray().add("A").add("B").add("C"))
          .encode()
      )
    );
  }
}
