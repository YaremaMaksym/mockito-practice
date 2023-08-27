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
    void save_ReturnSavedPokemon() {
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
    void findAll_ReturnMoreThanOnePokemon() {
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
    void findById_FindPokemonById() {
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

    @Test
    void update_ReturnUpdatedPokemon() {
        // Given
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric").build();

        pokemonRepository.save(pokemon);

        Pokemon pokemonUpdate = pokemonRepository.findById(pokemon.getId()).get();
        pokemonUpdate.setType("Electric");
        pokemonUpdate.setName("Raichu");

        // When
        Pokemon pokemonReturn = pokemonRepository.save(pokemonUpdate);

        // Then
        assertThat(pokemonReturn).usingRecursiveComparison()
                .isEqualTo(pokemonUpdate);
    }

    @Test
    void deleteById_ReturnPokemonIsEmpty() {
        // Given
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric").build();

        pokemonRepository.save(pokemon);

        // When
        pokemonRepository.deleteById(pokemon.getId());
        Optional<Pokemon> pokemonReturn = pokemonRepository.findById(pokemon.getId());

        // Then
        assertThat(pokemonReturn).isEmpty();
    }
}