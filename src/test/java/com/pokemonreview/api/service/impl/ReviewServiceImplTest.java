package com.pokemonreview.api.service.impl;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.RegisterDto;
import com.pokemonreview.api.dto.ReviewDto;
import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.models.Review;
import com.pokemonreview.api.repository.PokemonRepository;
import com.pokemonreview.api.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

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

        // When
        when(pokemonRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(pokemon));
        when(reviewRepository.save(Mockito.any(Review.class)))
                .thenReturn(review);

        ReviewDto savedReview = reviewService.createReview(pokemon.getId(), reviewDto);

        // Then
        assertThat(savedReview).usingRecursiveComparison()
                .isEqualTo(review);
    }

}