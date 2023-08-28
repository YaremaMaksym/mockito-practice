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
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private PokemonRepository pokemonRepository;
    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Review review;
    private RegisterDto registerDto;
    private Pokemon pokemon;
    private PokemonDto pokemonDto;

    @BeforeEach
    void setUp() {
        Review review = Review.builder().title("Title").content("Content").stars(5).build();
        ReviewDto reviewDto = ReviewDto.builder().title("Title").content("Content").stars(5).build();
        Pokemon pokemon = Pokemon.builder().name("Pikachu").type("Electric").build();
        PokemonDto pokemonDto = PokemonDto.builder().name("Pikachu").type("Electric").build();
    }

}