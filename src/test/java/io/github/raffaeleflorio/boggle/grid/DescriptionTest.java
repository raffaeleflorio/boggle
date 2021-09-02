package io.github.raffaeleflorio.boggle.grid;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

class DescriptionTest {
  @Test
  void testPermanentEmptiness() {
    assertThat(
      new Description.Fake().feature("any id", List.of("1")).feature("any id"),
      empty()
    );
  }
}
