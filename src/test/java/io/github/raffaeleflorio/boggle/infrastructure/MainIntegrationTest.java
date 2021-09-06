package io.github.raffaeleflorio.boggle.infrastructure;

import org.junit.jupiter.api.Test;

import static io.github.raffaeleflorio.boggle.infrastructure.Main.main;

class MainIntegrationTest {
  @Test
  void testNoException() {
    main(new String[]{});
  }
}
