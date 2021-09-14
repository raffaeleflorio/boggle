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
