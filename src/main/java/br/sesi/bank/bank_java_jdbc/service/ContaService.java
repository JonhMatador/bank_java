package main.java.br.sesi.bank.bank_java_jdbc.service;

import main.java.br.sesi.bank.bank_java_jdbc.domain.cliente.Cliente;
import main.java.br.sesi.bank.bank_java_jdbc.domain.conta.Conta;
import main.java.br.sesi.bank.bank_java_jdbc.domain.conta.DadosAberturaConta;
import main.java.br.sesi.bank.bank_java_jdbc.exceptions.RegraDeNegocioException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ContaService {
    private Set<Conta> contas = new HashSet<>();

    public ContaService(){ }

    public Set<Conta> listarContasAbertas() {return null;}

    public BigDecimal consultarSaldo(Integer numeroDaConta) { return BigDecimal.ZERO;}

    public void abrir(DadosAberturaConta dadosDaConta) throws SQLException {
        Cliente cliente = new Cliente(dadosDaConta.dadoscliente);
        Conta conta = new Conta(dadosDaConta.numero, BigDecimal.ZERO, cliente);
        if (contas.contains(conta)){
            throw new RegraDeNegocioException("Já existe outra conta aberta o mesmo numero.");
        }

        contas.add(conta);
    }

    public void realizarSaque(Integer numeroDaConta, BigDecimal valor) {
        var conta = buscarContaPorNumero(numeroDaConta);

        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new RegraDeNegocioException("Valor de saque deve ser superior a zero.");
        }

        if(valor.compareTo(conta.getSaldo()) > 0){
            throw new RegraDeNegocioException("Saldo insuficiente.");
        }

        conta.sacar(valor);
    }

    public void realizarDeposito(Integer numeroDaConta, BigDecimal valor) {
        var conta = buscarContaPorNumero(numeroDaConta);

        if(valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new RegraDeNegocioException("Valor de deposito deve ser superior a zero.");
        }

        conta.depositar(valor);
    }

    public void realizaTransferencia(Integer numeroContaOrigem, Integer numeroContaDestino, BigDecimal valor){ }

    public void encerrar(Integer numeroDaConta){
        var conta = buscarContaPorNumero(numeroDaConta);

        if(conta.possuiSaldo()){
            throw new RegraDeNegocioException("Conta não pode ser encerrada pois existe saldo!");
        }

        contas.remove(conta);
    }

    private Conta buscarContaPorNumero(Integer numero) {
        return contas
                .stream()
                .filter(c -> c.getNumero() == numero)
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Não existe conta com esse numero!"))
        ;


    }

    private void alterar(Integer numeroDaConta, BigDecimal valor) { }
}

