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
