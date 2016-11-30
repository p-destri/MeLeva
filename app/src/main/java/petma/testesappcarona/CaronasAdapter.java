package petma.testesappcarona;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CaronasAdapter extends ArrayAdapter<Caronas> {

    private final Context context;
    private final ArrayList<Caronas> elementos;

    //Declarando as cores do LV
//    int cor1 = Color.parseColor("#99d9df");
//    int cor2 = Color.parseColor("#ffffff");
//    int cortxt = Color.parseColor("#0057e7");

    public CaronasAdapter(Context context, ArrayList<Caronas> elementos){
        super(context, R.layout.linha, elementos);
        this.context = context;
        this.elementos = elementos;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha, parent, false);

        TextView nomeCarona = (TextView) rowView.findViewById(R.id.nome);
        TextView dia = (TextView) rowView.findViewById(R.id.data);
        TextView local = (TextView) rowView.findViewById(R.id.saida);
        TextView chegada = (TextView) rowView.findViewById(R.id.chegada);
        TextView horario = (TextView) rowView.findViewById(R.id.horario);
        TextView vagas = (TextView) rowView.findViewById(R.id.vagas);

        nomeCarona.setText(elementos.get(position).getNome());
        dia.setText(elementos.get(position).getDia());
        local.setText(elementos.get(position).getLocal());
        chegada.setText(elementos.get(position).getChegada());
        horario.setText(elementos.get(position).getHorario());
        vagas.setText(elementos.get(position).getVagas());


        return rowView;
    }
}

