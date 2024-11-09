package br.com.fiap.techchallenge.lanchonete.gateways;

import br.com.fiap.techchallenge.lanchonete.adapters.mappers.ClienteMapper;
import br.com.fiap.techchallenge.lanchonete.entities.Cliente;
import br.com.fiap.techchallenge.lanchonete.entities.dbEntities.ClienteEntity;
import br.com.fiap.techchallenge.lanchonete.exceptions.ResourceNotFoundException;
import br.com.fiap.techchallenge.lanchonete.interfaces.dbconnection.RepositoryCliente;
import br.com.fiap.techchallenge.lanchonete.mocks.ClienteEntityMock;
import br.com.fiap.techchallenge.lanchonete.mocks.ClienteMock;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class ClienteGatewayImplTest {

    @Mock
    private RepositoryCliente repositoryCliente;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteGatewayImpl clienteGateway;

    private final ClienteEntity clienteEntityMock = ClienteEntityMock.getClienteEntity();
    private final Cliente clienteMock = ClienteMock.getCliente();

    @Test
    @Description("Deve salvar um cliente no banco de dados")
    void deveSalvarClienteBancoDados() throws Exception {
        when(clienteMapper.toDbEntity(any())).thenReturn(clienteEntityMock);
        when(repositoryCliente.save(any())).thenReturn(clienteEntityMock);
        when(clienteMapper.fromDbEntityToEntity(any())).thenReturn(clienteMock);

        var result = clienteGateway.save(clienteMock);

        verify(repositoryCliente, times(1)).save(any());
        verify(clienteMapper, times(1)).toDbEntity(any());
        verify(clienteMapper, times(1)).fromDbEntityToEntity(any());
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.getNome(), clienteMock.getNome()),
                () -> assertEquals(result.getCpf(), clienteMock.getCpf()),
                () -> assertEquals(result.getEmail(), clienteMock.getEmail())
        );
    }

    @Test
    @Description("Deve retornar exception ao tentar salvar um cliente no banco de dados")
    void deveRetornarExceptionAoTentarSalvarUmClienteNoBancoDeDados() throws Exception {
        when(clienteMapper.toDbEntity(any())).thenReturn(clienteEntityMock);
        when(repositoryCliente.save(any())).thenThrow(new RuntimeException("erro"));

        assertThrows(Exception.class, () -> clienteGateway.save(clienteMock));

        verify(repositoryCliente, times(1)).save(any());
        verify(clienteMapper, times(1)).toDbEntity(any());
        verify(clienteMapper, times(0)).fromDbEntityToEntity(any());

    }

    @Test
    @Description("Deve buscar um cliente por CPF")
    void deveBuscarClientePorCpf() throws Exception {
        when(repositoryCliente.findByCpf(anyString())).thenReturn(Optional.ofNullable(clienteEntityMock));
        when(clienteMapper.fromDbEntityToEntity(any())).thenReturn(clienteMock);

        var result = clienteGateway.buscarClientePorCpf("01234567890");

        verify(repositoryCliente, times(1)).findByCpf(anyString());
        verify(clienteMapper, times(1)).fromDbEntityToEntity(any());

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.getNome(), clienteMock.getNome()),
                () -> assertEquals(result.getCpf(), clienteMock.getCpf()),
                () -> assertEquals(result.getEmail(), clienteMock.getEmail())
        );
    }

    @Test
    @Description("Deve retornar ResourceNotFoundException quando não for encontrado um cliente com o CPF informado")
    void deveRetornarResourceNotFoundExceptionQuandoNaoEncontrarClienteComCpfInformado() throws Exception {
        when(repositoryCliente.findByCpf(anyString())).thenThrow(new ResourceNotFoundException(""));

        assertThrows(ResourceNotFoundException.class, () -> clienteGateway.buscarClientePorCpf("01234567890"));
        verify(repositoryCliente, times(1)).findByCpf(anyString());
        verify(clienteMapper, times(0)).fromDbEntityToEntity(any());
    }

    @Test
    @Description("Deve retornar Exception ao tentar buscar um cliente com o CPF")
    void deveRetornarExceptionAoTentarBuscarClienteComCpf() throws Exception {
        when(repositoryCliente.findByCpf(anyString())).thenThrow(new Exception("erro"));

        assertThrows(Exception.class, () -> clienteGateway.buscarClientePorCpf("01234567890"));

        verify(repositoryCliente, times(1)).findByCpf(anyString());
        verify(clienteMapper, times(0)).fromDbEntityToEntity(any());
    }

    @Test
    @Description("Deve retornar uma lista de clientes")
    void deveRetornarUmaListaDeClientes() throws Exception {
        when(repositoryCliente.findAll()).thenReturn(List.of(clienteEntityMock));
        when(clienteMapper.fromDbEntityToEntity(any())).thenReturn(clienteMock);

        var result = clienteGateway.list();
        var item = result.get(0);

        verify(repositoryCliente, times(1)).findAll();
        verify(clienteMapper, times(1)).fromDbEntityToEntity(any());
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.size(), 1),
                () -> assertEquals(item.getNome(), clienteMock.getNome()),
                () -> assertEquals(item.getCpf(), clienteMock.getCpf()),
                () -> assertEquals(item.getEmail(), clienteMock.getEmail())
        );
    }

    @Test
    @Description("Deve retornar ResourceNotFoundException quando não houver nenhum cliente na lista")
    void deveRetornarResourceNotFoundExceptionQuandonaoHouverClienteNaLista() throws Exception {
        when(repositoryCliente.findAll()).thenThrow(new ResourceNotFoundException(""));

        assertThrows(ResourceNotFoundException.class, () -> clienteGateway.list());

        verify(repositoryCliente, times(1)).findAll();
        verify(clienteMapper, times(0)).fromDbEntityToEntity(any());
    }

    @Test
    @Description("Deve retornar Exception ao tentar buscar a lista de clientes")
    void deveRetornarExceptionAoTentarBuscarListaClientes() throws Exception {
        when(repositoryCliente.findAll()).thenThrow(new RuntimeException("erro"));

        assertThrows(Exception.class, () -> clienteGateway.list());
        verify(repositoryCliente, times(1)).findAll();
        verify(clienteMapper, times(0)).fromDbEntityToEntity(any());
    }

    @Test
    @Description("Deve alterar um cliente no banco de dados")
    void deveAlterarClienteBancoDados() throws Exception {
        when(repositoryCliente.findByCpf(anyString())).thenReturn(Optional.ofNullable(clienteEntityMock));
        when(repositoryCliente.save(any())).thenReturn(clienteEntityMock);
        when(clienteMapper.fromDbEntityToEntity(any())).thenReturn(clienteMock);
        when(clienteMapper.toDbEntity(any())).thenReturn(clienteEntityMock);

        var result = clienteGateway.updateCliente("1", clienteMock);

        verify(repositoryCliente, times(1)).findByCpf(any());
        verify(repositoryCliente, times(1)).save(any());
        verify(clienteMapper, times(1)).fromDbEntityToEntity(any());
        verify(clienteMapper, times(1)).toDbEntity(any());
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.getNome(), clienteMock.getNome()),
                () -> assertEquals(result.getCpf(), clienteMock.getCpf()),
                () -> assertEquals(result.getEmail(), clienteMock.getEmail())
        );
    }

    @Test
    @Description("Deve retornar ResourceNotFoundException ao tentar alterar um cliente no banco de dados")
    void deveRetornarResourceNotFoundExceptionAoTentarAlterarClienteBancoDados() throws Exception {
        when(repositoryCliente.findByCpf(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteGateway.updateCliente("1", clienteMock));

        verify(repositoryCliente, times(1)).findByCpf(any());
        verify(repositoryCliente, times(0)).save(any());
        verify(clienteMapper, times(0)).fromDbEntityToEntity(any());
        verify(clienteMapper, times(0)).toDbEntity(any());
    }

    @Test
    @Description("Deve retornar exception ao tentar alterar um cliente no banco de dados")
    void deveRetornarExceptionAoTentarAlterarUmClienteNoBancoDeDados() throws Exception {
        when(repositoryCliente.findByCpf(anyString())).thenThrow(new RuntimeException());

        assertThrows(Exception.class, () -> clienteGateway.updateCliente("1", clienteMock));

        verify(repositoryCliente, times(1)).findByCpf(any());
        verify(repositoryCliente, times(0)).save(any());
        verify(clienteMapper, times(0)).fromDbEntityToEntity(any());
        verify(clienteMapper, times(0)).toDbEntity(any());
    }

    @Test
    @Description("Deve retornar Exception ao tentar deletar um cliente do banco de dados")
    void deveRetornarExceptionAoTentarDeletarUmClienteDoBancoDeDados() {
        doThrow(new RuntimeException()).when(repositoryCliente).deleteByCpf(anyString());

        assertThrows(Exception.class, () -> clienteGateway.delete("1"));
    }

    @Test
    @Description("Deve deletar um cliente")
    void deveDeletarUmCliente() throws Exception {
        clienteGateway.delete("1");
        verify(repositoryCliente, times(1)).deleteByCpf(anyString());
    }

    @Test
    @Description("Deve retornar ResourceNotFoundException ao buscar um cliente pelo CPF com optional")
    void deveRetornarResourceNotFoundExceptionAoBuscarClientePorCpfComOptional() throws Exception {
        when(repositoryCliente.findByCpf(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteGateway.buscarClientePorCpf("01234567890"));
        verify(repositoryCliente, times(1)).findByCpf(anyString());
        verify(clienteMapper, times(0)).fromDbEntityToEntity(any());
    }
}
