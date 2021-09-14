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
package io.github.raffaeleflorio.boggle.infrastructure.sheet;

import io.github.raffaeleflorio.boggle.domain.description.Description;
import io.github.raffaeleflorio.boggle.domain.dice.Dice;
import io.github.raffaeleflorio.boggle.domain.sheet.Sheet;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * In memory {@link Sheet} implementation
 *
 * @param <T> The word type
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
final class InMemorySheet<T> implements Sheet<T> {
  /**
   * Builds an in memory sheet
   *
   * @param description The description
   * @since 1.0.0
   */
  InMemorySheet(final Description description) {
    this(
      description,
      Collections.synchronizedSet(new HashSet<>())
    );
  }

  /**
   * Builds an in memory sheet
   *
   * @param description The description
   * @param words       The word set
   * @since 1.0.0
   */
  InMemorySheet(final Description description, final Set<Dice<T>> words) {
    this.description = description;
    this.words = words;
  }

  @Override
  public UUID id() {
    return UUID.fromString(description.feature("id").get(0).toString());
  }

  @Override
  public Multi<Dice<T>> words() {
    return Multi.createFrom().items(words::stream);
  }

  @Override
  public Uni<Void> word(final Dice<T> word) {
    words.add(word);
    return Uni.createFrom().voidItem();
  }

  @Override
  public Description description() {
    return description;
  }

  private final Description description;
  private final Set<Dice<T>> words;
}
