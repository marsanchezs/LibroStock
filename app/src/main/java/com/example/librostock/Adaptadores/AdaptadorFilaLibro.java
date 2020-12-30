package com.example.librostock.Adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.librostock.Objetos.Libro;
import com.example.librostock.R;

import java.util.List;
import java.util.Objects;

public class AdaptadorFilaLibro extends ArrayAdapter<Libro>{

    private LayoutInflater disenoInflado;

    public AdaptadorFilaLibro(Context contexto, List<Libro> objects)
    {
        super(contexto, 0, objects);
        disenoInflado = LayoutInflater.from(contexto);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // holder pattern
        Holder holder;
        if (convertView == null)
        {
            holder = new Holder();

            convertView = disenoInflado.inflate(R.layout.fila_libro, null);
            holder.setImg((ImageView) convertView.findViewById(R.id.imgLibroFL));
            holder.setTxtTitulo((TextView) convertView.findViewById(R.id.txtTituloFL));
            holder.setTxtAutor((TextView) convertView.findViewById(R.id.txtAutorFL));
            holder.setTxtGenero((TextView) convertView.findViewById(R.id.txtGeneroFL));
            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }

        Libro fila = getItem(position);
        assert fila != null;
        holder.getImg().setImageResource(R.drawable.icono_terror);
        holder.getTxtTitulo().setText(fila.getTitulo());
        holder.getTxtAutor().setText(fila.getAutor());
        holder.getTxtGenero().setText(fila.getGenero());
        return convertView;
    }

    static class Holder {
        TextView txtTitulo, txtAutor, txtGenero;
        ImageView img;

        public ImageView getImg() {
            return img;
        }

        public void setImg(ImageView img) {
            this.img = img;
        }

        public TextView getTxtTitulo() {
            return txtTitulo;
        }

        public void setTxtTitulo(TextView txtTitulo) {
            this.txtTitulo = txtTitulo;
        }

        public TextView getTxtAutor() {
            return txtAutor;
        }

        public void setTxtAutor(TextView txtAutor) {
            this.txtAutor = txtAutor;
        }

        public TextView getTxtGenero() {
            return txtGenero;
        }

        public void setTxtGenero(TextView txtGenero) {
            this.txtGenero = txtGenero;
        }
    }

}
