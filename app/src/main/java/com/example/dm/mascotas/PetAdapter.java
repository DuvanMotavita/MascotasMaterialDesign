package com.example.dm.mascotas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Pet> petList;
    private OnPetClickListener onPetClickListener;

    public interface  OnPetClickListener {
        void onPetClick(Pet pet);
    }

    public PetAdapter(Context context, ArrayList<Pet> petList){
        this.context = context;
        this.petList = petList;
    }

    public void  setOnPetClickListener(OnPetClickListener onPetClickListener){
        this.onPetClickListener = onPetClickListener;
    }


    @NonNull
    @Override
    public PetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pet_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetAdapter.ViewHolder holder, int position) {
        final Pet pet = petList.get(position);
        holder.petNameTextView.setText(pet.getName());
        holder.petOwnerTextView.setText(pet.getOwnerName());
        holder.petImageView.setImageDrawable(ContextCompat.getDrawable(context,pet.getImageId()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPetClickListener.onPetClick(pet);
            }
        });
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

     class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView petNameTextView;
        public TextView petOwnerTextView;
        public ImageView petImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            petNameTextView = (TextView) itemView.findViewById(R.id.pet_list_item_name);
            petOwnerTextView = (TextView) itemView.findViewById(R.id.pet_list_item_owner_name);
            petImageView = (ImageView) itemView.findViewById(R.id.pet_list_item_image);
        }
    }
}
