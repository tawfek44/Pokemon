package com.example.pokemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pokemon.adapters.PokemonAdapter;
import com.example.pokemon.model.Pokemon;
import com.example.pokemon.viewModel.PokemonViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PokemonFavActivity extends AppCompatActivity {

    Button button;
    private PokemonViewModel viewModel;
    private RecyclerView pokemonRecyclerView;
    private PokemonAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_fav);
        button=findViewById(R.id.goto_main);

        pokemonRecyclerView=findViewById(R.id.rec2);
        adapter=new PokemonAdapter(this);
        pokemonRecyclerView.setAdapter(adapter);
        swipeRecyclerView();

        viewModel=new ViewModelProvider(this).get(PokemonViewModel.class);
        viewModel.showAll();
        viewModel.getFavList().observe(this, new Observer<List<Pokemon>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                adapter.setList((ArrayList)pokemons);
                adapter.notifyDataSetChanged();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PokemonFavActivity.this,MainActivity.class));
            }
        });

    }

    private void swipeRecyclerView() {
        ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pokemonItemId=viewHolder.getAdapterPosition();
                Pokemon pokemon=adapter.getPokemon(pokemonItemId);
                viewModel.deletePokemon(pokemon.getName());
                adapter.notifyDataSetChanged();
                Toast.makeText(PokemonFavActivity.this, "Done!!", Toast.LENGTH_SHORT).show();
            }
        };
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(pokemonRecyclerView);
    }
}