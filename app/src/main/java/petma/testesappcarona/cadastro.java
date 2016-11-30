package petma.testesappcarona;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;




import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;

public class cadastro extends AppCompatActivity{


    EditText inputTel,inputMail;
    TextView inputName, inputMatricula;
    Button btnCadastrar;
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("        Cadastro");
        session = new SessionManager(getApplicationContext());


        //Criando um dicionario
        HashMap<String, String> user = session.getUserDetails();
        final String matricula_ = user.get(SessionManager.KEY_MATRICULA) ;
        String pass_ = user.get(SessionManager.KEY_PASSWORD);
        Toast.makeText(getApplicationContext(),"Sua senha é: " + pass_,Toast.LENGTH_LONG).show();


        //Formatando o Edit Text pra receber o número de telefone
        //Vai ajustar no formato de acordo com a localização da pessoa
        //(XX) XXXX-XXXX
        inputTel = (EditText) findViewById(R.id.input_phone);
        inputTel.addTextChangedListener(new PhoneNumberFormattingTextWatcher()); // formatando o telefone
        inputMail = (EditText) findViewById(R.id.input_email);
        inputName = (TextView) findViewById(R.id.input_name);
        inputMatricula = (TextView) findViewById(R.id.input_matricula);
        assert inputMatricula != null;
        inputMatricula.setText(matricula_); //nunca vai ser null!! Pra fazer login tem que ter um valor lá;



        //------------------------------------------------------------------------------------------
        btnCadastrar = (Button) findViewById(R.id.cadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = inputName.getText().toString();
                String tel = inputTel.getText().toString();
                final String mail = inputMail.getText().toString().trim();
                final String mailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String matricula = inputMatricula.getText().toString();


               if(nome.trim().length() > 0 && mail.trim().length() > 0 && tel.trim().length() >0){
                   if (mail.matches(mailPattern)) {
                       session.createUserSession(nome, mail, tel, matricula); // coloca as infos em shared prefs.
                       session.createLoginSession(); // diz que o user ta logado
                       session.deletePass(); // deletando o password
                       Toast.makeText(getApplicationContext(), "User criado", Toast.LENGTH_LONG).show();
                       Intent i = new Intent(getApplicationContext(), ListaCaronasFragment.class);
                       startActivity(i);
                       finish();
                   }
                   else{
                       inputMail.setError("Digite um e-mail válido");
                       Toast.makeText(getApplicationContext(),"E-mail inválidooooo", Toast.LENGTH_LONG).show();
                   }


               }
               else{Toast.makeText(getApplicationContext(),"Insira os dados", Toast.LENGTH_LONG).show();}

            }
        });

    }

    private void goToact() {
        Intent i = new Intent(this, ListaCaronasFragment.class);
        i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP | i.FLAG_ACTIVITY_CLEAR_TASK | i.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }


    // onBackPressed lida com as ações quando o usuário clica no botão de retorno
    @Override
    public void onBackPressed() {
        Toast teste = Toast.makeText(getApplicationContext(),"Can´t return",Toast.LENGTH_LONG);
        teste.setGravity(0,0, Gravity.CENTER);
        teste.show();
    }

}
