package io.github.raffaeleflorio.boggle.infrastructure.player;

import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheets;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class JsonPlayerTest {
  @Test
  void testId() {
    var expected = UUID.randomUUID();
    assertThat(
      new JsonPlayer(
        new JsonObject().put("id", expected.toString()),
        new Sheets.Fake<>()
      ).id(),
      equalTo(expected)
    );
  }

  @Test
  void testSheet() {
    var expected = UUID.randomUUID();
    assertThat(
      new JsonPlayer(
        new JsonObject(),
        new Sheets.Fake<>(
          id -> Uni.createFrom().item(new Sheet.Fake<>(id)),
          x -> Uni.createFrom().nullItem()
        )
      ).sheet(expected).onItem().transform(Sheet::id),
      emits(equalTo(expected))
    );
  }
}
