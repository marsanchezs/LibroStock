package com.example.librostock.Adaptadores;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librostock.BBDD.LibroStockDelegar;
import com.example.librostock.DetalleAutor;
import com.example.librostock.Objetos.Autor;
import com.example.librostock.R;

import java.util.ArrayList;

public class AdaptadorRecyclerAutores extends RecyclerView.Adapter<AdaptadorRecyclerAutores.AutorViewHolder>{

    private Context miContexto;
    private ArrayList<Autor> listaAutores;
    private RecyclerView rvAutor;
    final private AdaptadorRecyclerAutores.clickAutor onItemClick;
    private LibroStockDelegar delegar = new LibroStockDelegar();

    public AdaptadorRecyclerAutores(Context contexto, ArrayList<Autor> lista,
                                    AdaptadorRecyclerAutores.clickAutor listener,
                                    RecyclerView rvAutores){
        miContexto = contexto;
        listaAutores = lista;
        onItemClick = listener;
        rvAutor = rvAutores;
    }

    public interface clickAutor{
        void clickListaAutor(int clickedAutor);
    }

    @NonNull
    @Override
    public AdaptadorRecyclerAutores.AutorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context contexto = parent.getContext();
        int idLayout = R.layout.fila_autor;
        LayoutInflater inflador = LayoutInflater.from(contexto);
        boolean adjuntar = false;
        View vista = inflador.inflate(idLayout, parent, adjuntar);
        AdaptadorRecyclerAutores.AutorViewHolder vistaAutor = new AdaptadorRecyclerAutores.AutorViewHolder(vista);

        return vistaAutor;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorRecyclerAutores.AutorViewHolder holder, int position) {
        //holder.enlazar(position);
        String autor = listaAutores.get(position).getNombre();
        String pais = listaAutores.get(position).getPais();
        String sexo = listaAutores.get(position).getSexo();
        holder.enlazar(autor, pais, sexo, position);
    }

    @Override
    public int getItemCount() {
        return listaAutores.size();
    }

    class AutorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imgAutor;
        TextView txtAutor, txtPais, txtSexo;
        ImageButton btnEditar, btnEliminar;
        CardView cvAutor;

        public AutorViewHolder(@NonNull View vistaAutor) {
            super(vistaAutor);

            cvAutor = vistaAutor.findViewById(R.id.cvAutorFA);
            imgAutor = vistaAutor.findViewById(R.id.imgAutorFA);
            txtAutor = vistaAutor.findViewById(R.id.txtAutorFA);
            txtPais = vistaAutor.findViewById(R.id.txtPaisFA);
            txtSexo = vistaAutor.findViewById(R.id.txtSexoFA);
            btnEditar = vistaAutor.findViewById(R.id.btnEditarFA);
            btnEliminar = vistaAutor.findViewById(R.id.btnEliminarFA);
            vistaAutor.setOnClickListener(this);

            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(miContexto, "EDITAR", Toast.LENGTH_LONG).show();
                }
            });


        }

        void enlazar(final String autor, final String pais, final String sexo, final int posicion){

            if(sexo.equals("Femenino")){
                imgAutor.setImageResource(R.drawable.icono_autorm);
            }else{
                imgAutor.setImageResource(R.drawable.icono_autorh);
            }

            switch(autor){
                case "varios autores":
                    imgAutor.setImageResource(R.drawable.icono_autorg);
                    break;
                case "anónimo":
                    imgAutor.setImageResource(R.drawable.icono_anonimo);
                    break;
            }

            txtAutor.setText(autor);
            txtPais.setText(pais);
            txtSexo.setText(sexo);
            final String mensaje = "AUTOR: "+autor+" PAÍS: "+pais+" SEXO: "+sexo+" POSICIÓN: "+posicion;
            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(miContexto, mensaje, Toast.LENGTH_LONG).show();
                    eliminarAutor(autor, pais, sexo, posicion);
                }
            });
        }

        @Override
        public void onClick(View view) {
            int clickedAutor = getAdapterPosition();
            onItemClick.clickListaAutor(clickedAutor);
        }

    }

    private void eliminarAutor(final String nombre, final String pais, final String sexo, final int posicion){
        final AlertDialog dialogoEliminar;
        final AlertDialog.Builder constructorDialogo = new AlertDialog.Builder(miContexto);
        LayoutInflater inflador = LayoutInflater.from(miContexto);
        //LayoutInflater inflador = getLayoutInflater();
        View vistaDialogo = inflador.inflate(R.layout.dialogo_eliminar,null);
        constructorDialogo.setView(vistaDialogo);
        dialogoEliminar = constructorDialogo.create();
        TextView txvMensaje = (TextView) vistaDialogo.findViewById(R.id.txtMensajeDE);
        ImageButton btnConfirmar = (ImageButton) vistaDialogo.findViewById(R.id.btnConfirmarDE);
        ImageButton btnCancelar = (ImageButton) vistaDialogo.findViewById(R.id.btnCancelarDE);
        final String mensaje = "¿ESTÁ SEGURO DE ELIMINAR A "+nombre.toUpperCase()+"?";
        txvMensaje.setText(mensaje);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                //String mensaje2 = "AUTOR: "+nombre+" PAíS: "+pais+" SEXO: "+sexo;
                //Toast.makeText(contexto, mensaje2, Toast.LENGTH_SHORT).show();
                String msg = "";
                String respuestaBorrarAutor = delegar.borrarAutor(miContexto, nombre, pais, sexo);
                if(respuestaBorrarAutor.equals("OK")){
                    msg = nombre.toUpperCase()+" HA SIDO ELIMINADO";
                    Toast.makeText(miContexto, msg, Toast.LENGTH_SHORT).show();
                    cargarRecyclerView();
                    //eliminar(posicion);
                    dialogoEliminar.dismiss();
                }else{
                    msg = "NO SE PUDO ELIMINAR A "+nombre.toUpperCase();
                    Toast.makeText(miContexto, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                dialogoEliminar.dismiss();
            }
        });
        dialogoEliminar.show();
    }

    public void cargarRecyclerView(){
        listaAutores = delegar.traerAutores2(miContexto);
        AdaptadorRecyclerAutores adaptador = new AdaptadorRecyclerAutores(miContexto, listaAutores, onItemClick, rvAutor);
        rvAutor.setAdapter(adaptador);
    }

    private void eliminar(int posicion){
        listaAutores.remove(posicion);
        AdaptadorRecyclerAutores.this.notifyItemRemoved(posicion);
        listaAutores = delegar.traerAutores2(miContexto);
    }

}
