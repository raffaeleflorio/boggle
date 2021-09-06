package io.github.raffaeleflorio.boggle.infrastructure.sheet;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.hamcrest.AreEmitted;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class InMemorySheetTest {
  @Test
  void testId() {
    var expected = UUID.randomUUID();
    assertThat(
      new InMemorySheet<>(
        new Description.Fake("id", expected.toString())
      ).id(),
      equalTo(expected)
    );
  }

  @Test
  void testWord() {
    assertThat(
      new InMemorySheet<>(new Description.Fake()).word(new Dice.Fake<>()),
      emits(nullValue())
    );
  }

  @Test
  void testWordsWithoutDuplicates() {
    assertThat(
      new InMemorySheet<>(
        new Description.Fake(),
        Set.of(
          new Dice.Fake<>(
            List.of(1, 2, 3)
          ),
          new Dice.Fake<>(
            List.of(1, 2, 3)
          )
        )
      ).words(),
      AreEmitted.emits(hasSize(1))
    );
  }

  @Test
  void testWordsAfterAddingAWord() {
    var sheets = new InMemorySheet<>(new Description.Fake());
    assertThat(
      sheets
        .word(new Dice.Fake<>())
        .onItem().transformToMulti(x -> sheets.words()),
      AreEmitted.emits(hasSize(1))
    );
  }

  @Test
  void testDescription() {
    assertThat(
      new InMemorySheet<>(new Description.Fake("fake", "value"))
        .description()
        .feature("fake"),
      contains("value")
    );
  }
}
