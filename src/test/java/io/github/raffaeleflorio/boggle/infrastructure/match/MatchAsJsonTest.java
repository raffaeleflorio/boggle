package io.github.raffaeleflorio.boggle.infrastructure.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.grid.Grid;
import io.github.raffaeleflorio.boggle.domain.match.Match;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class MatchAsJsonTest {
  @Test
  void testEmission() {
    var id = UUID.randomUUID();
    assertThat(
      new MatchAsJson(
        new Match.Fake<>(
          id,
          new Description.Fake(
            Map.of(
              "id", List.of(id.toString()),
              "deadline", List.of("a deadline")
            )
          ),
          Map.of(),
          Map.of(),
          new Grid.Fake<>(
            new Description.Fake(
              Map.of(
                "lang", List.of("a language"),
                "layout", List.of("a layout"),
                "size", List.of("a size")
              )
            )
          )
        )
      ).onItem().transform(JsonObject::encode),
      emits(equalTo(
          new JsonObject()
            .put("id", id.toString())
            .put("deadline", "a deadline")
            .put("grid", new JsonObject()
              .put("lang", "a language")
              .put("size", "a size")
              .put("layout", new JsonArray().add("a layout"))
            )
            .encode()
        )
      )
    );
  }
}
