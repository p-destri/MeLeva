package petma.testesappcarona;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.NumberFormat;


public class Calculator extends AppCompatActivity implements View.OnFocusChangeListener {

    EditText input_dist, input_price, input_kmlitro;
    Spinner input_qtd;
    TextView output_total;
    double total;
    String totals = "R$"+ Double.toString(total);
    //Valores padrão das variaveis usadas para calcular o valor da carona
    double dist = 0;
    double price = 3.15;
    int qtd = 1;
    int km = 12;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        setupUI(findViewById(R.id.content_calculator)); // funcao p esconder o teclado quando clica fora

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Calculadora");


        //declaração dos componentes
        input_dist = (EditText) findViewById(R.id.input_dist);
        input_price = (EditText) findViewById(R.id.input_price);
        input_kmlitro = (EditText) findViewById(R.id.input_kmlitro);
        input_qtd = (Spinner) findViewById(R.id.input_qtd);
        output_total = (TextView) findViewById(R.id.output_total);

        // Quando apertar em next, sai do Et e abre o spinner
        input_kmlitro.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) { // quando aperta no botão de Next
                    hideKeyboard(); // esconde o teclado
                    v.clearFocus(); // tira o foco da view
                }
                return true;
            }
        });



        input_qtd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calculate(km,price,dist,position+2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        input_dist.setOnFocusChangeListener(this);
        input_kmlitro.setOnFocusChangeListener(this);
        input_price.setOnFocusChangeListener(this);


        //TODO: melhorar a help que abre
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert();

            }
        });
    }


    // Função que calcula o valor por pessoa caso dividam o custo
    private void calculate(int k, double p, double d, int q){
        try {dist = Double.parseDouble(input_dist.getText().toString());}
        catch(NumberFormatException nfe){input_dist.setError("Insira a distância");        }

        try{price = Double.parseDouble(input_price.getText().toString());}
        catch(NumberFormatException nfe){ price = 3.05; }

        try{km = Integer.parseInt(input_kmlitro.getText().toString());}
        catch(NumberFormatException nfe){km = 12;}

        try{qtd = Integer.parseInt(input_qtd.getSelectedItem().toString());}
        catch(NumberFormatException nfe){qtd = 1;}


        if(k == 0){k = 12; total = (d*p)/(k*q);}
        if(p == 0){p = 3.15;total = (d*p)/(k*q);}
        if(d == 0){total = 0;}
        total = (d*p)/(k*q);
        NumberFormat formatter = NumberFormat.getCurrencyInstance(); // coloca o valor obtido em moeda
        totals = formatter.format(total);
        output_total.setText(totals); // coloca no botão
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        calculate(km,price,dist,qtd+1);
    }




    // Função que mostra o Help
    public void showAlert(){
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom);
        dialog.setTitle("Ajuda");

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332){
            finish();
        }
        return super.onOptionsItemSelected(item);
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
