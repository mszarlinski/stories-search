package com.github.mszarlinski.stories.search.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SearchStoriesResponse {
    private final List<MatchingStory> stories;

    SearchStoriesResponse(@JsonProperty("stories") List<MatchingStory> stories) {
        this.stories = stories;
    }

    public List<MatchingStory> getStories() {
        return stories;
    }
}
