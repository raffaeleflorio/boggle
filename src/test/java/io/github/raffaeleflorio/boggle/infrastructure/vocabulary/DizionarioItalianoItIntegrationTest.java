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

class DizionarioItalianoItIntegrationTest {
  @Test
  void testNotContainsAnEnglishWord() {
    assertThat(
      new DizionarioItalianoIt(WebClient.create(Vertx.vertx())).contains(
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
      new DizionarioItalianoIt(WebClient.create(Vertx.vertx())).contains(
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

  @Test
  void testContainsAnotherItalianWord() {
    assertThat(
      new DizionarioItalianoIt(WebClient.create(Vertx.vertx())).contains(
        new Dice.Fake<>(
          List.of("S", "I", "B", "I", "L", "A", "T", "O")
        )
      ),
      emits(
        equalTo(true),
        Duration.ofSeconds(5)
      )
    );
  }
}
