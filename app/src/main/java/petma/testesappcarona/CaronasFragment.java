package petma.testesappcarona;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
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

public class CaronasFragment extends AppCompatActivity implements AsyncResponse, SwipeRefreshLayout.OnRefreshListener{

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

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caronas_fragment);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_caronas, menu);
        return true;
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

    @Override
    public void processFinish(String jsonText) {
        ArrayList<caronaModel> caronaLista = new JsonConverter<caronaModel>().toArrayList(jsonText, caronaModel.class);

        BindDictionary<caronaModel> dict = new BindDictionary<>();

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

        FunDapter<caronaModel> adapter = new FunDapter<>(CaronasFragment.this,
                caronaLista, R.layout.linha, dict);

        listaCaronas.setAdapter(adapter);

    }

    @Override
    public void onRefresh() {
        Toast.makeText(this, "Refreshou", Toast.LENGTH_SHORT).show();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private SessionManager session;
        private SwipeRefreshLayout swipe_refresh_layout;
        private View rootView;
        private ListView listaCaronas;
        private ArrayList<caronaModel> caronaLista;

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
            getActivity().setTitle("Minhas Caronas");
            session = new SessionManager(getContext());
            rootView = inflater.inflate(R.layout.fragment_caronas, container, false);
            listaCaronas = (ListView) rootView.findViewById(R.id.listaCaron);

            HashMap post = new HashMap();
            post.put("param","true");
            post.put("matricula", session.getUserDetails().get("matricula"));
            PostResponseAsyncTask task = new PostResponseAsyncTask(getContext(), post, new AsyncResponse() {
                @Override
                public void processFinish(String jsonText) {
                    caronaLista = new JsonConverter<caronaModel>().toArrayList(jsonText, caronaModel.class);

                    BindDictionary<caronaModel> dict = new BindDictionary<>();

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

                    FunDapter<caronaModel> adapter = new FunDapter<>(getContext(),
                            caronaLista, R.layout.linha, dict);

                    listaCaronas.setAdapter(adapter);
                }
            });
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                task.execute("http://datapet.sites.ufsc.br/dadas.php");
            }
            else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2){
                task.execute("http://datapet.sites.ufsc.br/recebidas.php");
            }

            swipe_refresh_layout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layoutCaronas);
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
                    intent.putExtra("hide", 1);
                    startActivity(intent);
                }
            });
            return rootView;
        }

        @Override
        public void onRefresh() {
            swipe_refresh_layout.setRefreshing(true);
            HashMap post = new HashMap();
            post.put("param","true");
            post.put("matricula", session.getUserDetails().get("matricula"));
            PostResponseAsyncTask task = new PostResponseAsyncTask(getContext(), post, new AsyncResponse() {
                @Override
                public void processFinish(String jsonText) {
                    ArrayList<caronaModel> caronaLista = new JsonConverter<caronaModel>().toArrayList(jsonText, caronaModel.class);

                    BindDictionary<caronaModel> dict = new BindDictionary<>();

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

                    FunDapter<caronaModel> adapter = new FunDapter<>(getContext(),
                            caronaLista, R.layout.linha, dict);

                    listaCaronas.setAdapter(adapter);
                }
            });
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                task.execute("http://datapet.sites.ufsc.br/dadas.php");
            }
            else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2){
                task.execute("http://datapet.sites.ufsc.br/recebidas.php");
            }
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
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Caronas Dadas";
                case 1:
                    return "Caronas Recebidas";
            }
            return null;
        }
    }

    //Funções para esconder o teclado:




}
