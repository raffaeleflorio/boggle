package io.github.raffaeleflorio.boggle.infrastructure;

import org.junit.jupiter.api.Test;

import static io.github.raffaeleflorio.boggle.infrastructure.Main.main;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MainIntegrationTest {
  @Test
  void testExceptionWithoutPortAndHost() {
    assertThrows(RuntimeException.class, () -> main(new String[]{}));
  }

  @Test
  void testExceptionWithInvalidPort() {
    assertThrows(RuntimeException.class, () -> main(new String[]{"NOPE", "127.0.0.1"}));
  }

  @Test
  void testNoExceptionWithPortAndHost() {
    assertDoesNotThrow(() -> main(new String[]{"0", "127.0.0.1"}));
  }
}
