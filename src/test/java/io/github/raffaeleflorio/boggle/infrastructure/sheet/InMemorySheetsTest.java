package io.github.raffaeleflorio.boggle.infrastructure.sheet;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

class InMemorySheetsTest {
  @Test
  void testSheetCreation() {
    assertThat(
      new InMemorySheets<>().sheet(new Description.Fake()),
      emits(notNullValue())
    );
  }

  @Test
  void testExistingSheet() {
    var existingSheet = UUID.randomUUID();
    assertThat(
      new InMemorySheets<>(
        new ConcurrentHashMap<>(
          Map.of(
            existingSheet, Map.entry(new Sheet.Fake<>(), new Description.Fake())
          )
        )
      ).sheet(existingSheet),
      emits(notNullValue())
    );
  }

  @Test
  void testMissingSheet() {
    assertThat(
      new InMemorySheets<>().sheet(UUID.randomUUID()),
      emits(nullValue())
    );
  }
}
