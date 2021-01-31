package com.github.mszarlinski.stories.search;

import com.github.mszarlinski.stories.search.api.MatchingStory;
import com.github.mszarlinski.stories.search.api.SearchStoriesResponse;
import com.github.mszarlinski.stories.search.application.StoriesIndex;
import com.github.mszarlinski.stories.search.application.Story;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;
import java.util.Map;

import static com.github.mszarlinski.stories.search.TestStory.story;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Testcontainers
@SpringBootTest(webEnvironment = RANDOM_PORT)
class StoriesSearchAcceptanceTests {

    @Container
    static ElasticsearchContainer elasticContainer = new ElasticsearchContainer(DockerImageName
            .parse("docker.elastic.co/elasticsearch/elasticsearch-oss")
            .withTag("7.10.2"));

    @DynamicPropertySource
    static void elasticProperties(DynamicPropertyRegistry props) {
        props.add("spring.elasticsearch.rest.uris", elasticContainer::getHttpHostAddress);
    }

    @Autowired
    TestRestTemplate client;

    @Autowired
    StoriesIndex storiesIndex;

    @Test
    void shouldReturnMatchingStories() {
        // given
        thereAreStories(
                story().withId("1").withTitle("Java").withContent("Programming in Java").build(),
                story().withId("2").withTitle("Scala").withContent("Hacking in Scala").build(),
                story().withId("3").withTitle("Programming in Kotlin").withContent("Hacking in Kotlin").build()
        );

        // when
        SearchStoriesResponse result = searchForStories("programming");

        // then
        assertThat(result.getStories())
                .extracting(MatchingStory::getStoryId)
                .contains("1", "3");

    }

    private SearchStoriesResponse searchForStories(String query) {
        return client.getForObject("/search?query={query}", SearchStoriesResponse.class, Map.of("query", query));
    }

    //TODO: write to Kafka topic
    private void thereAreStories(TestStory... stories) {
        Arrays.stream(stories).forEach(s ->
                storiesIndex.save(new Story(s.getId(), s.getTitle(), s.getContent()))
        );
    }

}
