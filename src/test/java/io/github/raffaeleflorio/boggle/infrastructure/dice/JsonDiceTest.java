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
package io.github.raffaeleflorio.boggle.infrastructure.dice;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;

class JsonDiceTest {
  @Test
  void testValues() {
    assertThat(
      new JsonDice(
        new JsonObject().put("dice", new JsonArray().add("A").add("B").add("C"))
      ).values(),
      contains("A", "B", "C")
    );
  }

  @Test
  void testShuffledValues() {
    assertThat(
      new JsonDice(
        new JsonObject().put("dice", new JsonArray().add("A").add("B").add("C"))
      ).shuffled().values(),
      containsInAnyOrder("A", "B", "C")
    );
  }
}
