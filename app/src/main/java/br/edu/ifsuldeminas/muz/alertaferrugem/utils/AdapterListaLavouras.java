package br.edu.ifsuldeminas.muz.alertaferrugem.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsuldeminas.muz.alertaferrugem.model.Lavoura;
import br.edu.ifsuldeminas.muz.alertaferrugem.R;

/**
 * Created by samuel on 13/07/2017.
 */

public class AdapterListaLavouras extends BaseAdapter
{

    private final List<Lavoura> lavouras;
    private final Activity act;

    public AdapterListaLavouras(List<Lavoura> lavouras, Activity act)
    {
        this.lavouras = lavouras;
        this.act = act;
    }

    @Override
    public int getCount()
    {
        return lavouras.size();
    }

    @Override
    public Object getItem(int position) {
        return lavouras.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.lista_elements, parent, false);
        Lavoura lavoura = lavouras.get(i);

        TextView nome = (TextView) view.findViewById(R.id.lista_curso_personalizada_nome);
        ImageView imagem = (ImageView) view.findViewById(R.id.lista_curso_personalizada_imagem);


        nome.setText(lavoura.getNome());

        if(lavoura.getSta().equals("Vermelho"))
            imagem.setImageResource(R.drawable.vetorvermelho);
        else if(lavoura.getSta().equals("Verde"))
            imagem.setImageResource(R.drawable.vetorverde);
        else
            imagem.setImageResource(R.drawable.vetoramarelo);
        return view;
    }
}
