package petma.testesappcarona;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kosalgeek.genasync12.MainActivity;

public class Inst extends AppCompatActivity {

    ListView list;
    String[] item = {"  Como faço login?", "   O que é o aplicativo?" ,"Não sou aluno da UFSC, posso utilizar o aplicativo?",
            "   Minhas informações estão protegidas?","  Esqueci minha senha"} ;
    Integer[] imageId = {
            R.drawable.login,
            R.drawable.information,
            R.drawable.books,
            R.drawable.lock,
            R.drawable.lock
    };

    //TODO: FAZER ABRIR UMA PÁGINA (Frag talvez) que mostre o negócio completo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inst);

        HelpList adapter = new HelpList(Inst.this, item, imageId);

        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(Inst.this, "You Clicked at " +item[+ position], Toast.LENGTH_SHORT).show();

            }
        });




    }
}
