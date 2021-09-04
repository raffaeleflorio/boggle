package io.github.raffaeleflorio.boggle.domain.sheet;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static io.github.raffaeleflorio.boggle.hamcrest.IsThrowedWithMessage.throwsWithMessage;
import static org.hamcrest.MatcherAssert.assertThat;

class SandTimerSheetsTest {
  @Test
  void testCreationOfExpiredSheet() {
    assertThat(
      new SandTimerSheets<>(
        new Sheets.Fake<>(
          new Sheet.Fake<>(
            UUID.randomUUID(),
            new Description.Fake().feature("deadline", List.of("1970-01-01T00:00:00Z"))
          )
        )
      ).sheet(new Description.Fake("deadline", List.of("1970-01-01T00:00:00Z"))).onItem().transform(x -> x::id),
      emits(
        throwsWithMessage(IllegalStateException.class, "Deadline reached")
      )
    );
  }

  @Test
  void testBuildingOfExpiredSheet() {
    assertThat(
      new SandTimerSheets<>(
        new Sheets.Fake<>(
          new Sheet.Fake<>(
            UUID.randomUUID(),
            new Description.Fake().feature("deadline", List.of("1970-01-01T00:00:00Z"))
          )
        )
      ).sheet(UUID.randomUUID()).onItem().transform(x -> x::id),
      emits(
        throwsWithMessage(IllegalStateException.class, "Deadline reached")
      )
    );
  }
}
