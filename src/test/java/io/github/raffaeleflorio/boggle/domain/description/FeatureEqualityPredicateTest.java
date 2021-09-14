package io.github.raffaeleflorio.boggle.domain.description;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class FeatureEqualityPredicateTest {
  @Test
  void testWithExpectedFeatureValues() {
    assertThat(
      new FeatureEqualityPredicate(
        Map.of("name", List.of("expected value"))
      ).test(
        new Description.Fake("name", "expected value")
      ),
      equalTo(true)
    );
  }

  @Test
  void testWithUnexpectedFeatureValues() {
    assertThat(
      new FeatureEqualityPredicate(
        Map.of("name", List.of("one", "two"))
      ).test(
        new Description.Fake("name", "unexpected values")
      ),
      equalTo(false)
    );
  }

  @Test
  void testWithExpectedFeatureValuesAndAdditionalFeatures() {
    assertThat(
      new FeatureEqualityPredicate(
        Map.of("name", List.of("one", "two"))
      ).test(
        new Description.Fake(
          Map.of(
            "second feature", List.of("any", "value"),
            "name", List.of("one", "two"),
            "third feature", List.of()
          )
        )
      ),
      equalTo(true)
    );
  }
}
