package br.com.instagram.service;

import br.com.instagram.model.entity.PostDocument;
import reactor.core.publisher.Flux;

public interface FeedService {

    Flux<PostDocument> getFeedForUser(Long userId);
}
