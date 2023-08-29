package com.pokemonreview.api.service;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.ReviewDto;
import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.models.Review;
import com.pokemonreview.api.repository.PokemonRepository;
import com.pokemonreview.api.repository.ReviewRepository;
import com.pokemonreview.api.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private PokemonRepository pokemonRepository;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Review review;
    private ReviewDto reviewDto;
    private Pokemon pokemon;
    private PokemonDto pokemonDto;

    @BeforeEach
    void setUp() {
        review = Review.builder().title("Title").content("Content").stars(5).build();
        reviewDto = ReviewDto.builder().title("Title").content("Content").stars(5).build();
        pokemon = Pokemon.builder().name("Pikachu").type("Electric").build();
        pokemonDto = PokemonDto.builder().name("Pikachu").type("Electric").build();
    }

    @Test
    void createReview_ReturnsReviewDto() {
        // Given
        given(pokemonRepository.findById(Mockito.anyInt()))
                .willReturn(Optional.of(pokemon));
        given(reviewRepository.save(Mockito.any(Review.class)))
                .willReturn(review);

        // When
        ReviewDto savedReview = reviewService.createReview(pokemon.getId(), reviewDto);

        // Then
        assertThat(savedReview).usingRecursiveComparison()
                .isEqualTo(review);
    }

}