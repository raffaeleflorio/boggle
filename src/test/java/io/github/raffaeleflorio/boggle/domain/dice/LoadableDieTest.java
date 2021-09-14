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

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.oneOf;

class LoadableDieTest {
  @Test
  void testInitialValue() {
    assertThat(
      new LoadableDie<>(
        List.of("EXPECTED", "2", "3")
      ).value(),
      equalTo("EXPECTED")
    );
  }

  @Test
  void testRolledValue() {
    assertThat(
      new LoadableDie<>(
        List.of(1, 2, 3, 42, 5)
      ).rolled().value(),
      oneOf(1, 2, 3, 42, 5)
    );
  }
}
