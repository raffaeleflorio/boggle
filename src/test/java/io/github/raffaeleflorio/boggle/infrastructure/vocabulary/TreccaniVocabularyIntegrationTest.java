package io.github.raffaeleflorio.boggle.infrastructure.vocabulary;

import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class TreccaniVocabularyIntegrationTest {
  @Test
  void testNotContainsAnEnglishWord() {
    assertThat(
      new TreccaniVocabulary(WebClient.create(Vertx.vertx())).contains("dog"),
      emits(
        equalTo(false),
        Duration.ofSeconds(5)
      )
    );
  }

  @Test
  void testNotContainsAnItalianWord() {
    assertThat(
      new TreccaniVocabulary(WebClient.create(Vertx.vertx())).contains("cane"),
      emits(
        equalTo(true),
        Duration.ofSeconds(5)
      )
    );
  }
}
