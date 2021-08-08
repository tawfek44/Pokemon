package com.example.pokemon.di;

import android.app.Application;

import androidx.room.Room;

import com.example.pokemon.db.PokemonDao;
import com.example.pokemon.db.RoomDB;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
@Module
@InstallIn(ApplicationComponent.class)
public class RoomDBModule {

    @Provides
    @Singleton
    public static RoomDB getDB(Application application)
    {
        return Room.databaseBuilder(application,RoomDB.class,"favourite_pokemon_table")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }
    @Provides
    @Singleton
    public static PokemonDao provideDao(RoomDB db)
    {
        return db.pokemonDao();
    }
}
