package com.example.pokemon.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pokemon.model.Pokemon;

import java.util.List;

import javax.inject.Inject;

import dagger.Provides;

@Dao
public interface PokemonDao {
    @Insert
    public void insertPokemon(Pokemon pokemon);

    @Query("delete from favourite_pokemon_table where name =:Name")
    public void deletePokemon(String Name);

    @Query("select * from favourite_pokemon_table")
    public LiveData<List<Pokemon>> showAllFavourite();
}
