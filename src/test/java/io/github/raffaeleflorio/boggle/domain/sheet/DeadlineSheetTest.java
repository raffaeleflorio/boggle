package io.github.raffaeleflorio.boggle.domain.sheet;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.sandtimer.SandTimer;
import io.github.raffaeleflorio.boggle.hamcrest.AreEmittedFailure;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsEmittedFailure.emits;
import static io.github.raffaeleflorio.boggle.hamcrest.IsThrowedWithMessage.throwsWithMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

class DeadlineSheetTest {
  @Test
  void testAddingWordAfterExpiration() {
    assertThat(
      new DeadlineSheet<>(
        new Sheet.Fake<>(UUID.randomUUID()),
        new SandTimer.Fake(true)
      ).word(new Dice.Fake<>()),
      emits(IllegalStateException.class, "Deadline reached")
    );
  }

  @Test
  void testAddingWordBeforeExpiration() {
    assertThat(
      new DeadlineSheet<>(
        new Sheet.Fake<>(UUID.randomUUID()),
        new SandTimer.Fake(false)
      ).word(new Dice.Fake<>()),
      not(emits(IllegalStateException.class, "Deadline reached"))
    );
  }

  @Test
  void testIdAfterExpiration() {
    var expected = UUID.randomUUID();
    assertThat(
      new DeadlineSheet<>(
        new Sheet.Fake<>(expected),
        new SandTimer.Fake(true)
      ).id(),
      equalTo(expected)
    );
  }

  @Test
  void testDescriptionAfterExpiration() {
    assertThat(
      () -> new DeadlineSheet<>(
        new Sheet.Fake<>(UUID.randomUUID()),
        Instant.EPOCH
      ).description(),
      not(throwsWithMessage(IllegalStateException.class, "Deadline reached"))
    );
  }

  @Test
  void testWordsAfterExpiration() {
    assertThat(
      new DeadlineSheet<>(
        new Sheet.Fake<>(UUID.randomUUID()),
        new SandTimer.Fake(true)
      ).words(),
      not(AreEmittedFailure.emits(IllegalStateException.class, "Deadline reached"))
    );
  }
}
