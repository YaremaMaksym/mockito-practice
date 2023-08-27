package com.pokemonreview.api.service;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.PokemonResponse;
import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.repository.PokemonRepository;
import com.pokemonreview.api.service.impl.PokemonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private PokemonServiceImpl pokemonService;

    @Test
    void createPokemon_ReturnsPokemonDto() {
        // Given
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric").build();
        PokemonDto pokemonDto = PokemonDto.builder()
                .name("Pikachu")
                .type("Electric").build();

        // When
        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);
        PokemonDto savedPokemon = pokemonService.createPokemon(pokemonDto);

        // Then
        assertThat(savedPokemon).usingRecursiveComparison()
                .isEqualTo(pokemonDto);
    }

    @Test
    void getAllPokemon_ReturnsResponseDto() {
        // Given
        Page<Pokemon> pokemons = Mockito.mock(Page.class);

        // When
        when(pokemonRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pokemons);

        PokemonResponse pokemonsReturn = pokemonService.getAllPokemon(1, 20);

        // Then
        assertThat(pokemonsReturn).isNotNull();
    }

    @Test
    void getPokemonById_ReturnsResponseDto() {
        // Given
        int id = 10;
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric").build();

        // When
        when(pokemonRepository.findById(Mockito.any(Integer.class)))
                .thenReturn(Optional.of(pokemon));

        PokemonDto pokemonReturn = pokemonService.getPokemonById(id);

        // Then
        assertThat(pokemonReturn).usingRecursiveComparison()
                .isEqualTo(pokemon);
    }

    @Test
    void updatePokemon_ReturnsResponseDto() {
        // Given
        int id = 10;
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric").build();
        Pokemon pokemonUpdated = Pokemon.builder()
                .name("Raichu")
                .type("Electric").build();
        PokemonDto pokemonDtoUpdated = PokemonDto.builder()
                .name("Raichu")
                .type("Electric").build();

        // When
        when(pokemonRepository.findById(Mockito.any(Integer.class)))
                .thenReturn(Optional.ofNullable(pokemon));

        when(pokemonRepository.save(Mockito.any(Pokemon.class)))
                .thenReturn(pokemonUpdated);

        PokemonDto pokemonReturn = pokemonService.updatePokemon(pokemonDtoUpdated, id);

        // Then
        assertThat(pokemonReturn).usingRecursiveComparison()
                .isEqualTo(pokemonDtoUpdated);
    }
}
