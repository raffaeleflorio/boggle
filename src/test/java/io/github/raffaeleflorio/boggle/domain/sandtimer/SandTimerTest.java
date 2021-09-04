package io.github.raffaeleflorio.boggle.domain.sandtimer;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class SandTimerTest {
  @Nested
  class FakeTest {
    @Test
    void testExpired() {
      assertThat(
        new SandTimer.Fake(true).expired(),
        equalTo(true)
      );
    }

    @Test
    void testNotExpired() {
      assertThat(
        new SandTimer.Fake(false).expired(),
        equalTo(false)
      );
    }
  }
}
