package io.github.raffaeleflorio.boggle.domain.player;

import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

class PlayerTest {
  @Test
  void testId() {
    var expected = UUID.randomUUID();
    assertThat(
      new Player.Fake<>(expected).id(),
      equalTo(expected)
    );
  }

  @Test
  void testSheet() {
    assertThat(
      new Player.Fake<>().sheet(UUID.randomUUID()),
      emits(nullValue())
    );
  }

  @Test
  void testCustomSheet() {
    var expected = UUID.randomUUID();
    assertThat(
      new Player.Fake<>(
        UUID.randomUUID(),
        Uni.createFrom().item(new Sheet.Fake<>(expected))
      ).sheet(UUID.randomUUID()).onItem().transform(Sheet::id),
      emits(equalTo(expected))
    );
  }
}
