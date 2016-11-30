package petma.testesappcarona;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pdt on 24/11/2016.
 */

class HelpList extends ArrayAdapter<String>{
    private final Activity context;
    private final String[] item;
    private final Integer[] imageId;

    HelpList(Activity context,
             String[] item, Integer[] imageId) {
        super(context, R.layout.help_item, item);
        this.context = context;
        this.item = item;
        this.imageId = imageId;

    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.help_item,null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt); // faz referencia a txt view chamada txt
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img); // faz referencia a img view chamda img

        txtTitle.setText(item[position]); // setando o texto
        imageView.setImageResource(imageId[position]); // setando a imagem
        return rowView;
    }
}

