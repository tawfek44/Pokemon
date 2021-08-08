package com.example.pokemon.retroufit;

import com.example.pokemon.model.PokemonResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface pokemonInterface {
    @GET("pokemon")
    Observable<PokemonResponse> getResult();
}
