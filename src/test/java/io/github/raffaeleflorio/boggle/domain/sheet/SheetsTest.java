package io.github.raffaeleflorio.boggle.domain.sheet;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class SheetsTest {
  @Nested
  class FakeTest {
    @Test
    void testDefaultSheetCreation() {
      assertThat(
        new Sheets.Fake<>().sheet(new Description.Fake()),
        emits(nullValue())
      );
    }

    @Test
    void testSheetCreationWithCustomFn() {
      assertThat(
        new Sheets.Fake<>(
          x -> Uni.createFrom().nullItem(),
          description -> Uni.createFrom().item(new Sheet.Fake<>(UUID.randomUUID(), description))
        ).sheet(new Description.Fake()),
        emits(notNullValue())
      );
    }

    @Test
    void testDefaultSheetBuilding() {
      assertThat(
        new Sheets.Fake<>().sheet(UUID.randomUUID()),
        emits(nullValue())
      );
    }

    @Test
    void testSheetBuildingWithCustomFn() {
      assertThat(
        new Sheets.Fake<>(
          id -> Uni.createFrom().item(new Sheet.Fake<>(id)),
          x -> Uni.createFrom().nullItem()
        ).sheet(UUID.randomUUID()),
        emits(notNullValue())
      );
    }
  }
}
