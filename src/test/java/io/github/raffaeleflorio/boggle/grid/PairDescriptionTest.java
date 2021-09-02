package io.github.raffaeleflorio.boggle.grid;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

class PairDescriptionTest {
  @Test
  void testFeature() {
    assertThat(
      new PairDescription("name", "single value").feature("name"),
      contains("single value")
    );
  }
}
