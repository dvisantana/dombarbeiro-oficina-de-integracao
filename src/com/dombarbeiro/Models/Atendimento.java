package src.com.dombarbeiro.Models;
import java.time.LocalDateTime;

public class Atendimento {

    protected LocalDateTime data;
    protected Pessoa cliente;
    protected boolean confirmado;
    protected Servico servico;
    protected String login;

    

    // public Atendimento(LocalDateTime data, Pessoa cliente, String servico) {
    //     this.data = data;
    //     this.cliente = cliente;
    //     this.servico = servico;
    // }

    public Atendimento() {}

    public Atendimento(LocalDateTime data, int clienteId,String clienteNome, String servico) {
        this.data = data;
        this.cliente = new Pessoa(clienteId,clienteNome);
        this.servico = new Servico(servico, null);
    }



    public LocalDateTime getData() {
        return data;
    }
    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Pessoa getCliente() {
        return cliente;
    }
    public void setClienteId(Pessoa cliente) {
        this.cliente = cliente;
    }

    public boolean isConfirmado() {
        return confirmado;
    }
    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

    public Servico getServico() {
        return servico;
    }
    public void setServico(Servico servico) {
        this.servico = servico;
    }
    
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }



    
}
