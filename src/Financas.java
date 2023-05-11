public class Financas {

    protected int tipo;
    protected double valor;
    protected String descricao;
    protected String data;


    public Financas(String descricao, double valor, String data) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    public int getTipo() {
        return tipo;
    }
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
       

}
