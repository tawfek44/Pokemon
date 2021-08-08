package com.example.pokemon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokemon.R;
import com.example.pokemon.model.Pokemon;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.pokemonViewHolder> {

    ArrayList<Pokemon>pokemons=new ArrayList<>();
    private Context context;

    public PokemonAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public pokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new pokemonViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull pokemonViewHolder holder, int position) {
        holder.pokemonTextView.setText(pokemons.get(position).getName());
        Glide.with(context).load(pokemons.get(position).getUrl()).into(holder.pokemonImageView);
    }
     public Pokemon getPokemon(int id)
     {
         return pokemons.get(id);
     }
    public void setList(ArrayList<Pokemon>pokemons)
    {
        this.pokemons=pokemons;
    }
    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public class pokemonViewHolder extends RecyclerView.ViewHolder {
        private ImageView pokemonImageView;
        private TextView pokemonTextView;
        public pokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonImageView=itemView.findViewById(R.id.pokemon_imageView);
            pokemonTextView=itemView.findViewById(R.id.pokemon_TextView);
        }
    }
}
