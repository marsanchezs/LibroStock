package com.example.librostock.Utilidades;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.example.librostock.R;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilidades {
    public Image traerLogo(Context contexto) throws IOException, BadElementException {
        Drawable dibujo = ContextCompat.getDrawable(contexto, R.drawable.logo);
        BitmapDrawable bitmapDibujo = ((BitmapDrawable) dibujo);
        assert bitmapDibujo != null;
        Bitmap bmp = bitmapDibujo.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return Image.getInstance(stream.toByteArray());
    }

    public String traerFecha() {
        String sFecha = "SIN FECHA";
        Date date = new Date();
        SimpleDateFormat fechaF = new SimpleDateFormat("dd-MMM-yyyy", java.util.Locale.getDefault());
        sFecha = fechaF.format(date);
        return sFecha;
    }

    public String traerHora() {
        String sHora = "SIN HORA";
        Date date = new Date();
        SimpleDateFormat horaF = new SimpleDateFormat("H:mm a", java.util.Locale.getDefault());
        sHora =  horaF.format(date);
        return sHora;
    }
}
