import java.time.LocalDateTime;

public class Financas {

    protected int tipo;
    protected double valor;
    protected String descricao;
    // protected LocalDateTime data;
    protected LocalDateTime data;


    public Financas(String descricao, double valor, LocalDateTime data, int tipo) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
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
    
    public LocalDateTime getData(){
        return data;
    }

}
