package petma.testesappcarona;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class InfoGerais extends AppCompatActivity implements AsyncResponse {

    String[] teste, teste2;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_gerais);
        Toolbar toolbar = (Toolbar) findViewById(R.id.barra);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView Matricula = (TextView) findViewById(R.id.matricula_info);
        final TextView Nome = (TextView) findViewById(R.id.name_info);
        final TextView Mail = (TextView) findViewById(R.id.email_info);
        final TextView Tel = (TextView) findViewById(R.id.phone_info);

        Intent intent = getIntent();
        String matricula = intent.getSerializableExtra("matricula").toString();
        HashMap post = new HashMap();
        post.put("matricula", String.valueOf(matricula));
        post.put("comunica", "true");
        PostResponseAsyncTask task = new PostResponseAsyncTask(this, post, new AsyncResponse() {
            @Override
            public void processFinish(String result) {
                teste = result.split(",");
                teste2 = (Arrays.toString(teste)).split("\"");
                Matricula.setText(teste2[7]);
                Nome.setText(teste2[23]);
                Mail.setText(teste2[19]);
                Tel.setText(teste2[15]);

            }
        });
        task.execute("http://datapet.sites.ufsc.br/info.php");


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void processFinish(String jsonText) {
        /*
        infoList = new JsonConverter<info>().toArrayList(jsonText, info.class);

        BindDictionary<info> dict = new BindDictionary<>();

        dict.addStringField(R.id.uidEx, new StringExtractor<info>() {
            @Override
            public String getStringValue(info item, int position) {
                return "" + item.uid;
            }
        });

        dict.addStringField(R.id.matriculaEx, new StringExtractor<info>() {
            @Override
            public String getStringValue(info item, int position) {
                return "" + item.mat;
            }
        });

        dict.addStringField(R.id.idadeEx, new StringExtractor<info>() {
            @Override
            public String getStringValue(info item, int position) {
                return "" + item.idade;
            }
        });

        dict.addStringField(R.id.phoneEx, new StringExtractor<info>() {
            @Override
            public String getStringValue(info item, int position) {
                return "" + item.telefone;
            }
        });

        dict.addStringField(R.id.emailEx, new StringExtractor<info>() {
            @Override
            public String getStringValue(info item, int position) {
                return "" + item.email;
            }
        });

        dict.addStringField(R.id.nameEx, new StringExtractor<info>() {
            @Override
            public String getStringValue(info item, int position) {
                return "" + item.name;
            }
        });

        FunDapter<info> adapter = new FunDapter<>(InfoGerais.this,
                infoList, R.layout.activity_info, dict);
        */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("InfoGerais Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
