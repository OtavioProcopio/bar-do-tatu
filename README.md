# Bar do Tatu

Bar do Tatu é uma aplicação backend desenvolvida em Java utilizando o framework Spring Boot. Ela oferece funcionalidades de CRUD para auxiliar no controle e gestão de um bar.

## Funcionalidades

- Gerenciamento de produtos: cadastro, atualização, listagem e remoção de itens do menu.

## Implementaçoes Futuras
- Controle de pedidos: criação, atualização, listagem e cancelamento de pedidos dos clientes.
- Gestão de estoque: monitoramento e atualização das quantidades de produtos disponíveis.
- Relatórios: geração de relatórios sobre vendas e produtos mais consumidos.

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3.4.1
- Maven 
- Banco de Dados H2 (pode ser configurado para outros SGBDs)
- Docker (para containerização da aplicação)

## Pré-requisitos

- Java 21 ou superior instalado
- Maven 3.6.3 ou superior instalado
- Docker (opcional, caso deseje utilizar contêineres)

## Como Executar a Aplicação

1. **Clonar o repositório:**

   ```bash
   git clone https://github.com/OtavioProcopio/bar-do-tatu.git
   cd bar-do-tatu
   ```

2. **Construir o projeto com Maven:**

   ```bash
   mvn clean install
   ```

3. **Executar a aplicação:**

   ```bash
   mvn spring-boot:run
   ```

   A aplicação estará disponível em `http://localhost:8080`.

## Utilizando Docker

Para executar a aplicação em um contêiner Docker:

1. **Construir a imagem Docker:**

   ```bash
   docker build -t bar-do-tatu .
   ```

2. **Executar o contêiner:**

   ```bash
   docker run -p 8080:8080 bar-do-tatu
   ```

   A aplicação estará disponível em `http://localhost:8080`.

## Endpoints Principais

- **Produtos:**
  - `GET /produtos` - Lista todos os produtos
  - `POST /produtos` - Adiciona um novo produto
  - `PUT /produtos/{id}` - Atualiza um produto existente
  - `DELETE /produtos/{id}` - Remove um produto

## Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e enviar pull requests.