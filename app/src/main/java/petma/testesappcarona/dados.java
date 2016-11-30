package petma.testesappcarona;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.w3c.dom.Text;

import java.util.HashMap;

public class dados extends AppCompatActivity{

    Button pegaCarona, pegaPerfil;
    SessionManager session;
    int cid, feito, restantes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados);
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        pegaCarona = (Button) findViewById(R.id.pegaCarona);
        pegaPerfil = (Button) findViewById(R.id.pegaPerfil);

        Intent intent = getIntent();
        String[] data = intent.getSerializableExtra("data").toString().split("-");
        String[] tempo = intent.getSerializableExtra("horario").toString().split(":");

        cid = (int) intent.getSerializableExtra("cid");

        final String matricula = intent.getSerializableExtra("matricula").toString();
        String nomeStr = intent.getSerializableExtra("nome").toString();
        String localStr =  intent.getSerializableExtra("local").toString();
        String chegadaStr = intent.getSerializableExtra("chegada").toString();
        String recadoStr = intent.getSerializableExtra("recado").toString();
        final String vagasStr = intent.getSerializableExtra("vagas").toString();

        String diaStr = data[2]+ "/" + data[1] + "/" + data[0];
        String horarioStr = tempo[0] + ":" + tempo[1];

        TextView nome = (TextView) findViewById(R.id.txtNome);
        TextView local = (TextView) findViewById(R.id.txtLocal);
        TextView chegada = (TextView) findViewById(R.id.txtChegada);
        TextView dia = (TextView) findViewById(R.id.txtData);
        TextView horario = (TextView) findViewById(R.id.txtHorario);
        TextView vagas = (TextView) findViewById(R.id.txtVagas);
        TextView recado = (TextView) findViewById(R.id.txtRecado);

        TextView passag1 = (TextView) findViewById(R.id.passag1);
        TextView passag2 = (TextView) findViewById(R.id.passag2);
        TextView passag3 = (TextView) findViewById(R.id.passag3);
        TextView passag4 = (TextView) findViewById(R.id.passag4);

        assert nome != null;
        nome.setText(nomeStr);
        assert dia != null;
        dia.setText(diaStr);
        assert local != null;
        local.setText(localStr);
        assert chegada != null;
        chegada.setText(chegadaStr);
        assert horario != null;
        horario.setText(horarioStr);
        assert vagas != null;
        vagas.setText(vagasStr);
        assert recado != null;
        recado.setText(recadoStr);

        feito = 0;

        if ((Integer.parseInt(String.valueOf(intent.getSerializableExtra("hide"))) == 1) || String.valueOf(matricula).equals(session.getUserDetails().get("matricula"))){
            pegaCarona.setVisibility(View.GONE);
            pegaPerfil.setVisibility(View.GONE);
            String[] pessoa = intent.getSerializableExtra("pessoa").toString().split("/");
            for (int i = 1; i <= pessoa.length - 1; i++) {
                if(passag1.getText().equals("Vago") && i == 1){
                    passag1.setText(pessoa[i]);
                }
                if(passag2.getText().equals("Vago")&& i == 2){
                    passag2.setText(pessoa[i]);
                }
                if(passag3.getText().equals("Vago") && i == 3){
                    passag3.setText(pessoa[i]);
                }
                if (passag4.getText().equals("Vago")&& i == 4){
                    passag4.setText(pessoa[i]);
                }
            }
            feito = 1;
        }
        else if (Integer.parseInt(String.valueOf(intent.getSerializableExtra("vagas"))) <= 0){
            pegaCarona.setVisibility(View.GONE);
            String[] pessoa = intent.getSerializableExtra("pessoa").toString().split("/");
            if (feito == 0) {
                for (int i = 1; i <= pessoa.length - 1; i++) {
                    if (passag1.getText().equals("Vago") && i == 1) {
                        passag1.setText(pessoa[i]);
                    }
                    if (passag2.getText().equals("Vago") && i == 2) {
                        passag2.setText(pessoa[i]);
                    }
                    if (passag3.getText().equals("Vago") && i == 3) {
                        passag3.setText(pessoa[i]);
                    }
                    if (passag4.getText().equals("Vago") && i == 4) {
                        passag4.setText(pessoa[i]);
                    }
                }
            }
            feito = 1;
        }
        else{
            String[] pessoa = intent.getSerializableExtra("pessoa").toString().split("/");
            for (int i = 1; i <= pessoa.length - 1; i++) {
                if(feito == 0) {
                    if (passag1.getText().equals("Vago") && i == 1) {
                        passag1.setText(pessoa[i]);
                    }
                    if (passag2.getText().equals("Vago") && i == 2) {
                        passag2.setText(pessoa[i]);
                    }
                    if (passag3.getText().equals("Vago") && i == 3) {
                        passag3.setText(pessoa[i]);
                    }
                    if (passag4.getText().equals("Vago") && i == 4) {
                        passag4.setText(pessoa[i]);
                    }
                }
                if (pessoa[i].equals(String.valueOf(session.getUserDetails().get("matricula")))) {
                    pegaCarona.setVisibility(View.GONE);
                }
            }
            feito = 1;
        }

        restantes = (int) intent.getSerializableExtra("vagas");

        if (passag1.getText().equals("Vago")){
            if (restantes > 0) {
                restantes--;
            }
            else{
                passag1.setVisibility(View.GONE);
            }
        }
        if (passag2.getText().equals("Vago")){
            if (restantes > 0) {
                restantes--;
            }
            else{
                passag2.setVisibility(View.GONE);
            }
        }
        if (passag3.getText().equals("Vago")){
            if (restantes > 0) {
                restantes--;
            }
            else{
                passag3.setVisibility(View.GONE);
            }
        }
        if (passag4.getText().equals("Vago")){
            if (restantes > 0) {
                restantes--;
            }
            else{
                passag4.setVisibility(View.GONE);
            }
        }

        pegaCarona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.valueOf(vagasStr) > 0){
                    String novaVaga = String.valueOf((Integer.valueOf(vagasStr) - 1));
                    HashMap post = new HashMap();
                    post.put("txtVagas", novaVaga);
                    post.put("txtCid", String.valueOf(cid));
                    post.put("carona", session.getUserDetails().get("matricula"));
                    PostResponseAsyncTask task = new PostResponseAsyncTask(dados.this, post, new AsyncResponse() {
                        @Override
                        public void processFinish(String result) {
                            if (result.contains("success")){
                                Intent i = new Intent(dados.this, ListaCaronasFragment.class);
                                startActivity(i);
                            }
                        }
                    });
                    task.execute("http://datapet.sites.ufsc.br/pegaCarona.php");
                }
            }
        });

        pegaPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nova = new Intent(dados.this, InfoGerais.class);
                nova.putExtra("matricula", matricula);
                startActivity(nova);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
