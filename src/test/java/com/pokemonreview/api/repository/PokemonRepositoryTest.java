package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Pokemon;
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
class PokemonRepositoryTest {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    void itShouldSavePokemon() {
        // Given
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric").build();

        // When
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        // Then
        assertThat(savedPokemon).usingRecursiveComparison()
                .isEqualTo(pokemon);
    }

    @Test
    void itShouldReturnMoreThanOnePokemon() {
        // Given
        Pokemon pokemon1 = Pokemon.builder()
                .name("Pikachu1")
                .type("Electric").build();

        Pokemon pokemon2 = Pokemon.builder()
                .name("Pikachu2")
                .type("Electric").build();

        pokemonRepository.save(pokemon1);
        pokemonRepository.save(pokemon2);

        // When
        List<Pokemon> list = pokemonRepository.findAll();

        // Then
        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    void itShouldFindPokemonById() {
        // Given
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric").build();

        pokemonRepository.save(pokemon);

        // When
        Pokemon pokemonReturn = pokemonRepository.findById(pokemon.getId()).get();

        // Then
        assertThat(pokemonReturn).usingRecursiveComparison()
                .isEqualTo(pokemon);
    }
}