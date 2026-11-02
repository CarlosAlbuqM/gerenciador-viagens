# Gerenciador de Viagens

API REST para cadastrar e consultar viagens, desenvolvida em Java com Spring Boot. Projeto de estudo focado em construção de APIs, validação de dados, tratamento de erros e testes automatizados.

## Tecnologias

- Java 21
- Spring Boot 4 (Web, Data JPA, Validation)
- H2 Database (banco em memória)
- Maven
- JUnit 5 e MockMvc (testes de integração)
- Postman (testes manuais e automatizados da API)

## Funcionalidades

- CRUD completo de viagens (criar, listar, buscar, atualizar e remover)
- Validação de campos obrigatórios e de tamanho
- Regra de negócio: a data de retorno não pode ser anterior à data de partida
- Tratamento centralizado de erros, com status HTTP adequados (400 e 404)
- Respostas padronizadas no formato `data` / `errors`

## Arquitetura

O projeto é organizado em camadas, cada uma com uma responsabilidade:

```
Requisição → Controller → Service → Repository → H2
```

| Pacote | Responsabilidade |
|---|---|
| `controller` | Recebe as requisições HTTP e devolve as respostas |
| `services` | Regras de negócio |
| `repositories` | Acesso ao banco de dados (Spring Data JPA) |
| `entities` | Mapeamento das tabelas |
| `dtos` | Dados recebidos nas requisições, com validações |
| `responses` | Formato padrão das respostas |

## Como executar

Pré-requisito: Java 21 instalado.

```bash
git clone https://github.com/CarlosAlbuqM/gerenciador-viagens.git
cd gerenciador-viagens
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`.

O console do H2 fica disponível em `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:viagensdb`, usuário `sa`, senha em branco).

## Endpoints

| Método | Rota | Descrição | Sucesso |
|---|---|---|---|
| POST | `/api/viagens` | Cadastra uma viagem | 201 Created |
| GET | `/api/viagens` | Lista todas as viagens | 200 OK |
| GET | `/api/viagens/{id}` | Busca uma viagem pelo id | 200 OK |
| PUT | `/api/viagens/{id}` | Atualiza uma viagem | 200 OK |
| DELETE | `/api/viagens/{id}` | Remove uma viagem | 204 No Content |

### Exemplo de requisição

`POST /api/viagens`

```json
{
    "localDeDestino": "Itapema - SC",
    "dataPartida": "2018-01-10",
    "dataRetorno": "2018-02-20",
    "acompanhante": "Camila"
}
```

### Exemplo de resposta (201 Created)

```json
{
    "data": {
        "id": 1,
        "localDeDestino": "Itapema - SC",
        "dataPartida": "2018-01-10",
        "dataRetorno": "2018-02-20",
        "acompanhante": "Camila"
    },
    "errors": []
}
```

### Exemplo de erro (400 Bad Request)

```json
{
    "data": null,
    "errors": [
        "Local de Destino deve estar entre 3 e 40 caracteres"
    ]
}
```

## Regras de validação

- `localDeDestino`: obrigatório, entre 3 e 40 caracteres
- `dataPartida`: obrigatória, no formato `yyyy-MM-dd`
- `dataRetorno`: opcional; se informada, não pode ser anterior à data de partida
- `acompanhante`: opcional

## Testes

### Testes automatizados em Java

```bash
./mvnw test
```

Os testes de integração (JUnit + MockMvc) cobrem:

- Cadastro com dados válidos (201)
- Validação de tamanho do local de destino (400)
- Regra de negócio das datas (400)
- Busca de viagem inexistente (404)

### Testes no Postman

A collection do Postman testa o ciclo completo de uma viagem: cadastrar, atualizar, remover e confirmar a remoção, encadeando as requisições pelo id retornado no cadastro.

## Referência

Projeto baseado no artigo "Como construir e testar uma API em Java utilizando o Postman", de Antonio Montanha (assert(QA), 2018), atualizado para Java 21, Spring Boot 4 e VS Code.

## Autor

Carlos Phelipe Mesquita de Albuquerque, estudante de Sistemas de Informação no CESMAC.