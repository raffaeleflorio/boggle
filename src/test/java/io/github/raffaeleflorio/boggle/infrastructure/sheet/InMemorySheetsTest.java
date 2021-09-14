/*
   Copyright 2021 Raffaele Florio

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
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
            existingSheet, new Sheet.Fake<>()
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

  @Test
  void testSheetBuildingAfterCreation() {
    var sheets = new InMemorySheets<>();
    assertThat(
      sheets
        .sheet(new Description.Fake())
        .onItem().transform(Sheet::id)
        .onItem().transform(sheets::sheet),
      emits(notNullValue())
    );
  }
}
