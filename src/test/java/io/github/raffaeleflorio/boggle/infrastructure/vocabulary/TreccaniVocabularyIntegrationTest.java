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
package io.github.raffaeleflorio.boggle.infrastructure.vocabulary;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class TreccaniVocabularyIntegrationTest {
  @Test
  void testNotContainsAnEnglishWord() {
    assertThat(
      new TreccaniVocabulary(WebClient.create(Vertx.vertx())).contains(
        new Dice.Fake<>(
          List.of("D", "O", "G")
        )
      ),
      emits(
        equalTo(false),
        Duration.ofSeconds(5)
      )
    );
  }

  @Test
  void testContainsAnItalianWord() {
    assertThat(
      new TreccaniVocabulary(WebClient.create(Vertx.vertx())).contains(
        new Dice.Fake<>(
          List.of("C", "A", "N", "E")
        )
      ),
      emits(
        equalTo(true),
        Duration.ofSeconds(5)
      )
    );
  }
}
