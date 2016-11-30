package petma.testesappcarona;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {


    // Email, password EditText
    EditText inputMatricula , inputPassword;
    TextView login_help;

    // login button
    Button btnLogin;

    // Session Manager Class
    SessionManager session; // criando um objeto da classe sessionmanager

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupUI(findViewById(R.id.activity_login));

        // Session Manager -- craia objeto da classe SessionManager
        session = new SessionManager(getApplicationContext());

        // Email, Password input text
        inputMatricula = (EditText) findViewById(R.id.login_input_matricula);
        inputPassword = (EditText) findViewById(R.id.input_password);

        //TextView que redireciona p/ help
        login_help = (TextView) findViewById(R.id.login_help);

        login_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Inst.class);
                startActivity(i);
            }});


        // Login button
        btnLogin = (Button) findViewById(R.id.login_btn);

        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String matricula = inputMatricula.getText().toString();
                String password = inputPassword.getText().toString();

                // Check if username, password is filled
                if(matricula.trim().length() < 8 && password.trim().length() < 6){
                    Toast toast =  Toast.makeText(getApplicationContext(),"Usuário ou senha inválidos, \n" +
                            "para mais informações clique em 'HELP'",Toast.LENGTH_LONG);
                   toast.setGravity(Gravity.CENTER,0,0);
                   toast.show();
                }
                else if(matricula.trim().length() < 8){
                    inputMatricula.setError("Digite seu número de matrícula");
                }
                else if(password.trim().length() < 6){
                    inputPassword.setError("Digite sua senha do CAGR/MOODLE");
                }
                else{
                    // try to log in
                    // catch --> display error, incrementa contador ?
                    //session.createLoginSession(); // só cria log_in se já tem cadastro
                    session.createUserSession(null,null,null, matricula); // coloca matricula em Sharede Pref
                    session.createPass(password);

                    //TODO: Verificar se ta no DB, se ta, goto lista, se não goto cadastro
                    open(); // abre a próxima activity
                    //


                }

            }
        });

    }

    // onBackPressed lida com as ações quando o usuário clica no botão de retorno
    @Override
    public void onBackPressed() {
        Toast teste = Toast.makeText(getApplicationContext(),"Can´t return",Toast.LENGTH_LONG);
        teste.setGravity(0,0,Gravity.CENTER);
        teste.show();
    }

    public void open(){
        Intent i = new Intent(getApplicationContext(), cadastro.class);
        startActivity(i);
        finish();
    }

    //função pra esconder o teclado
    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void setupUI(View view) { //Usa para esconder o teclado

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideKeyboard();
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    } //

}