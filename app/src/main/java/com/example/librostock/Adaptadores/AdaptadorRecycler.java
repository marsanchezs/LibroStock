package com.example.librostock.Adaptadores;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librostock.Objetos.Libro;
import com.example.librostock.R;

import java.util.ArrayList;
import java.util.LinkedList;

public class AdaptadorRecycler extends RecyclerView.Adapter<AdaptadorRecycler.LibroViewHolder>{

    private ArrayList<Libro> listaLibro;
    final private clickLibro onItemClick;

    public AdaptadorRecycler(ArrayList<Libro> lista, clickLibro listener){
        listaLibro = lista;
        onItemClick = listener;
    }

    public interface clickLibro{
        void clickListaLibro(int clickedLibro);
        void clickLargoListaLibro(int clickedLibro);
    }

    @NonNull
    @Override
    public LibroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context contexto = parent.getContext();
        int idLayout = R.layout.fila_libro;
        LayoutInflater inflador = LayoutInflater.from(contexto);
        boolean adjuntar = false;
        View vista = inflador.inflate(idLayout, parent, adjuntar);
        LibroViewHolder vistaLibro = new LibroViewHolder(vista);

        return vistaLibro;
    }

    @Override
    public void onBindViewHolder(@NonNull LibroViewHolder holder, int position) {
        //holder.enlazar(position);
        String titulo = listaLibro.get(position).getTitulo();
        String autor = listaLibro.get(position).getAutor();
        String genero = listaLibro.get(position).getGenero();
        holder.enlazar(titulo, autor, genero);
    }

    @Override
    public int getItemCount() {
        return listaLibro.size();
    }

    class LibroViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        TextView txtTitulo, txtAutor, txtGenero;
        CardView cvLibro;

        public LibroViewHolder(@NonNull View vistaLibro) {
            super(vistaLibro);

            cvLibro = vistaLibro.findViewById(R.id.cvLibroFL);
            txtTitulo = vistaLibro.findViewById(R.id.txtTituloFL);
            txtAutor = vistaLibro.findViewById(R.id.txtAutorFL);
            txtGenero = vistaLibro.findViewById(R.id.txtGeneroFL);
            vistaLibro.setOnClickListener(this);
            vistaLibro.setOnLongClickListener(this);
        }

        void enlazar(String titulo, String autor, String genero){
            txtTitulo.setText(titulo);
            txtAutor.setText(autor);
            txtGenero.setText(genero);

        }

        @Override
        public void onClick(View view) {
            int clickedLibro = getAdapterPosition();
            onItemClick.clickListaLibro(clickedLibro);
        }

        @Override
        public boolean onLongClick(View view) {
            int clickedLibro = getAdapterPosition();
            if (clickedLibro != RecyclerView.NO_POSITION) {
                if (onItemClick != null) {
                    onItemClick.clickLargoListaLibro(clickedLibro);
                }
            }
            return false;

        }
    }

    public void filtrar(ArrayList<Libro> filtroLibro) {
        this.listaLibro = filtroLibro;
        notifyDataSetChanged();
    }

}
