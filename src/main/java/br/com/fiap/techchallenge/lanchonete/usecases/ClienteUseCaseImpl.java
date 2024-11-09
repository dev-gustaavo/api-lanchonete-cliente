package br.com.fiap.techchallenge.lanchonete.usecases;

import br.com.fiap.techchallenge.lanchonete.entities.Cliente;
import br.com.fiap.techchallenge.lanchonete.entities.MensagemErroPadrao;
import br.com.fiap.techchallenge.lanchonete.exceptions.ResourceNotFoundException;
import br.com.fiap.techchallenge.lanchonete.interfaces.gateways.ClienteGateway;
import br.com.fiap.techchallenge.lanchonete.interfaces.usecases.ClienteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteUseCaseImpl implements ClienteUseCase {

    private final ClienteGateway clienteGateway;

    @Override
    public Cliente save(Cliente cliente) throws Exception {

        try {
            clienteGateway.buscarClientePorCpf(cliente.getCpf());

            throw new Exception(MensagemErroPadrao.CLIENTE_CADASTRADO);
        } catch (ResourceNotFoundException ResourceNotFoundException) {
            return clienteGateway.save(cliente);
        } catch (Exception exception) {
            throw new Exception(exception.getMessage(), exception);
        }
    }

    @Override
    public Cliente buscarClientePorCpf(String cpf) throws Exception {
        return clienteGateway.buscarClientePorCpf(cpf);
    }

    @Override
    public List<Cliente> list() throws Exception {
        return clienteGateway.list();
    }

    @Override
    public Cliente update(String cpf, Cliente cliente) throws Exception {
        return clienteGateway.updateCliente(cpf, cliente);
    }

    @Override
    public void delete(String cpf) throws Exception {
        clienteGateway.delete(cpf);
    }
}
