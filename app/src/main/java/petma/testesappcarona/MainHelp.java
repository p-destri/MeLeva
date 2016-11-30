package petma.testesappcarona;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainHelp extends AppCompatActivity {
    ListView list_main;

    //Itens do LV
    ListView list;
    String[] item = {"   Como começar?", "   Como adcionar uma carona?" ,"    Como remover uma carona?",
            "   Como faço para pegar uma carona com alguém?","   Minhas informações estão seguras?","   Como editar meu perfil?",
            "   Tive um problema com uma carona, o que fazer?","   Como trocar minha senha?","   Preciso dividir o valor da carona?",
            "Recomendações aos usuários","Entre em contato"
    } ;
    Integer[] imageId = {
            R.drawable.information,
            R.drawable.plus,
            R.drawable.removecloud,
            R.drawable.network,
            R.drawable.lock,
            R.drawable.user,
            R.drawable.siren,
            R.drawable.lock,
            R.drawable.piggy,
            R.drawable.recomenda,
            R.drawable.mail

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dúvidas Frequentes");

        HelpList adapter = new HelpList(MainHelp.this,item,imageId);
        list_main = (ListView) findViewById(R.id.list_main);
        list_main.setAdapter(adapter);
        list_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) { Toast.makeText(getApplicationContext(), "You Clicked at " +item[+ position], Toast.LENGTH_SHORT).show();}});



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        if (item.getItemId() == 16908332){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
