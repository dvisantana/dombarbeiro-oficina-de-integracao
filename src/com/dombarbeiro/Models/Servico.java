package src.com.dombarbeiro.Models;
public class Servico{

    protected String desc;
    protected Double valor;

    public Servico(String desc, Double valor) {
        this.desc = desc;
        this.valor = valor;
    }

    @Override
    public String toString(){
        return desc;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Double getValor() {
        return valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }
    
}
