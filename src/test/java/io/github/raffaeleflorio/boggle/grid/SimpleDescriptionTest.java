package io.github.raffaeleflorio.boggle.grid;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

class SimpleDescriptionTest {
  @Test
  void testFeature() {
    assertThat(
      new SimpleDescription()
        .feature("a feature", List.of("a", "feature", "value"))
        .feature("a feature"),
      contains("a", "feature", "value")
    );
  }
}
