package io.github.raffaeleflorio.boggle.domain.dice;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class DieTest {
  @Nested
  class FakeTest {
    @Test
    void testInitialValue() {
      var expected = 123456790L;
      assertThat(
        new Die.Fake<>(expected).value(),
        equalTo(expected)
      );
    }

    @Test
    void testRolledValueWithoutNextFn() {
      var expected = "this value never change";
      assertThat(
        new Die.Fake<>(expected).rolled().value(),
        equalTo(expected)
      );
    }

    @Test
    void testRolledValueWithNextFn() {
      assertThat(
        new Die.Fake<>(
          1,
          x -> x + 1
        ).rolled().value(),
        equalTo(2)
      );
    }
  }
}
