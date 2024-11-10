package br.com.fiap.techchallenge.lanchonete.bdd;

import br.com.fiap.techchallenge.lanchonete.dtos.inbound.ClienteDTO;
import br.com.fiap.techchallenge.lanchonete.entities.Cliente;
import br.com.fiap.techchallenge.lanchonete.entities.ErroResponse;
import br.com.fiap.techchallenge.lanchonete.mocks.ClienteDTOMock;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.Assert.assertEquals;

public class ClienteStepDefinition {

    private final String ENDPOINT_API = "http://localhost:8282/cliente";
    private Response response;
    private ClienteDTO clienteCadastradoMock;
    private Cliente clienteCadastrado;

    @Quando("enviar os dados de cadastro")
    public Cliente enviarOsDadosDeCadastro() {
        var clienteRequest = ClienteDTOMock.getClienteDTO();

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteRequest)
                .when().post(ENDPOINT_API);
        return response.then().extract().as(Cliente.class);
    }

    @Entao("o cliente deve ser criado com sucesso")
    public void oClienteDeveSerCriadoComSucesso() {
        response
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Dado("que um cliente já está cadastrado")
    public void queUmClienteJaEstaCadastrado() {
        clienteCadastradoMock = ClienteDTOMock.getClienteDTO();
    }

    @Quando("enviar os dados de cadastro do mesmo cliente")
    public void enviarOsDadosDeCadastroDoMesmoCliente() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteCadastradoMock)
                .when().post(ENDPOINT_API);
    }

    @Entao("um erro de cliente já cadastrado deve ser retornado")
    public void umErroDeClienteJaCadastradoDeveSerRetornado() {
        var erroResponse = response.then().extract().as(ErroResponse.class);
        var mensagemErro = "Cliente já cadastrado.";
        var statusErro = 500;
        assertEquals(mensagemErro, erroResponse.getMessage());
        assertEquals(statusErro, erroResponse.getStatus());
    }

    @Dado("que um cliente já foi cadastro")
    public void queUmClienteJaFoiCadastro() {
        clienteCadastradoMock = ClienteDTOMock.getClienteDTO();
    }

    @Quando("efetuar a busca do cliente")
    public void efetuarABuscaDoCliente() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(ENDPOINT_API + "/{cpf}", clienteCadastradoMock.getCpf());
    }

    @Entao("o cliente é retornado com sucesso")
    public void oClienteERetornadoComSucesso() {
        response.then().statusCode(HttpStatus.OK.value())
                .body(
                        matchesJsonSchemaInClasspath("./schemas/ClienteResponse.json")
                );
    }

    @Dado("que um cliente está cadastrado")
    public void queUmClienteEstáCadastrado() {
        clienteCadastradoMock = ClienteDTOMock.getClienteDTO();
    }

    @Quando("efetuar a requisição para alterar o cliente")
    public void efetuarARequisiçãoParaAlterarOCliente() {
        var clienteAlterado = clienteCadastradoMock.setNome("Nome alterado");
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteAlterado)
                .when().put(ENDPOINT_API + "/{cpf}", clienteCadastradoMock.getCpf());
    }

    @Entao("o cliente deve ser atualizado com sucesso")
    public void oClienteDeveSerAtualizadoComSucesso() {
        var cliente = response.then().extract().as(Cliente.class);
        assertEquals(cliente.getNome(), "Nome alterado");
    }

    @Dado("que um cliente já foi cadastrado")
    public void queUmClienteJaFoiCadastrado() {
        clienteCadastradoMock = ClienteDTOMock.getClienteDTO();
    }

    @Quando("efetuar a requisição de exclusão do cliente")
    public void efetuarARequisicaoDeExclusaoDoCliente() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(ENDPOINT_API + "/{cpf}", clienteCadastradoMock.getCpf());
    }

    @Entao("o cliente deve ser removido com sucesso")
    public void oClienteDeveSerRemovidoComSucesso() {
        response.then().statusCode(HttpStatus.OK.value());
    }
}
