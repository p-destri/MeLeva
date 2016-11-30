package petma.testesappcarona;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "petPref";

    // All Shared Preferences Keys
    // KEYS USADAS PARA LOG_IN
    private static final String IS_LOGIN = "IsLoggedIn";
    //matricula (make variable public to access from outside)
    public static final String KEY_MATRICULA = "matricula";
    //------------------------------------------------------
    //KEYS USADAS PARA DEIXAR O USER SALVO NO DEVICE
    public static final String KEY_NOME = "nome"; //nome
    public static final String KEY_MAIL = "mail"; // email
    public static final String KEY_TEL = "tel"; // telefone - DEIXA COMO STRING P PODER MOSTRAR
    //------------------------------------------------------
    public static final String KEY_PASSWORD = "password";


    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */

    // criando a sessão do log in
    public void createLoginSession(){ //  depois colocar nome e email como param. String name, String email
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true); // colocando em IS_LOGIN  o valor true
        // commit changes -- salva as mudanças
        editor.commit();
    }

    /**
     * Cria uma seção de Usuário.
     * Vai pegar os dados do DB, usando a matrícula, e jogar em SharedPreferences
     * **/
    public void createUserSession(String nome, String mail, String tel, String matricula){
        editor.putString(KEY_NOME, nome);

        editor.putString(KEY_MAIL, mail);

        editor.putString(KEY_TEL, tel);

        editor.putString(KEY_MATRICULA,matricula);

        editor.commit();

    }

    public void createPass(String pass){
        editor.putString(KEY_PASSWORD,pass);
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */

    // TODO : USAR ESSES FLAGS
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){  // se nao estiver logado, redireciona pra Act
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user nome
        user.put(KEY_NOME, pref.getString(KEY_NOME, null));
        // user emai
        user.put(KEY_MAIL, pref.getString(KEY_MAIL, null));
        //user telefone
        user.put(KEY_TEL, pref.getString(KEY_TEL, null));
        // user matricula
        user.put(KEY_MATRICULA,pref.getString(KEY_MATRICULA,null));
        //user senha
        user.put(KEY_PASSWORD,pref.getString(KEY_PASSWORD,null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    public void deletePass(){
        String pass = null;
        editor.putString(KEY_PASSWORD,pass);
        editor.commit();

    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public String teste(){
        return pref.getString(KEY_MATRICULA,"sem numero de matricula");
    }

//    public String[] userData(){
//        String userDataList[] = new String[4];
//        userDataList[0] = pref.getString(KEY_NOME, "Não há nome");
//        userDataList[0] = pref.getString(KEY_MATRICULA, "Não há matrícula");
//        userDataList[0] = pref.getString(KEY_MAIL, "Não há e-mail");
//        userDataList[0] = pref.getString(KEY_TEL, "Não há telefone");
//
//        return userDataList;
//
//    }


}
