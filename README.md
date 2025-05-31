# E-commerce Challenge

Um sistema completo de e-commerce desenvolvido em Spring Boot, oferecendo funcionalidades robustas para gestão de produtos, carrinho de compras, processamento de pedidos e relatórios administrativos.

## Sobre o Projeto

Este projeto foi desenvolvido como parte de um desafio técnico, implementando um sistema de e-commerce moderno com foco em segurança, escalabilidade e experiência do usuário. A aplicação oferece uma API RESTful completa para operações de comércio eletrônico, desde o cadastro de usuários até a finalização de compras e geração de relatórios gerenciais.

## Tecnologias Utilizadas

- **Java 21** - Linguagem de programação principal
- **Spring Boot 3.4.5** - Framework principal para desenvolvimento da API
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **PostgreSQL** - Banco de dados relacional
- **JWT (JSON Web Tokens)** - Autenticação stateless
- **Maven** - Gerenciamento de dependências
- **Lombok** - Redução de código boilerplate
- **Bean Validation** - Validação de dados de entrada

## Funcionalidades Principais

### Autenticação e Autorização
- Sistema de login com JWT
- Dois níveis de acesso: Administrador e Cliente
- Redefinição de senha via email
- Proteção de endpoints baseada em roles

### Gestão de Produtos
- CRUD completo para administradores
- Visualização pública para clientes
- Controle de estoque automático
- Sistema de ativação/inativação de produtos
- Validações de negócio (preços positivos, estoque mínimo)

### Carrinho de Compras
- Adição e remoção de itens
- Edição de quantidades
- Persistência entre sessões
- Validação de disponibilidade de estoque

### Processamento de Pedidos
- Finalização de compras
- Cálculo automático de totais
- Atualização de estoque em tempo real
- Histórico de pedidos

### Relatórios Gerenciais
- Total de vendas por período
- Produtos mais vendidos
- Clientes que mais compraram
- Produtos com baixo estoque
- Análises de performance de vendas

## Estrutura do Projeto

```
src/main/java/com/compass/ecommercechallenge/
├── config/          # Configurações de segurança e inicialização
├── controller/      # Endpoints da API REST
├── dto/            # Objetos de transferência de dados
├── entity/         # Entidades JPA
├── exception/      # Tratamento de exceções
├── repository/     # Interfaces de acesso a dados
├── service/        # Lógica de negócio
└── utils/          # Classes utilitárias
```

## Configuração e Execução

### Pré-requisitos
- Java 21 ou superior
- PostgreSQL 12 ou superior
- Maven 3.6 ou superior

### Configuração do Banco de Dados
1. Crie um banco de dados PostgreSQL chamado `ecommerce`
2. Configure as credenciais no arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### Configuração de Email (Opcional)
Para funcionalidade de redefinição de senha:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu_email@gmail.com
spring.mail.password=sua_senha_app
```

### Executando a Aplicação

```bash
# Clone o repositório
git clone [url-do-repositorio]

# Navegue até o diretório
cd EcommerceChallenge

# Execute a aplicação
./mvnw spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

## Endpoints Principais

### Autenticação
- `POST /api/v1/login` - Login de usuário
- `POST /api/v1/createUser` - Cadastro de cliente
- `POST /api/v1/auth/forgot-password` - Solicitar redefinição de senha
- `POST /api/v1/auth/reset-password` - Confirmar nova senha

### Produtos
- `GET /api/v1/products` - Listar produtos (público)
- `GET /api/v1/product/{id}` - Detalhes do produto
- `POST /api/v1/createProduct` - Criar produto (admin)
- `POST /api/v1/updateProduct/{id}` - Atualizar produto (admin)
- `POST /api/v1/deleteProduct/{id}` - Remover produto (admin)

### Carrinho
- `GET /api/v1/cart` - Visualizar carrinho
- `POST /api/v1/addItemsCart` - Adicionar itens
- `POST /api/v1/deleteItemCart/{id}` - Remover item

### Pedidos
- `POST /api/v1/finishOrder` - Finalizar compra

### Relatórios (Admin)
- `GET /api/v1/report/totalSales` - Total de vendas por período
- `GET /api/v1/report/top-product` - Produtos mais vendidos
- `GET /api/v1/report/top-customers` - Melhores clientes
- `GET /api/v1/report/low-stock` - Produtos com baixo estoque

## Usuário Administrador Padrão

O sistema cria automaticamente um usuário administrador na primeira execução:

- **Email:** admin@admin.com
- **Senha:** senha123

## Validações e Regras de Negócio

- Produtos não podem ter preços negativos
- Estoque é validado antes da finalização de compras
- Produtos sem estoque são automaticamente inativados
- Emails devem ser únicos no sistema
- Senhas são criptografadas com BCrypt
- Tokens JWT têm expiração configurável