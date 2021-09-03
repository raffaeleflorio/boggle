package io.github.raffaeleflorio.boggle.domain.dice;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class RandomDieTest {
  @Test
  void testDefaultInitialValue() {
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
    var min = 16;
    var bound = 32;
    assertThat(
      new RandomDie<>(Function.identity(), min, bound).rolled().value(),
      allOf(
        lessThan(bound),
        greaterThanOrEqualTo(min)
      )
    );
  }
}
