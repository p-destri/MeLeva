package petma.testesappcarona;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

public class ListaCaronasFragment extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    ListView listaCaronas;
    SessionManager session;
    Toolbar toolbar;
    DrawerLayout mDrawer;
    ActionBarDrawerToggle toggle;
    ArrayList<caronaModel> caronaLista;
    BindDictionary<caronaModel> dict;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_caronas_fragment);

        toolbar = (Toolbar) findViewById(R.id.toolbarLista);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.containerLista);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsLista);
        tabLayout.setupWithViewPager(mViewPager);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layoutNovo);
        toggle = new ActionBarDrawerToggle(this,mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert mDrawer != null;
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView nvView = (NavigationView) findViewById(R.id.nvViewNovo);
        assert nvView != null;
        nvView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //
                Intent carona = new Intent(getApplicationContext(),NovaCarona.class);
                startActivity(carona);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        HashMap<String, String>usuario = session.getUserDetails();
        final String matricula  = usuario.get(SessionManager.KEY_MATRICULA);
        final String nome = usuario.get(SessionManager.KEY_NOME);

        TextView Matricula = (TextView) findViewById(R.id.inputMatricula);
        TextView Nome = (TextView) findViewById(R.id.inputNome);

        try {
            assert Matricula != null;
            Matricula.setText(Html.fromHtml(matricula));
            assert Nome != null;
            Nome.setText(Html.fromHtml(nome));
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_logout) {
            //session.logoutUser();
            logOut();
        }
        else if (id == R.id.nav_info) {
            Intent i = new Intent(this, UserInfo.class);
            startActivity(i);

        }else if (id == R.id.nav_calc) {
            Intent i = new Intent(this, Calculator.class);
            startActivity(i);

        }
        else if (id == R.id.nav_minhasCaronas) {
            Intent i = new Intent(this, CaronasFragment.class);
            startActivity(i);
        }
        else if(id == R.id.nav_ajuda){
            Toast.makeText(getApplicationContext(),"click",Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, MainHelp.class);
            startActivity(i);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutNovo);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        SessionManager session;
        SwipeRefreshLayout swipe_refresh_layout;
        View rootView;
        ListView listaCaronas;
        ArrayList<caronaModel> caronaLista;
        BindDictionary<caronaModel> dict;
        FunDapter<caronaModel> adapter;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            getActivity().setTitle("Procurar");
            session = new SessionManager(getContext());
            rootView = inflater.inflate(R.layout.fragment_lista_caronas, container, false);
            listaCaronas = (ListView) rootView.findViewById(R.id.listaDeCaron);
            HashMap post = new HashMap();
            post.put("param","true");
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                post.put("dir", "ida");
            }
            else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2){
                post.put("dir", "volta");
            }
            PostResponseAsyncTask task = new PostResponseAsyncTask(getContext(), post, new AsyncResponse() {
                @Override
                public void processFinish(String jsonText) {
                    caronaLista = new JsonConverter<caronaModel>().toArrayList(jsonText, caronaModel.class);

                    dict = new BindDictionary<>();

                    dict.addStringField(R.id.cid, new StringExtractor<caronaModel>() {
                        @Override
                        public String getStringValue(caronaModel carona, int position) {
                            return "" + carona.cid;
                        }
                    });

                    dict.addStringField(R.id.nome, new StringExtractor<caronaModel>() {
                        @Override
                        public String getStringValue(caronaModel carona, int position) {
                            return carona.nome;
                        }
                    });

                    dict.addStringField(R.id.horario, new StringExtractor<caronaModel>() {
                        @Override
                        public String getStringValue(caronaModel carona, int position) {
                            String[] tempo = carona.horario.split(":");
                            return "" + tempo[0] + ":" + tempo[1];
                        }
                    });

                    dict.addStringField(R.id.saida, new StringExtractor<caronaModel>() {
                        @Override
                        public String getStringValue(caronaModel carona, int position) {
                            return "" + carona.local;
                        }
                    });

                    dict.addStringField(R.id.chegada, new StringExtractor<caronaModel>() {
                        @Override
                        public String getStringValue(caronaModel carona, int position) {
                            return "" + carona.chegada;
                        }
                    });
                    dict.addStringField(R.id.data, new StringExtractor<caronaModel>() {
                        @Override
                        public String getStringValue(caronaModel carona, int position) {
                            return "" + carona.data;
                        }
                    });

                    dict.addStringField(R.id.vagas, new StringExtractor<caronaModel>() {
                        @Override
                        public String getStringValue(caronaModel carona, int position) {
                            return "" + carona.vagas;
                        }
                    });

                    dict.addStringField(R.id.matricula, new StringExtractor<caronaModel>() {
                        @Override
                        public String getStringValue(caronaModel carona, int position) {
                            return "" + carona.matricula;
                        }
                    });

                    dict.addStringField(R.id.pessoa, new StringExtractor<caronaModel>() {
                        @Override
                        public String getStringValue(caronaModel carona, int position) {
                            return "" + carona.pessoa;
                        }
                    });

                    dict.addStringField(R.id.recado, new StringExtractor<caronaModel>() {
                        @Override
                        public String getStringValue(caronaModel carona, int position) {
                            return "" + carona.recado;
                        }
                    });

                    adapter = new FunDapter<>(getContext(),caronaLista, R.layout.linha, dict);

                    listaCaronas.setAdapter(adapter);
                }
            });

            task.execute("http://datapet.sites.ufsc.br/direcao.php");
            swipe_refresh_layout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_Lista);
            assert swipe_refresh_layout != null;
            swipe_refresh_layout.setOnRefreshListener(this);

            listaCaronas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent intent = new Intent(getContext(), dados.class);
                    intent.putExtra("cid", caronaLista.get(position).cid);
                    intent.putExtra("nome", caronaLista.get(position).nome);
                    intent.putExtra("local", caronaLista.get(position).local);
                    intent.putExtra("chegada", caronaLista.get(position).chegada);
                    intent.putExtra("data", caronaLista.get(position).data);
                    intent.putExtra("horario", caronaLista.get(position).horario);
                    intent.putExtra("vagas", caronaLista.get(position).vagas);
                    intent.putExtra("matricula", caronaLista.get(position).matricula);
                    intent.putExtra("pessoa", caronaLista.get(position).pessoa);
                    intent.putExtra("recado", caronaLista.get(position).recado);
                    intent.putExtra("hide", 0);
                    startActivity(intent);
                }
            });

            return rootView;
        }

        @Override
        public void onRefresh() {
            swipe_refresh_layout.setRefreshing(true);
            HashMap post = new HashMap();
            post.put("param", "true");
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                post.put("dir", "ida");
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                post.put("dir", "volta");
            }
            PostResponseAsyncTask task = new PostResponseAsyncTask(getContext(), post, new AsyncResponse() {
                @Override
                public void processFinish(String jsonText) {
                    caronaLista = new JsonConverter<caronaModel>().toArrayList(jsonText, caronaModel.class);

                    dict = new BindDictionary<>();

                    dict.addStringField(R.id.cid, new StringExtractor<caronaModel>() {
                        @Override
                        public String getStringValue(caronaModel carona, int position) {
                            return "" + carona.cid;
                        }
                    });

                    dict.addStringField(R.id.nome, new StringExtractor<caronaModel>() {
                        @Override
                        public String getStringValue(caronaModel carona, int position) {
                            return carona.nome;
                        }
                    });

                    dict.addStringField(R.id.horario, new StringExtractor<caronaModel>() {
                        @Override
                        public String getStringValue(caronaModel carona, int position) {
                            String[] tempo = carona.horario.split(":");
                            return "" + tempo[0] + ":" + tempo[1];
                        }
                    });

                    dict.addStringField(R.id.saida, new StringExtractor<caronaModel>() {
                        @Override
                        public String getStringValue(caronaModel carona, int position) {
                            return "" + carona.local;
                        }
                    });

                    dict.addStringField(R.id.chegada, new StringExtractor<caronaModel>() {
                        @Override
                        public String getStringValue(caronaModel carona, int position) {
                            return "" + carona.chegada;
                        }
                    });
                    dict.addStringField(R.id.data, new StringExtractor<caronaModel>() {
                        @Override
                        public String getStringValue(caronaModel carona, int position) {
                            return "" + carona.data;
                        }
                    });

                    dict.addStringField(R.id.vagas, new StringExtractor<caronaModel>() {
                        @Override
                        public String getStringValue(caronaModel carona, int position) {
                            return "" + carona.vagas;
                        }
                    });

                    dict.addStringField(R.id.matricula, new StringExtractor<caronaModel>() {
                        @Override
                        public String getStringValue(caronaModel carona, int position) {
                            return "" + carona.matricula;
                        }
                    });

                    dict.addStringField(R.id.pessoa, new StringExtractor<caronaModel>() {
                        @Override
                        public String getStringValue(caronaModel carona, int position) {
                            return "" + carona.pessoa;
                        }
                    });

                    dict.addStringField(R.id.recado, new StringExtractor<caronaModel>() {
                        @Override
                        public String getStringValue(caronaModel carona, int position) {
                            return "" + carona.recado;
                        }
                    });

                    adapter = new FunDapter<>(getContext(),caronaLista, R.layout.linha, dict);

                    listaCaronas.setAdapter(adapter);
                }
            });
            task.execute("http://datapet.sites.ufsc.br/direcao.php");
            swipe_refresh_layout.setRefreshing(false);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Caronas para UFSC";
                case 1:
                    return "Caronas da UFSC";
            }
            return null;
        }
    }
    public void logOut(){ //confirmar o log out
        AlertDialog ad = new AlertDialog.Builder(ListaCaronasFragment.this).create();
        ad.setTitle("SAIR");
        ad.setMessage("CONFIRMAR LOG OUT?");
        ad.setButton(AlertDialog.BUTTON_NEUTRAL, "NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {dialog.dismiss();} });
        ad.setButton(AlertDialog.BUTTON_POSITIVE, "SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {session.logoutUser();}});
        ad.show();
    }
}
