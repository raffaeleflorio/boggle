package io.github.raffaeleflorio.boggle.domain.sandtimer;

import org.junit.jupiter.api.Test;

import static io.github.raffaeleflorio.boggle.hamcrest.After.after;
import static io.github.raffaeleflorio.boggle.hamcrest.IsSupplied.supplies;
import static java.time.Duration.ofMillis;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class SimpleSandTimerTest {
  @Test
  void testExpired() {
    var timer = new SimpleSandTimer(ofMillis(50));
    assertThat(
      timer::expired,
      after(
        60, MILLISECONDS,
        supplies(equalTo(true))
      )
    );
  }

  @Test
  void testNotExpired() {
    var timer = new SimpleSandTimer(ofMillis(100));
    assertThat(
      timer::expired,
      after(
        20, MILLISECONDS,
        supplies(equalTo(false))
      )
    );
  }
}
