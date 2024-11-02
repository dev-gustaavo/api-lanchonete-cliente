package br.com.fiap.techchallenge.lanchonete.interfaces.gateways;

import br.com.fiap.techchallenge.lanchonete.entities.Cliente;

import java.util.List;

public interface ClienteGateway {

    Cliente save(Cliente cliente) throws Exception;

    Cliente buscarClientePorCpf(String cpf) throws Exception;

    List<Cliente> list() throws Exception;

    Cliente updateCliente(String id, Cliente cliente) throws Exception;

    void delete(String id) throws Exception;
}
