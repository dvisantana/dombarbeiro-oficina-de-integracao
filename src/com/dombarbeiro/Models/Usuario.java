package src.com.dombarbeiro.Models;
import javafx.beans.property.SimpleStringProperty;

public class Usuario{

    protected String nome;
    protected String usuario;
    protected String senha;
    protected String tipoStr;
    protected int tipo;

    public Usuario(){}

    public Usuario(String nome, String usuario, String senha, int tipo) {
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.tipo = tipo;
        if(tipo == 0){
            tipoStr = "Administrador";
        }else if(tipo == 1){
            tipoStr = "Secretário";
        }
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public int getTipo() {
        return tipo;
    }
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    public String getTipoStr(){
        return tipoStr;
    }
}
