package io.github.raffaeleflorio.boggle.domain.score;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.vocabulary.Vocabulary;
import org.junit.jupiter.api.Test;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class IfInVocabularyTest {
  @Test
  void testScoreInVocabulary() {
    assertThat(
      new IfInVocabulary<>(
        new Score.Fake<>(x -> 123),
        new Vocabulary.Fake<>(x -> true)
      ).score(new Dice.Fake<>()),
      emits(equalTo(123))
    );
  }

  @Test
  void testScoreNotInVocabulary() {
    assertThat(
      new IfInVocabulary<>(
        new Score.Fake<>(x -> 789),
        new Vocabulary.Fake<>(x -> false)
      ).score(new Dice.Fake<>()),
      emits(equalTo(0))
    );
  }
}

