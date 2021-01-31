package com.github.mszarlinski.stories.search.application;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface StoriesIndex extends ElasticsearchRepository<Story, String> {
    List<Story> findFirst10ByTitleOrContent(String query1, String query2);
}
