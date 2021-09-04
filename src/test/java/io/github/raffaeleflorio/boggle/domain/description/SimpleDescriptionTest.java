package io.github.raffaeleflorio.boggle.domain.description;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

class SimpleDescriptionTest {
  @Test
  void testFeatureOverwrite() {
    assertThat(
      new SimpleDescription()
        .feature("a", List.of("OLD"))
        .feature("a", List.of("0", "1", "42"))
        .feature("a"),
      contains("0", "1", "42")
    );
  }
}
