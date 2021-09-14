/*
   Copyright 2021 Raffaele Florio

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
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
