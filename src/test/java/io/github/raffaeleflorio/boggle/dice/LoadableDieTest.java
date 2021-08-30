package io.github.raffaeleflorio.boggle.dice;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class LoadableDieTest {
  @Test
  void testInitialValue() {
    assertThat(
      new LoadableDie<>(List.of("EXPECTED", "2", "3")).value(),
      equalTo("EXPECTED")
    );
  }

  @Test
  void testRolled() {
    assertThat(
      new LoadableDie<>(List.of(1, 2, 3, 42, 5), x -> 3).rolled().value(),
      equalTo(42)
    );
  }
}
