package com.github.mszarlinski.stories.search.application;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "stories")
public class Story {

    private final String id;
    private final String title;
    private final String content;

    public Story(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public String getId() {
        return id;
    }
}
