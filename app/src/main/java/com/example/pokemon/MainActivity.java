package com.example.pokemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchUIUtil;
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

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private PokemonViewModel viewModel;
    private RecyclerView pokemonRecyclerView;
    private PokemonAdapter adapter;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pokemonRecyclerView=findViewById(R.id.rec);
        button=findViewById(R.id.goto_fav);

        adapter=new PokemonAdapter(this);
        pokemonRecyclerView.setAdapter(adapter);
        swipeRecyclerView();


        viewModel=new ViewModelProvider(this).get(PokemonViewModel.class);
        viewModel.getPokemon();
        viewModel.getPokemonList().observe(this, new Observer<ArrayList<Pokemon>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(ArrayList<Pokemon> pokemons) {
                adapter.setList(pokemons);
                adapter.notifyDataSetChanged();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,PokemonFavActivity.class));
            }
        });


    }
    private void swipeRecyclerView()
    {
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
                viewModel.insertPokemon(pokemon);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Done!!", Toast.LENGTH_SHORT).show();
            }
        };
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(pokemonRecyclerView);
    }
}