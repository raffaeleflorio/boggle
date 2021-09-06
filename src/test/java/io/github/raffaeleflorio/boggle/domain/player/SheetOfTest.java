package io.github.raffaeleflorio.boggle.domain.player;

import io.github.raffaeleflorio.boggle.domain.match.Match;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class SheetOfTest {
  @Test
  void testEmission() {
    var expected = UUID.randomUUID();
    assertThat(
      new SheetOf<>(
        expected,
        new Match.Fake<>(
          Map.of(),
          Map.of(
            expected, new Sheet.Fake<>()
          )
        )
      ),
      emits(notNullValue())
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
