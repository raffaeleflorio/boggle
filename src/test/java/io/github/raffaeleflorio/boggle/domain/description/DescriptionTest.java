package io.github.raffaeleflorio.boggle.domain.description;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;

class DescriptionTest {
  @Nested
  class FakeTest {
    @Test
    void testFeature() {
      assertThat(
        new Description.Fake("name", "feature").feature("name"),
        contains("feature")
      );
    }

    @Test
    void testFeatureAttaching() {
      assertThat(
        new Description.Fake()
          .feature("new", List.of("abc", "def"))
          .feature("new"),
        contains("abc", "def")
      );
    }

    @Test
    void testMissingFeature() {
      assertThat(
        new Description.Fake("name", "feature").feature("any"),
        empty()
      );
    }

    @Test
    void testFeatureAfterAttaching() {
      assertThat(
        new Description.Fake("name", "feature")
          .feature("any", List.of())
          .feature("name"),
        empty()
      );
    }
  }
}
