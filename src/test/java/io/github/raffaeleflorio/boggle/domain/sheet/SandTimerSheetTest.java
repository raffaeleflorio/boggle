package io.github.raffaeleflorio.boggle.domain.sheet;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.sandtimer.SandTimer;
import io.github.raffaeleflorio.boggle.hamcrest.AreEmittedFailure;
import io.github.raffaeleflorio.boggle.hamcrest.IsEmittedFailure;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsThrowedWithMessage.throwsWithMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

class SandTimerSheetTest {
  @Test
  void testWordAfterExpiration() {
    assertThat(
      new SandTimerSheet<>(
        new Sheet.Fake<>(UUID.randomUUID()),
        new SandTimer.Fake(true)
      ).word(new Dice.Fake<>()),
      IsEmittedFailure.emits(IllegalStateException.class, "Deadline reached")
    );
  }

  @Test
  void testWordBeforeExpiration() {
    assertThat(
      new SandTimerSheet<>(
        new Sheet.Fake<>(UUID.randomUUID()),
        new SandTimer.Fake(false)
      ).word(new Dice.Fake<>()),
      not(IsEmittedFailure.emits(IllegalStateException.class, "Deadline reached"))
    );
  }

  @Test
  void testIdAfterExpiration() {
    var expected = UUID.randomUUID();
    assertThat(
      new SandTimerSheet<>(
        new Sheet.Fake<>(expected),
        new SandTimer.Fake(true)
      ).id(),
      equalTo(expected)
    );
  }

  @Test
  void testDescriptionAfterExpiration() {
    assertThat(
      () -> new SandTimerSheet<>(
        new Sheet.Fake<>(UUID.randomUUID()),
        Instant.EPOCH
      ).description(),
      not(throwsWithMessage(IllegalStateException.class, "Deadline reached"))
    );
  }

  @Test
  void testWordsAfterExpiration() {
    assertThat(
      new SandTimerSheet<>(
        new Sheet.Fake<>(UUID.randomUUID()),
        new SandTimer.Fake(true)
      ).words(),
      not(AreEmittedFailure.emits(IllegalStateException.class, "Deadline reached"))
    );
  }

  @Test
  void testUniqueWordsAfterExpiration() {
    assertThat(
      new SandTimerSheet<>(
        new Sheet.Fake<>(UUID.randomUUID()),
        new SandTimer.Fake(true)
      ).words(new Sheet.Fake<>()),
      not(AreEmittedFailure.emits(IllegalStateException.class, "Deadline reached"))
    );
  }
}
