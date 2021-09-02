package io.github.raffaeleflorio.boggle.dice;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CryptoDieTest {
  @Test
  void testInitialValue() {
    assertThat(
      new CryptoDie<>(Function.identity()).value(),
      equalTo(0)
    );
  }

  @Test
  void testCustomInitialValue() {
    assertThat(
      new CryptoDie<>(Function.identity(), 42).value(),
      equalTo(42)
    );
  }

  @RepeatedTest(128)
  void testRolledWithBound() {
    var min = 2;
    var bound = 16;
    assertThat(
      new CryptoDie<>(Function.identity(), min, bound).rolled().value(),
      allOf(
        lessThan(bound),
        greaterThanOrEqualTo(min)
      )
    );
  }
}
