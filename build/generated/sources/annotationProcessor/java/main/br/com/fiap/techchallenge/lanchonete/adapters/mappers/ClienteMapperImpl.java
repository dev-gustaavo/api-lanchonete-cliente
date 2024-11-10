package br.com.fiap.techchallenge.lanchonete.adapters.mappers;

import br.com.fiap.techchallenge.lanchonete.dtos.inbound.ClienteDTO;
import br.com.fiap.techchallenge.lanchonete.entities.Cliente;
import br.com.fiap.techchallenge.lanchonete.entities.dbEntities.ClienteEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-09T17:37:25-0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.7.jar, environment: Java 17.0.12 (Ubuntu)"
)
@Component
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public Cliente toEntity(ClienteDTO clienteRequest) {
        if ( clienteRequest == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setNome( clienteRequest.getNome() );
        cliente.setEmail( clienteRequest.getEmail() );
        cliente.setCpf( clienteRequest.getCpf() );

        return cliente;
    }

    @Override
    public ClienteEntity toDbEntity(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }

        ClienteEntity clienteEntity = new ClienteEntity();

        clienteEntity.setNome( cliente.getNome() );
        clienteEntity.setEmail( cliente.getEmail() );
        clienteEntity.setCpf( cliente.getCpf() );

        return clienteEntity;
    }

    @Override
    public Cliente fromDbEntityToEntity(ClienteEntity clienteEntity) {
        if ( clienteEntity == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setNome( clienteEntity.getNome() );
        cliente.setEmail( clienteEntity.getEmail() );
        cliente.setCpf( clienteEntity.getCpf() );

        return cliente;
    }
}
