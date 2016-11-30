package petma.testesappcarona;

import com.google.gson.annotations.SerializedName;


public class caronaModel {

    @SerializedName("cid")
    public int cid;

    @SerializedName("nome")
    public String nome;

    @SerializedName("origem")
    public String local;

    @SerializedName("destino")
    public String chegada;

    @SerializedName("data")
    public String data;

    @SerializedName("horario")
    public String horario;

    @SerializedName("vagas")
    public int vagas;

    @SerializedName("matricula")
    public String matricula;

    @SerializedName("pessoa")
    public String pessoa;

    @SerializedName("recado")
    public String recado;
}
