package io.github.raffaeleflorio.boggle.infrastructure.player;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class JsonPlayerTest {
  @Test
  void testId() {
    var expected = UUID.randomUUID();
    assertThat(
      new JsonPlayer(new JsonObject().put("player", expected.toString())).id(),
      equalTo(expected)
    );
  }
}
