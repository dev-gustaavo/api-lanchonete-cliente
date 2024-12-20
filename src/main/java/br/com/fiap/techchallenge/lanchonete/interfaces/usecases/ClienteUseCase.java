package br.com.fiap.techchallenge.lanchonete.interfaces.usecases;

import br.com.fiap.techchallenge.lanchonete.entities.Cliente;

import java.util.List;

public interface ClienteUseCase {

    Cliente save(Cliente cliente) throws Exception;

    Cliente buscarClientePorCpf(String cpf) throws Exception;

    List<Cliente> list() throws Exception;

    Cliente update(String cpf, Cliente cliente) throws Exception;

    void delete(String cpf) throws Exception;
}
