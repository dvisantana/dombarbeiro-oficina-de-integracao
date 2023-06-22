public class Pessoa{

    protected int id;
    protected String nome;
    protected String telefone;
    protected String tipo;


    public Pessoa() {
    }

    public Pessoa(int id,String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Pessoa(String nome, String telefone, String tipo) {
        this.nome = nome;
        this.telefone = telefone;
        this.tipo = tipo;
    }

    public Pessoa(int id, String nome, String telefone, String tipo) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return nome;
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

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }    
}
