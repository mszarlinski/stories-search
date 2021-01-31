package com.github.mszarlinski.stories.search.api;

import com.github.mszarlinski.stories.search.application.StoriesIndex;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.toList;

@RestController
class SearchStoriesController {

    private final StoriesIndex storiesIndex;

    SearchStoriesController(StoriesIndex storiesIndex) {
        this.storiesIndex = storiesIndex;
    }

    @GetMapping("/search")
    SearchStoriesResponse search(@RequestParam("query") String query) {
        var stories = storiesIndex.findFirst10ByTitleOrContent(query, query);
        return new SearchStoriesResponse(stories.stream().map(s -> new MatchingStory(s.getId())).collect(toList()));
    }
}
