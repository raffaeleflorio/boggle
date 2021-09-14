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

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class RandomDieTest {
  @Test
  void testDefaultInitialValue() {
    assertThat(
      new RandomDie<>(Function.identity()).value(),
      equalTo(0)
    );
  }

  @Test
  void testCustomInitialValue() {
    assertThat(
      new RandomDie<>(Function.identity(), 123).value(),
      equalTo(123)
    );
  }

  @RepeatedTest(128)
  void testMinAndBoundAfterRolling() {
    var min = 16;
    var bound = 32;
    assertThat(
      new RandomDie<>(Function.identity(), min, bound).rolled().value(),
      allOf(
        lessThan(bound),
        greaterThanOrEqualTo(min)
      )
    );
  }
}
