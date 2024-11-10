package br.com.fiap.techchallenge.lanchonete.interfaces.dbconnection;

import br.com.fiap.techchallenge.lanchonete.entities.dbEntities.ClienteEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryCliente extends MongoRepository<ClienteEntity, String> {

    Optional<ClienteEntity> findByCpf(String cpf) throws Exception;

    void deleteByCpf(String cpf);
}
