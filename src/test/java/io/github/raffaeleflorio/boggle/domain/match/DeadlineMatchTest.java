package io.github.raffaeleflorio.boggle.domain.match;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.sandtimer.SandTimer;
import io.github.raffaeleflorio.boggle.hamcrest.AreEmitted;
import io.github.raffaeleflorio.boggle.hamcrest.AreEmittedFailure;
import io.github.raffaeleflorio.boggle.hamcrest.IsEmitted;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class DeadlineMatchTest {
  @Test
  void testScoreBeforeExpiration() {
    var expected = UUID.randomUUID();
    assertThat(
      new DeadlineMatch<>(
        new Match.Fake<>(expected, new Description.Fake()),
        new SandTimer.Fake(true)
      ).score(),
      not(
        AreEmittedFailure.emits(
          IllegalStateException.class, "Unable to build score of an in progress match"
        )
      )
    );
  }

  @Test
  void testScoreAfterExpiration() {
    assertThat(
      new DeadlineMatch<>(
        new Match.Fake<>(UUID.randomUUID(), new Description.Fake()),
        new SandTimer.Fake(false)
      ).score(),
      AreEmittedFailure.emits(
        IllegalStateException.class, "Unable to build score of an in progress match"
      )
    );
  }

  @Test
  void testIdAfterExpiration() {
    var expected = UUID.randomUUID();
    assertThat(
      new DeadlineMatch<>(
        new Match.Fake<>(expected, new Description.Fake()),
        new SandTimer.Fake(true)
      ).id(),
      equalTo(expected)
    );
  }

  @Test
  void testDescriptionAfterExpiration() {
    assertThat(
      new DeadlineMatch<>(
        new Match.Fake<>(UUID.randomUUID(), new Description.Fake("feature", "value")),
        new SandTimer.Fake(true)
      ).description().feature("feature"),
      contains("value")
    );
  }

  @Test
  void testGridAfterExpiration() {
    assertThat(
      new DeadlineMatch<>(
        new Match.Fake<>(UUID.randomUUID(), new Description.Fake()),
        new SandTimer.Fake(true)
      ).grid(),
      IsEmitted.emits(notNullValue())
    );
  }

  @Test
  void testPlayersAfterExpiration() {
    assertThat(
      new DeadlineMatch<>(
        new Match.Fake<>(UUID.randomUUID(), new Description.Fake()),
        new SandTimer.Fake(true)
      ).players(),
      AreEmitted.emits(hasSize(0))
    );
  }
}
