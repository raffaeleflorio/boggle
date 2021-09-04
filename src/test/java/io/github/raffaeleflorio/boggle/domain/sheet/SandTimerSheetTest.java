package io.github.raffaeleflorio.boggle.domain.sheet;

import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.sandtimer.SandTimer;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.github.raffaeleflorio.boggle.hamcrest.IsThrowedWithMessage.throwsWithMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

class SandTimerSheetTest {
  @Test
  void testIdBeforeExpiration() {
    var expected = UUID.randomUUID();
    assertThat(
      new SandTimerSheet<>(
        new Sheet.Fake<>(expected),
        new SandTimer.Fake(false)
      ).id(),
      equalTo(expected)
    );
  }

  @Test
  void testIdAfterExpiration() {
    assertThat(
      () -> new SandTimerSheet<>(
        new Sheet.Fake<>(UUID.randomUUID()),
        new SandTimer.Fake(true)
      ).id(),
      throwsWithMessage(IllegalStateException.class, "Deadline reached")
    );
  }

  @Test
  void testDescriptionBeforeExpiration() {
    assertThat(
      () -> new SandTimerSheet<>(
        new Sheet.Fake<>(UUID.randomUUID()),
        new SandTimer.Fake(false)
      ).description(),
      not(throwsWithMessage(IllegalStateException.class, "Deadline reached"))
    );
  }

  @Test
  void testDescriptionAfterExpiration() {
    assertThat(
      () -> new SandTimerSheet<>(
        new Sheet.Fake<>(UUID.randomUUID()),
        new SandTimer.Fake(true)
      ).description(),
      throwsWithMessage(IllegalStateException.class, "Deadline reached")
    );
  }

  @Test
  void testWordBeforeExpiration() {
    assertThat(
      () -> new SandTimerSheet<>(
        new Sheet.Fake<>(UUID.randomUUID()),
        new SandTimer.Fake(false)
      ).word(new Dice.Fake<>()),
      not(throwsWithMessage(IllegalStateException.class, "Deadline reached"))
    );
  }

  @Test
  void testWordAfterExpiration() {
    assertThat(
      () -> new SandTimerSheet<>(
        new Sheet.Fake<>(UUID.randomUUID()),
        new SandTimer.Fake(true)
      ).word(new Dice.Fake<>()),
      throwsWithMessage(IllegalStateException.class, "Deadline reached")
    );
  }

  @Test
  void testWordsBeforeExpiration() {
    assertThat(
      () -> new SandTimerSheet<>(
        new Sheet.Fake<>(UUID.randomUUID()),
        new SandTimer.Fake(false)
      ).words(),
      not(throwsWithMessage(IllegalStateException.class, "Deadline reached"))
    );
  }

  @Test
  void testWordsAfterExpiration() {
    assertThat(
      () -> new SandTimerSheet<>(
        new Sheet.Fake<>(UUID.randomUUID()),
        new SandTimer.Fake(true)
      ).words(),
      throwsWithMessage(IllegalStateException.class, "Deadline reached")
    );
  }

  @Test
  void testUniqueWordsBeforeExpiration() {
    assertThat(
      () -> new SandTimerSheet<>(
        new Sheet.Fake<>(UUID.randomUUID()),
        new SandTimer.Fake(false)
      ).words(new Sheet.Fake<>()),
      not(throwsWithMessage(IllegalStateException.class, "Deadline reached"))
    );
  }

  @Test
  void testUniqueWordsAfterExpiration() {
    assertThat(
      () -> new SandTimerSheet<>(
        new Sheet.Fake<>(UUID.randomUUID()),
        new SandTimer.Fake(true)
      ).words(new Sheet.Fake<>()),
      throwsWithMessage(IllegalStateException.class, "Deadline reached")
    );
  }
}
