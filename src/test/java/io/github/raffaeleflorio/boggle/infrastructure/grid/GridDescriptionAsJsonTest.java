package io.github.raffaeleflorio.boggle.infrastructure.grid;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class GridDescriptionAsJsonTest {
  @Test
  void testEncode() {
    assertThat(
      new GridDescriptionAsJson(
        new Description.Fake(
          Map.of(
            "lang", List.of("any language"),
            "size", List.of("any size"),
            "layout", List.of("A", "B", "C")
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