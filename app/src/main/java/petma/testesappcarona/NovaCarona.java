package petma.testesappcarona;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.Calendar;
import java.util.HashMap;


public class NovaCarona extends AppCompatActivity implements View.OnClickListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    FloatingActionButton fab;
    private ViewPager mViewPager;
    Toolbar toolbar;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_carona);

        toolbar = (Toolbar) findViewById(R.id.toolbarNovo);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.containerNovo);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        fab = (FloatingActionButton) findViewById(R.id.fabNovo);
        fab.setOnClickListener(new View.OnClickListener() {
            boolean data, hora, saida, chegada, nome, recado;

            @Override
            public void onClick(View view) {
                PlaceholderFragment frag = (PlaceholderFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.containerNovo + ":" + mViewPager.getCurrentItem());
                data = dataCheck(frag);
                hora = timeCheck(frag);
                saida = saidaCheck(frag);
                chegada = chegadaCheck(frag);
                nome = nomeCheck(frag);

                if (data && hora && saida && chegada && nome) {
                    HashMap post = new HashMap();
                    post.put("txtData", frag.input_data.getText().toString());
                    post.put("txtNome", frag.input_nome.getText().toString());
                    post.put("txtLocal", frag.input_saida.getText().toString());
                    post.put("txtChegada", frag.input_chegada.getText().toString());
                    post.put("txtHorario", frag.input_horario.getText().toString());
                    post.put("txtVagas", frag.input_pessoas.getSelectedItem().toString());
                    post.put("txtMatricula", session.getUserDetails().get("matricula"));
                    post.put("txtRecado", frag.input_Recado.getText().toString());

                    if (mViewPager.getCurrentItem() == 0) {
                        post.put("txtDir", "ida");
                    }
                    if (mViewPager.getCurrentItem() == 1){
                        post.put("txtDir", "volta");
                    }

                    post.put("mobile", "android");
                    PostResponseAsyncTask task = new PostResponseAsyncTask(NovaCarona.this, post, new AsyncResponse() {
                        @Override
                        public void processFinish(String result) {
                            if (result.contains("success")) {
                                startActivity(new Intent(NovaCarona.this, ListaCaronasFragment.class));
                            }
                        }
                    });

                    task.execute("http://datapet.sites.ufsc.br/inserir.php");
                }
            }
        });
    }

    //Esconder teclado em todas as abas (fragments)
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public boolean dataCheck(PlaceholderFragment frag){
        if(frag.input_data.getText().toString().trim().equals("")){
            setInput_data(frag);
            return  false;
        }
        return true;
    }

    public boolean timeCheck(PlaceholderFragment frag){
        if(frag.input_horario.getText().toString().trim().equals("")){
            setInput_horario(frag);
            return  false;
        }
        return true;
    }

    public boolean chegadaCheck(PlaceholderFragment frag){
        if (frag.input_chegada.getText().toString().trim().length() < 1)
        {
            frag.input_chegada.setError("Insira o local de chegada");
            return  false;
        }
        frag.input_chegada.setError(null);
        return true;
    }

    public boolean saidaCheck(PlaceholderFragment frag){
        if (frag.input_saida.getText().toString().trim().length() < 1)
        {
            frag.input_saida.setError("Insira o local de saída");
            return  false;
        }
        frag.input_saida.setError(null);
        return true;

    }

    public boolean nomeCheck(PlaceholderFragment frag){
        if(frag.input_nome.getText().toString().trim().length() < 4){
            frag.input_nome.setError("Insira o nome da carona");
            return false;
        }
        frag.input_nome.setError(null);
        return true;

    }

    public void setInput_data(final PlaceholderFragment frag){
        int mYear, mMonth, mDay;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        frag.input_data.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void setInput_horario(final PlaceholderFragment frag){
        int mHour, mMinute;
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        if(minute <= 9){
                            frag.input_horario.setText(hourOfDay + ":"+"0"+minute);
                        }
                        else{
                            frag.input_horario.setText(hourOfDay + ":" + minute);
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nova_carona, menu);
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
    public void onClick(View view) {

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        EditText input_nome,input_data, input_horario, input_saida, input_chegada, input_Recado;
        Spinner input_pessoas;
        SessionManager session;


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
            View rootView = inflater.inflate(R.layout.fragment_nova_carona, container, false);

            session = new SessionManager(getContext());
            session.checkLogin();

            input_data = (EditText)rootView.findViewById(R.id.input_data);
            assert input_data != null;
            input_data.setOnClickListener(this);


            input_horario = (EditText) rootView.findViewById(R.id.input_horario);
            assert input_horario != null;
            input_horario.setOnClickListener(this);

            input_chegada = (EditText)rootView.findViewById(R.id.input_chegada);
            input_saida = (EditText)rootView.findViewById(R.id.input_saida);
            input_nome = (EditText)rootView.findViewById(R.id.input_nome);
            input_pessoas = (Spinner)rootView.findViewById(R.id.input_pessoas);
            input_Recado = (EditText) rootView.findViewById(R.id.input_Recado);

            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Adicionar Carona");
            return rootView;
        }

        @Override
        public void onClick(View view) {
            if (view == input_data){
                int mYear, mMonth, mDay;
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                input_data.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
            else if (view == input_horario){
                int mHour, mMinute;
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                if(minute <= 9){
                                    input_horario.setText(hourOfDay + ":"+"0"+minute);
                                }
                                else{
                                    input_horario.setText(hourOfDay + ":" + minute);
                                }
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
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
                    return "Caronas da UFSC";
                case 1:
                    return "Caronas para UFSC";
            }
            return null;
        }
    }
}