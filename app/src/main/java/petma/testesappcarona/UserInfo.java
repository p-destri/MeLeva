package petma.testesappcarona;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
//TODO: Melhorar
//TODO: Esse
//TODO: Layout
public class UserInfo extends AppCompatActivity {

    SessionManager session;
    TextView user_name, user_age, user_rating;
    ImageView user_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String>user = session.getUserDetails();
        final String matricula  = user.get(SessionManager.KEY_MATRICULA);
        final String nome = user.get(SessionManager.KEY_NOME);
        final String mail = user.get(SessionManager.KEY_MAIL);
        final String tel = user.get(SessionManager.KEY_TEL);
        final String pass = user.get(SessionManager.KEY_PASSWORD);

        if(pass == null){
            Toast.makeText(getApplicationContext(),"funcionou",Toast.LENGTH_LONG).show();

        }

        user_name = (TextView) findViewById(R.id.user_name);
        user_age = (TextView) findViewById(R.id.user_age);
        user_rating = (TextView) findViewById(R.id.user_rating);
        user_photo = (ImageView) findViewById(R.id.user_photo);


        Toolbar toolbar = (Toolbar) findViewById(R.id.barra);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Informações Pessoais");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
