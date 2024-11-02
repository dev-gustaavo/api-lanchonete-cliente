package br.com.fiap.techchallenge.lanchonete.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErroResponse {
    private int status;
    private String message;
}
