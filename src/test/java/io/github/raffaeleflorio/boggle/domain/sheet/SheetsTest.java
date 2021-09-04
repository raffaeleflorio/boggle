package io.github.raffaeleflorio.boggle.domain.sheet;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

class SheetsTest {
  @Nested
  class FakeTest {
    @Test
    void testSheetCreation() {
      assertThat(
        new Sheets.Fake<>().sheet(new Description.Fake()),
        emits(notNullValue())
      );
    }

    @Test
    void testSheetBuilding() {
      assertThat(
        new Sheets.Fake<>().sheet(UUID.randomUUID()),
        emits(notNullValue())
      );
    }
  }
}
