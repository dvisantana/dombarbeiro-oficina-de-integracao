import javafx.beans.property.SimpleStringProperty;

public class Servico{

    protected String desc;
    protected Double valor;

    public Servico(String desc, Double valor) {
        this.desc = desc;
        this.valor = valor;
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
