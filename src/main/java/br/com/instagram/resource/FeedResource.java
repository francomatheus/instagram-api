package br.com.instagram.resource;

import br.com.instagram.model.entity.PostDocument;
import br.com.instagram.service.FeedService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/v0/feed")
public class FeedResource {

    @Autowired
    private FeedService feedService;

    @GetMapping("/{userId}")
    @Operation(summary = "Post of user feed ", tags = {"feed"})
    private Flux<PostDocument> getFeedForUser(@PathVariable Long userId){
        return feedService.getFeedForUser(userId);
    }


}
