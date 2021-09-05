package io.github.raffaeleflorio.boggle.domain.vocabulary;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class VocabularyTest {
  @Nested
  class FakeTest {
    @Test
    void testDefaultContains() {
      assertThat(
        new Vocabulary.Fake<>().contains(new Dice.Fake<>(List.of("ANY"))),
        emits(equalTo(false))
      );
    }

    @Test
    void testCustomContains() {
      assertThat(
        new Vocabulary.Fake<>(x -> true).contains(new Dice.Fake<>(List.of("ANY"))),
        emits(equalTo(true))
      );
    }
  }
}
