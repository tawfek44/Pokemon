package com.example.pokemon.viewModel;

import android.annotation.SuppressLint;
import android.database.Observable;
import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pokemon.model.Pokemon;
import com.example.pokemon.model.PokemonResponse;
import com.example.pokemon.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PokemonViewModel extends ViewModel {
    private Repository repository;
    MutableLiveData<ArrayList<Pokemon>>pokemonList=new MutableLiveData<>();
    private String TAG;
    private LiveData<List<Pokemon>>favList=null;
    @ViewModelInject
    public PokemonViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<Pokemon>> getFavList() {
        return favList;
    }

    public MutableLiveData<ArrayList<Pokemon>> getPokemonList() {
        return pokemonList;
    }
    @SuppressLint("CheckResult")
    public void getPokemon()
    {
        repository.getPokemons()
                .subscribeOn(Schedulers.io())
                .map(new Function<PokemonResponse, ArrayList<Pokemon>>() {
                    @Override
                    public ArrayList<Pokemon> apply(PokemonResponse pokemonResponse) throws Throwable {
                        ArrayList<Pokemon>results=pokemonResponse.getPokemonResult();
                        for(Pokemon pokemon:results)
                        {
                            String oldUrl=pokemon.getUrl();
                            String[] splitArray=oldUrl.split("/");
                            pokemon.setUrl("https://pokeres.bastionbot.org/images/pokemon/"+splitArray[splitArray.length-1]+".png");
                        }
                        return results;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(res->pokemonList.setValue(res),error-> Log.e(TAG, "ViewModel: "+error.getMessage()));
    }

    public void insertPokemon(Pokemon pokemon)
    {
        repository.insertPokemon(pokemon);
    }
    public void deletePokemon(String name)
    {
        repository.deletePokemon(name);
    }
    public void showAll()
    {
        favList= repository.showAll();

    }
}
