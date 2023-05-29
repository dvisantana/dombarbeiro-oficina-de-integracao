public class Pessoa{

    protected int id;
    protected String nome;
    protected int telefone;
    protected String tipo;


    public Pessoa() {
    }

    public Pessoa(String nome, int telefone, String tipo) {
        this.nome = nome;
        this.telefone = telefone;
        this.tipo = tipo;
    }

    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getTelefone() {
        return telefone;
    }
    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }    
}
