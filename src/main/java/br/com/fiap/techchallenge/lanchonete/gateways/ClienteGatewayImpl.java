package br.com.fiap.techchallenge.lanchonete.gateways;

import br.com.fiap.techchallenge.lanchonete.adapters.mappers.ClienteMapper;
import br.com.fiap.techchallenge.lanchonete.entities.Cliente;
import br.com.fiap.techchallenge.lanchonete.entities.MensagemErroPadrao;
import br.com.fiap.techchallenge.lanchonete.entities.dbEntities.ClienteEntity;
import br.com.fiap.techchallenge.lanchonete.exceptions.ResourceNotFoundException;
import br.com.fiap.techchallenge.lanchonete.interfaces.dbconnection.RepositoryCliente;
import br.com.fiap.techchallenge.lanchonete.interfaces.gateways.ClienteGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ClienteGatewayImpl implements ClienteGateway {

    private final RepositoryCliente repositoryCliente;
    private final ClienteMapper clienteMapper;

    @Override
    public Cliente save(Cliente cliente) throws Exception {
        try {
            var clienteDbEntity = clienteMapper.toDbEntity(cliente);

            clienteDbEntity = repositoryCliente.save(clienteDbEntity);

            return clienteMapper.fromDbEntityToEntity(clienteDbEntity);
        } catch (Exception exception) {
            throw new Exception(exception.getMessage(), exception);
        }
    }

    @Override
    public Cliente buscarClientePorCpf(String cpf) throws Exception {
        try {
            var clienteEntityOptional = repositoryCliente.findByCpf(cpf);

            if (clienteEntityOptional.isPresent())
                return clienteMapper.fromDbEntityToEntity(clienteEntityOptional.get());

            throw new ResourceNotFoundException(String.format(MensagemErroPadrao.CLIENTE_NAO_ENCONTRADO_CPF, cpf));

        } catch (ResourceNotFoundException ResourceNotFoundException) {
            throw new ResourceNotFoundException(ResourceNotFoundException.getLocalizedMessage());
        } catch (Exception exception) {
            throw new Exception(exception.getMessage(), exception);
        }
    }

    @Override
    public List<Cliente> list() throws Exception {
        try {
            var clienteEntities = repositoryCliente.findAll();

            return clienteEntities.stream()
                    .map(clienteMapper::fromDbEntityToEntity)
                    .collect(Collectors.toList());

        } catch (ResourceNotFoundException ResourceNotFoundException) {
            throw new ResourceNotFoundException(MensagemErroPadrao.LISTA_CLIENTE_VAZIA);
        } catch (Exception exception) {
            throw new Exception(MensagemErroPadrao.ERRO_GENERICO, exception);
        }
    }

    @Override
    public Cliente updateCliente(String cpf, Cliente cliente) throws Exception {

        try {
            var clienteEntityOptional = repositoryCliente.findByCpf(cpf);
            var clienteEntity = aplicaAlteracoes(cliente, clienteEntityOptional);
            return clienteMapper.fromDbEntityToEntity(clienteEntity);
        } catch (ResourceNotFoundException ResourceNotFoundException) {
            throw new ResourceNotFoundException(MensagemErroPadrao.CLIENTE_NAO_ENCONTRADO_ATUALIZACAO);
        } catch (Exception exception) {
            throw new Exception(MensagemErroPadrao.ERRO_GENERICO, exception);
        }
    }

    @Override
    public void delete(String cpf) throws Exception {
        try {
            repositoryCliente.deleteByCpf(cpf);
        } catch (Exception exception) {
            throw new Exception(MensagemErroPadrao.ERRO_GENERICO, exception);
        }
    }

    private ClienteEntity aplicaAlteracoes(Cliente cliente, Optional<ClienteEntity> clienteEntityOptional) {
        if (clienteEntityOptional.isEmpty())
            throw new ResourceNotFoundException(MensagemErroPadrao.CLIENTE_NAO_ENCONTRADO_ATUALIZACAO);

        var clienteEntity = clienteEntityOptional.get();
        clienteEntity = clienteMapper.toDbEntity(cliente);
        return repositoryCliente.save(clienteEntity);
    }
}

