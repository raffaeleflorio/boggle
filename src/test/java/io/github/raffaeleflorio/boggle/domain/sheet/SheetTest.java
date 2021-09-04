package io.github.raffaeleflorio.boggle.domain.sheet;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.hamcrest.AreEmitted;
import io.smallrye.mutiny.Multi;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class SheetTest {
  @Test
  void testId() {
    var expected = UUID.fromString("6b47aefc-85e6-4125-b930-dd15e9227377");
    assertThat(
      new Sheet.Fake<>(expected).id(),
      equalTo(expected)
    );
  }

  @Test
  void testWord() {
    assertThat(
      new Sheet.Fake<>().word(new Dice.Fake<>()),
      emits(nullValue(Void.class))
    );
  }

  @Test
  void testWords() {
    assertThat(
      new Sheet.Fake<>().words(),
      AreEmitted.emits(empty())
    );
  }

  @Test
  void testCustomWords() {
    assertThat(
      new Sheet.Fake<>(
        UUID.randomUUID(),
        Multi.createFrom().items(
          new Dice.Fake<>(List.of(1, 42)),
          new Dice.Fake<>(List.of(2, 43)),
          new Dice.Fake<>(List.of(3, 44))
        ),
        Multi.createFrom().empty()
      ).words().onItem().transform(Dice::values),
      AreEmitted.emits(
        contains(
          List.of(1, 42),
          List.of(2, 43),
          List.of(3, 44)
        )
      )
    );
  }

  @Test
  void testUniqueWords() {
    assertThat(
      new Sheet.Fake<>(
        UUID.randomUUID(),
        Multi.createFrom().empty(),
        Multi.createFrom().items(new Dice.Fake<>(List.of("UNIQUE")))
      ).words(new Sheet.Fake<>()).onItem().transform(Dice::values),
      AreEmitted.emits(
        contains(
          List.of("UNIQUE")
        )
      )
    );
  }
}
