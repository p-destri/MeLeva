package petma.testesappcarona;

/**
 * Created by air on 28/09/2016.
 */
public class Caronas {

    private String nome;
    private String dia;
    private String local;
    private String chegada;
    private String horario;
    private int vagas;


    public Caronas (String nome,String dia, String local, String chegada, String horario, int vagas){
        this.nome = nome;
        this.dia = dia;
        this.local = local;
        this.chegada = chegada;
        this.horario = horario;
        this.vagas = vagas;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getChegada() {
        return chegada;
    }

    public void setChegada(String chegada) {
        this.chegada = chegada;
    }

    public String getHorario() { return horario;}

    public void setHorario(String horario) { this.horario = horario;}

    public int getVagas() { return vagas;}

    public void setVagas(int vagas) { this.vagas = vagas;}


}
