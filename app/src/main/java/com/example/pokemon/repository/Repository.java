package com.example.pokemon.repository;

import androidx.lifecycle.LiveData;

import com.example.pokemon.db.PokemonDao;
import com.example.pokemon.db.RoomDB;
import com.example.pokemon.model.Pokemon;
import com.example.pokemon.model.PokemonResponse;
import com.example.pokemon.retroufit.pokemonInterface;

import java.util.List;

import javax.inject.Inject;

import dagger.Provides;
import io.reactivex.rxjava3.core.Observable;

public class Repository {
    private pokemonInterface pokemonInterface;
    private PokemonDao dao;

    @Inject
    public Repository(com.example.pokemon.retroufit.pokemonInterface pokemonInterface,com.example.pokemon.db.PokemonDao pokemonDao) {
            this.pokemonInterface = pokemonInterface;
            this.dao=pokemonDao;

    }
    public Observable<PokemonResponse> getPokemons()
    {
        return pokemonInterface.getResult();
    }
    public  void insertPokemon(Pokemon pokemon)
    {
        dao.insertPokemon(pokemon);
    }

    public void deletePokemon(String name)
    {
        dao.deletePokemon(name);
    }

    public LiveData<List<Pokemon>> showAll()
    {
        return dao.showAllFavourite();
    }
}
