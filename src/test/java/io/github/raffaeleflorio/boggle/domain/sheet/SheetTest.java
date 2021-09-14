package io.github.raffaeleflorio.boggle.domain.sheet;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.hamcrest.AreEmitted;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmitted.emits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class SheetTest {
  @Nested
  class FakeTest {
    @Test
    void testId() {
      var expected = UUID.randomUUID();
      assertThat(
        new Sheet.Fake<>(expected).id(),
        equalTo(expected)
      );
    }

    @Test
    void testDefaultWordCreateion() {
      assertThat(
        new Sheet.Fake<>().word(new Dice.Fake<>()),
        emits(nullValue())
      );
    }

    @Test
    void testDefaultWords() {
      assertThat(
        new Sheet.Fake<>().words(),
        AreEmitted.emits(empty())
      );
    }

    @Test
    void testCustomWords() {
      assertThat(
        new Sheet.Fake<>(
          List.of(
            new Dice.Fake<>(List.of(1, 42)),
            new Dice.Fake<>(List.of(2, 43)),
            new Dice.Fake<>(List.of(3, 44))
          )
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
    void testDescription() {
      assertThat(
        new Sheet.Fake<>(
          UUID.randomUUID(),
          new Description.Fake("existing", List.of("feature", "value"))
        ).description().feature("existing"),
        contains("feature", "value")
      );
    }
  }
}
