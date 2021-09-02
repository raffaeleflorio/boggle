package io.github.raffaeleflorio.boggle.grid;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;

class PairDescriptionTest {
  @Test
  void testFeature() {
    assertThat(
      new PairDescription("name", "single value").feature("name"),
      contains("single value")
    );
  }

  @Test
  void testMissingFEature() {
    assertThat(
      new PairDescription("name", "single value").feature("missing"),
      empty()
    );
  }
}
