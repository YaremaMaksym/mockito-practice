package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void save_ReturnSavedReview() {
        // Given
        Review review = Review.builder()
                .title("Title")
                .content("Content")
                .stars(5)
                .build();

        // When
        Review savedReview = reviewRepository.save(review);

        // Then
        assertThat(savedReview).usingRecursiveComparison()
                .isEqualTo(review);
    }

    @Test
    void findAll_ReturnMoreThanOneReview() {
        // Given
        Review review1 = Review.builder()
                .title("Title")
                .content("Content")
                .stars(5)
                .build();

        Review review2 = Review.builder()
                .title("Title")
                .content("Content")
                .stars(5)
                .build();

        reviewRepository.save(review1);
        reviewRepository.save(review2);

        // When
        List<Review> list = reviewRepository.findAll();

        // Then
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    void findById_FindPokemonById() {
        // Given
        Review review = Review.builder()
                .title("Title")
                .content("Content")
                .stars(5)
                .build();

        reviewRepository.save(review);

        // When
        Review reviewReturn = reviewRepository.findById(review.getId()).get();

        // Then
        assertThat(reviewReturn).usingRecursiveComparison()
                .isEqualTo(review);
    }

    @Test
    void update_ReturnUpdatedReview() {
        // Given
        Review review = Review.builder()
                .title("Title")
                .content("Content")
                .stars(5)
                .build();

        reviewRepository.save(review);

        Review reviewUpdate = reviewRepository.findById(review.getId()).get();
        reviewUpdate.setTitle("New Title");
        reviewUpdate.setContent("New Content");

        // When
        Review reviewReturn = reviewRepository.save(reviewUpdate);

        // Then
        assertThat(reviewReturn).usingRecursiveComparison()
                .isEqualTo(reviewUpdate);
    }

    @Test
    void deleteById_ReturnReviewIsEmpty() {
        // Given
        Review review = Review.builder()
                .title("Title")
                .content("Content")
                .stars(5)
                .build();

        reviewRepository.save(review);

        // When
        reviewRepository.deleteById(review.getId());
        Optional<Review> reviewReturn = reviewRepository.findById(review.getId());

        // Then
        assertThat(reviewReturn).isEmpty();
    }
}