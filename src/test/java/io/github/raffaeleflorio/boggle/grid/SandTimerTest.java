package io.github.raffaeleflorio.boggle.grid;

import io.github.raffaeleflorio.boggle.dice.Dice;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.github.raffaeleflorio.boggle.hamcrest.After.after;
import static io.github.raffaeleflorio.boggle.hamcrest.IsThrowedWithMessage.throwsWithMessage;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

class SandTimerTest {
  @Test
  void testDescription() {
    assertThat(
      new SandTimer<>(new Grid.Fake<>(), ofMinutes(2), () -> Instant.EPOCH).description(),
      equalTo(
        Map.of(
          "id", "fake grid",
          "deadline", "1970-01-01T00:02:00Z"
        )
      )
    );
  }

  @Test
  void testScoreAfterExpiration() {
    var timer = new SandTimer<>(new Grid.Fake<>(), ofMillis(10));
    assertThat(
      () -> timer.score(new Dice.Fake<>()),
      after(
        20, TimeUnit.MILLISECONDS,
        throwsWithMessage(IllegalStateException.class, "Deadline reached")
      )
    );
  }

  @Test
  void testShuffledAfterExpiration() {
    var timer = new SandTimer<>(new Grid.Fake<>(), ofMillis(5));
    assertThat(
      timer::shuffled,
      after(
        20, TimeUnit.MILLISECONDS,
        throwsWithMessage(IllegalStateException.class, "Deadline reached")
      )
    );
  }

  @Test
  void testScoreBeforeExpiration() {
    assertThat(
      new SandTimer<>(new Grid.Fake<>(x -> 100), ofMinutes(10)).score(new Dice.Fake<>()),
      after(
        20, TimeUnit.MILLISECONDS,
        equalTo(100)
      )
    );
  }

  @Test
  void testShuffledBeforeExpiration() {
    assertThat(
      new SandTimer<>(new Grid.Fake<>(x -> 100), ofMinutes(10)).shuffled().score(new Dice.Fake<>()),
      after(
        20, TimeUnit.MILLISECONDS,
        equalTo(100)
      )
    );
  }

  @Test
  void testValuesAfterExpiration() {
    var timer = new SandTimer<>(new Grid.Fake<>(), ofMillis(42));
    assertThat(
      timer::values,
      after(
        100, TimeUnit.MILLISECONDS,
        throwsWithMessage(IllegalStateException.class, "Deadline reached")
      )
    );
  }

  @Test
  void testValuesBeforeExpiration() {
    assertThat(
      new SandTimer<>(
        new Grid.Fake<>(new Dice.Fake<>(List.of(1))),
        ofMinutes(1)
      ).values(),
      after(
        100, TimeUnit.MILLISECONDS,
        contains(1)
      )
    );
  }
}
