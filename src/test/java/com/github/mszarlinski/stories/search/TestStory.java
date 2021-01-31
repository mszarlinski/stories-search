package com.github.mszarlinski.stories.search;

import java.util.UUID;

public class TestStory {

    private final String id;
    private final String title;
    private final String content;

    TestStory(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static TestStoryBuilder story() {
        return new TestStoryBuilder();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public static class TestStoryBuilder {
        private String id = UUID.randomUUID().toString();
        private String title = "Some title";
        private String content = "Some content";

        TestStoryBuilder() {
        }

        public TestStoryBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public TestStoryBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public TestStoryBuilder withContent(String content) {
            this.content = content;
            return this;
        }

        public TestStory build() {
            return new TestStory(id, title, content);
        }
    }
}
