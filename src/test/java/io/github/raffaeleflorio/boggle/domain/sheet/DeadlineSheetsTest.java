package io.github.raffaeleflorio.boggle.domain.sheet;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static io.github.raffaeleflorio.boggle.hamcrest.IsThrowedWithMessage.throwsWithMessage;
import static org.hamcrest.MatcherAssert.assertThat;

class DeadlineSheetsTest {
  @Test
  void testCreationOfExpiredSheet() {
    assertThat(
      new DeadlineSheets<>(
        new Sheets.Fake<>(
          x -> Uni.createFrom().nullItem(),
          description -> Uni.createFrom().item(new Sheet.Fake<>(UUID.randomUUID(), description))
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
      new DeadlineSheets<>(
        new Sheets.Fake<>(
          id -> Uni.createFrom().item(
            new Sheet.Fake<>(id, new Description.Fake("deadline", "1970-01-01T00:00:00Z"))
          ),
          x -> Uni.createFrom().nullItem()
        )
      ).sheet(UUID.randomUUID()).onItem().transform(x -> x::id),
      emits(
        throwsWithMessage(IllegalStateException.class, "Deadline reached")
      )
    );
  }
}
