package br.sesi.bank.bank_java_jdbc.controller;

import main.java.br.sesi.bank.bank_java_jdbc.domain.cliente.DadosCadastroCliente;
import main.java.br.sesi.bank.bank_java_jdbc.domain.conta.DadosAberturaConta;
import main.java.br.sesi.bank.bank_java_jdbc.exceptions.RegraDeNegocioException;
import main.java.br.sesi.bank.bank_java_jdbc.service.ContaService;

import java.sql.SQLException;
import java.util.Scanner;

public class BankJavaController {
    ContaService service;
    Scanner teclado;

    public BankJavaController(){
        this.service = new ContaService();
        this.teclado = new Scanner(System.in).useDelimiter("\n");
    }

    public static void main(String[] args) throws SQLException {
        BankJavaController controller = new BankJavaController();
        controller.start();
    }

    public void start() throws SQLException {
        Scanner teclado = new Scanner(System.in).useDelimiter("\n");

        var opcao = exibirMenu();
        while (opcao != 8){
            try{
                switch(opcao){
                    case 1:
                        listarContas();
                        break;
                    case 2:
                        abrirConta();
                        break;
                    case 3:
                        encerrarConta();
                        break;
                    case 4:
                        consultarSaldo();
                        break;
                    case 5:
                        realizarSaque();
                        break;
                    case 6:
                        realizarDeposito();
                        break;
                    case 7:
                        realizarTransferencia();
                        break;
                }
            }catch(RegraDeNegocioException e){
                System.out.println("Erro: " + e.getMessage());
                System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu");
                teclado.next();
            }
            opcao = exibirMenu();
        }
    }

    private int exibirMenu() {
        System.out.println("""
                BANCO DO SEU ZÉ - ESCOLHA UMA OPÇÂO:
                1: Listar contas abertas;
                2: Abertura de conta;
                3: Encerrar conta;
                4: Consultar saldo;
                5: Realizar um saque;
                6: Realizar um deposito;
                7: Realizar uma transferencia;
                8: QUIT;
                """);
        return teclado.nextInt();
    }

    private void listarContas() {
        System.out.println("Contas cadestradas:");
        var contas = service.listarContasAbertas();
        contas.stream().forEach(System.out::println);

        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao inicio!");
        teclado.next();
    }

    private void abrirConta() throws SQLException {
        System.out.println("Digite o numero da conta:");
        var numeroDaConta = teclado.nextInt();

        System.out.println("Digite o nome do cliente:");
        var nome = teclado.next();

        System.out.println("Digite o cpf do cliente:");
        var cpf = teclado.next();

        System.out.println("Digite o e-mail do cliente:");
        var email = teclado.next();

        service.abrir(new DadosAberturaConta(numeroDaConta, new DadosCadastroCliente(nome, cpf, email)));

        System.out.println("Conta criada com suscesso!");
        System.out.println("Pressione qualquer tecla para voltar para o inicio.");
        teclado.next();
    }

    private void encerrarConta() {
        System.out.println("Digite o numero da conta para fechar:");
        var numero = teclado.nextInt();

        service.encerrar(numero);

        System.out.println("Conta fechada com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para retornar ao inicio.");
        teclado.next();
    }

    private void consultarSaldo() {
        System.out.println("Digite o numero da conta para ver o saldo");
        var numero = teclado.nextInt();

        var saldo = service.consultarSaldo(numero);

        System.out.println("Seu saldo é: R$" + saldo);
        System.out.println("Pressione qualquer tecla e de ENTER para retornar ao inicio.");
        teclado.next();
    }

    private void realizarSaque() {
        System.out.println("Digite o numero da conta:");
        var numero = teclado.nextInt();

        System.out.println("Digite o valor a ser pegado:");
        var get_saldo = teclado.nextBigDecimal();

        service.realizarSaque(numero, get_saldo);

        System.out.println("O saque foi realizado com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para retornar ao inicio.");
        teclado.next();
    }

    private void realizarDeposito() {
        System.out.println("Digite o numero da conta:");
        var numero = teclado.nextInt();

        System.out.println("Digite o valor a ser depositado:");
        var valor = teclado.nextBigDecimal();

        service.realizarDeposito(numero, valor);

        System.out.println("Valor depositado com secesso");
        System.out.println("Pressione qualquer tecla e de ENTER para retornar ao inicio.");
        teclado.next();
    }

    private void realizarTransferencia() {
        System.out.println("Digite o numero da conta de ORIGEM:");
        var numero_origem = teclado.nextInt();

        System.out.println("Digite o numero da conta de DESTINO:");
        var numero_destino = teclado.nextInt();

        System.out.println("Digite o valor a ser trasferido:");
        var valor = teclado.nextBigDecimal();

        service.realizaTransferencia(numero_origem, numero_destino, valor);

        System.out.println("Valor transferido.");
        System.out.println("Pressione qualquer tecla e de ENTER para retornar ao inicio.");
        teclado.next();
    }
}