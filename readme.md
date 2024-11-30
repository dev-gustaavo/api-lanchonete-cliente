# Tech Challenge - API Cliente Lanchonete


API resopnsável por gerenciar o cadastro de clientes da Lanchonete.

# Pré-requisitos
1. Docker
   1. Para instalação [clique aqui](https://www.docker.com/get-started/)
2. Docker compose
   1. Para instalação [clique aqui](https://docs.docker.com/compose/install/)
3. Docker Desktop
   1. Para instalação [clique aqui](https://www.docker.com/products/docker-desktop/)
4. Habilitar o Kubernetes através do menu de configuração do Docker Desktop
   1. Após abrir o Docker Desktop, clique na engrnagem no canto superior direito;
   2. Vá em "Kubernetes"
   3. Habilite o Kubernetes selecionando o check box "Enable Kubernetes"

# Para executar o projeto:
1. Acesse via terminal a pasta do projeto
2. Execute em ordem os comandos abaixo: 
```bash
kubectl apply -f kubernetes --recursive  # você vai criar todos os recursos kubernetes que estão dentro da pasta 'kubernetes/'
```

### Obs.: Caso esteja utilizando o minikube para rodar seu cluster local, é necessário executar o campo abaixo:
```bash
kubectl get svc # neste comando você listará todas as services. Localize a service do app (svc-lanchonete-app-cliente)
kubectl port-forward svc-lanchonete-app-cliente 8080:80 # neste comando você vai direcionar todas as chamadas da porta 8080 para a porta 80 do cluster
```

#### Após os passos acima, a API estará funcionando e será possível realizar as operações, conforme descrito abaixo.

# Passo a passo funcional da API

### **Operações Disponíveis nesta API**
![POST](https://img.shields.io/badge/POST-green?style=for-the-badge)  
**Rota:** `/cliente`  
**Descrição:** Realiza o cadastro de um cliente.  
**Body:**

```json
{
   "nome": "string",
   "email": "string",
   "cpf": "string"
}
```

![GET](https://img.shields.io/badge/GET-blue?style=for-the-badge)  
**Rota:** `/cliente`  
**Descrição:** Realiza a busca de uma lista de clientes.  

![GET](https://img.shields.io/badge/GET-blue?style=for-the-badge)  
**Rota:** `/cliente/{cpf}`  
**Descrição:** Realiza a busca de um cliente por CPF.

![PUT](https://img.shields.io/badge/PUT-orange?style=for-the-badge)  
**Rota:** `/cliente/{cpf}`  
**Descrição:** Realiza a alteração de um cliente por CPF.

![DELETE](https://img.shields.io/badge/DELTE-red?style=for-the-badge)  
**Rota:** `/cliente/{cpf}`  
**Descrição:** Realiza a exclusão de um cliente por CPF.

# Documentações

Link da documentação com o desenho do DDD: [Clique aqui para acessar o Miro](https://miro.com/app/board/uXjVKHPTdLg=/?share_link_id=544608334788)
<br>
Após subir a aplicação, para acessar o Swagger [Clique aqui](http://localhost:8080/swagger-ui/index.html)

# Relatório de cobertura de teste
![relatorio-teste.png](relatorio-teste.png)

# Desenho de arquitetura do projeto

![](arquitetura/tech-challenge-fiap-fase-2.png)