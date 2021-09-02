package io.github.raffaeleflorio.boggle.dice;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

class RandomDieTest {
  @Test
  void testInitialValue() {
    assertThat(
      new RandomDie<>(Function.identity()).value(),
      equalTo(0)
    );
  }

  @Test
  void testCustomInitialValue() {
    assertThat(
      new RandomDie<>(Function.identity(), 123).value(),
      equalTo(123)
    );
  }

  @RepeatedTest(128)
  void testRolledWithBound() {
    var bound = 32;
    assertThat(
      new RandomDie<>(Function.identity(), 0, bound).rolled().value(),
      lessThan(bound)
    );
  }
}
