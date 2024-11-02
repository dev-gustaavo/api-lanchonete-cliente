package br.com.fiap.techchallenge.lanchonete.entities.dbEntities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "clientes")
@Getter
@Setter
@Accessors(chain = true)
public class ClienteEntity implements Serializable {

    @Id
    private String id;
    private String nome;
    private String email;
    private String cpf;
}
