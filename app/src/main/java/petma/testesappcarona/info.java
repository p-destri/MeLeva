package petma.testesappcarona;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.annotations.SerializedName;


public class info extends AppCompatActivity{

    @SerializedName("uid")
    public int uid;

    @SerializedName("matricula")
    public int mat;

    @SerializedName("idade")
    public String idade;

    @SerializedName("telefone")
    public String telefone;

    @SerializedName("email")
    public String email;

    @SerializedName("nome")
    public String name;
}
