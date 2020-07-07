package com.example.implementacionws.ui.listado;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.implementacionws.R;
import com.example.implementacionws.entity.User;

import java.util.List;

public class ListadoAdapter extends RecyclerView.Adapter<ListadoAdapter.ViewHolder> {

    private List<User> usuarios;
    private Context context;

    public ListadoAdapter(List<User> usuarios, Context context) {
        this.usuarios = usuarios;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listado_users,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mID.setText("Documento: " + usuarios.get(position).getDocumento().toString());
        holder.mNombre.setText("Nombre: " + usuarios.get(position).getNombreUser());
        holder.mProfesion.setText("Profesion: " + usuarios.get(position).getProfesion());
    }

    @Override
    public int getItemCount() {

        if(usuarios!=null){
            return usuarios.size();
        }else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mID, mNombre, mProfesion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mID = itemView.findViewById(R.id.textViewRecyclerUserID);
            mNombre = itemView.findViewById(R.id.textViewRecyclerUserNombre);
            mProfesion = itemView.findViewById(R.id.textViewRecyclerUserProfesion);
        }
    }
}
