package io.github.raffaeleflorio.boggle.infrastructure.vocabulary;

import io.github.raffaeleflorio.boggle.domain.vocabulary.Vocabulary;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;
import io.vertx.mutiny.ext.web.client.predicate.ResponsePredicate;

/**
 * A {@link io.github.raffaeleflorio.boggle.domain.vocabulary.Vocabulary} backed by Treccani (https://www.treccani.it)
 *
 * @author Raffaele Florio (raffaeleflorio@protonmail.com)
 * @since 1.0.0
 */
public final class TreccaniVocabulary implements Vocabulary {
  /**
   * Builds a vocabulary
   *
   * @param webClient The webclient
   * @since 1.0.0
   */
  public TreccaniVocabulary(final WebClient webClient) {
    this(webClient, "https://www.treccani.it/vocabolario/");
  }

  /**
   * Builds a vocabulary
   *
   * @param webClient The webclient
   * @param url       The base url
   * @since 1.0.0
   */
  TreccaniVocabulary(final WebClient webClient, final String url) {
    this.webClient = webClient;
    this.url = url;
  }

  @Override
  public Uni<Boolean> contains(final CharSequence word) {
    return webClient
      .getAbs(url.concat(word.toString()).concat("/"))
      .expect(ResponsePredicate.SC_OK)
      .expect(ResponsePredicate.contentType("text/html"))
      .send()
      .onItem().transform(HttpResponse::bodyAsString)
      .onItem().transform(response -> !response.contains("La tua ricerca non ha prodotto risultati in nessun documento"));
  }

  private final WebClient webClient;
  private final String url;
}
