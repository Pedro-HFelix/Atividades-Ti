# REPOSITÓRIO PARA TAREFAS DE TI2

Este repositório contém atividades e exemplos de código para a disciplina TI2. As atividades estão divididas em duas pastas:

### PASTA - `ATIVIDADE1_TI2`

Contém a primeira atividade.

### PASTA - `ATIVIDADE2_TI2`

Contém a segunda atividade, que envolve a manipulação de livros em um banco de dados PostgreSQL.

### Configuração do Banco de Dados

#### Criar a Tabela

Para testar a aplicação, você precisa criar a tabela `books` no seu banco de dados PostgreSQL. Execute o seguinte comando SQL:

```sql
CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL
);
```
Configuração do BookDAO
Atualize as variáveis de conexão no código BookDAO para corresponder às suas configurações de banco de dados PostgreSQL:
```java
private final String url = "jdbc:postgresql://localhost:5432/postgres"; // URL do banco de dados
private final String user = "postgres"; // Seu usuário do PostgreSQL
private final String password = "admin"; // Sua senha do PostgreSQL
```