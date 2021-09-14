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
