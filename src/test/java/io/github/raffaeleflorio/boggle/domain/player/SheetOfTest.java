package io.github.raffaeleflorio.boggle.domain.player;

import io.github.raffaeleflorio.boggle.domain.match.Match;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheets;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

class SheetOfTest {
  @Test
  void testEmission() {
    var expected = UUID.randomUUID();
    assertThat(
      new SheetOf<>(
        UUID.randomUUID(),
        new Match.Fake<>(
          new Sheets.Fake<>(
            x -> Uni.createFrom().item(new Sheet.Fake<>(expected)),
            x -> Uni.createFrom().nullItem()
          )
        )
      ).onItem().transform(Sheet::id),
      emits(equalTo(expected))
    );
  }

  @Test
  void testNullEmission() {
    assertThat(
      new SheetOf<>(UUID.randomUUID(), new Match.Fake<>()),
      emits(nullValue())
    );
  }
}
