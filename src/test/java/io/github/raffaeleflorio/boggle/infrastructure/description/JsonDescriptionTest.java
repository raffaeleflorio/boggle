package io.github.raffaeleflorio.boggle.infrastructure.description;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

class JsonDescriptionTest {
  @Test
  void testLang() {
    assertThat(
      new JsonDescription(
        new JsonObject().put("lang", "any language")
      ).feature("lang"),
      contains("any language")
    );
  }

  @Test
  void testDuration() {
    assertThat(
      new JsonDescription(
        new JsonObject().put("duration", 3)
      ).feature("duration"),
      contains("3")
    );
  }

  @Test
  void testSize() {
    assertThat(
      new JsonDescription(
        new JsonObject().put("size", "any size")
      ).feature("size"),
      contains("any size")
    );
  }

  @Test
  void testNewFeature() {
    assertThat(
      new JsonDescription(new JsonObject())
        .feature("any", List.of("only first", "second"))
        .feature("any"),
      contains("only first")
    );
  }
}
