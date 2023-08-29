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
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

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
        given(pokemonRepository.save(Mockito.any(Pokemon.class))).willReturn(pokemon);

        // When
        PokemonDto savedPokemon = pokemonService.createPokemon(pokemonDto);

        // Then
        assertThat(savedPokemon).usingRecursiveComparison()
                .isEqualTo(pokemonDto);
    }

    @Test
    void getAllPokemon_ReturnsResponseDto() {
        // Given
        Page<Pokemon> pokemons = Mockito.mock(Page.class);
        given(pokemonRepository.findAll(Mockito.any(Pageable.class))).willReturn(pokemons);

        // When
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
        given(pokemonRepository.findById(Mockito.any(Integer.class)))
                .willReturn(Optional.of(pokemon));

        // When
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

        given(pokemonRepository.findById(Mockito.any(Integer.class)))
                .willReturn(Optional.ofNullable(pokemon));
        given(pokemonRepository.save(Mockito.any(Pokemon.class)))
                .willReturn(pokemonUpdated);

        // When
        PokemonDto pokemonReturn = pokemonService.updatePokemon(pokemonDtoUpdated, id);

        // Then
        assertThat(pokemonReturn).usingRecursiveComparison()
                .isEqualTo(pokemonDtoUpdated);
    }

    @Test
    void deletePokemonById_ReturnsResponseDto() {
        // Given
        int id = 10;
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric").build();

        given(pokemonRepository.findById(Mockito.any(Integer.class)))
                .willReturn(Optional.of(pokemon));

        // When

        // Then
        assertAll(() -> pokemonService.deletePokemonId(id));
    }
}
