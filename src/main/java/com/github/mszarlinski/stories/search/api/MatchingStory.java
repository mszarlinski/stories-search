package com.github.mszarlinski.stories.search.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MatchingStory {
    private final String storyId;

    public MatchingStory(@JsonProperty("storyId") String storyId) {
        this.storyId = storyId;
    }

    public String getStoryId() {
        return storyId;
    }
}
