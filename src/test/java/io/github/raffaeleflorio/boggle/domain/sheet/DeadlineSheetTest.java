/*
   Copyright 2021 Raffaele Florio

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
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
