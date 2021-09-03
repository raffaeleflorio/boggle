package io.github.raffaeleflorio.boggle.domain.grid;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

class DescriptionTest {
  @Test
  void testPermanentEmptiness() {
    assertThat(
      new Description.Fake().feature("any id"),
      empty()
    );
  }
}
