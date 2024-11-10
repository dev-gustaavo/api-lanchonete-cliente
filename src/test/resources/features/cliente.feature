# language: pt

  Funcionalidade:

    Cenario: Cadastrar cliente
      Quando enviar os dados de cadastro
      Entao o cliente deve ser criado com sucesso

    Cenario: Erro ao cadastrar cliente já existente na base
      Dado que um cliente já está cadastrado
      Quando enviar os dados de cadastro do mesmo cliente
      Entao um erro de cliente já cadastrado deve ser retornado

    Cenario: Buscar cliente
      Dado que um cliente já foi cadastro
      Quando efetuar a busca do cliente
      Entao o cliente é retornado com sucesso

    Cenario: Alterar cliente
      Dado que um cliente está cadastrado
      Quando efetuar a requisição para alterar o cliente
      Entao o cliente deve ser atualizado com sucesso

    Cenario: Remover cliente
      Dado que um cliente já foi cadastrado
      Quando efetuar a requisição de exclusão do cliente
      Entao o cliente deve ser removido com sucesso