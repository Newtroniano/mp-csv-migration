# Desafio  - Multi Portal

## Descrição

Projeto desenvolvido para o desafio do processo seletivo da Multi Portal. O objetivo é importar dados  de um arquivo CSV, corrigir as informações, processar e armazenar em um banco de dados PostgreSQL, além de exibir estatísticas e permitir exportação dos dados corrigidos.

---

## Funcionalidades

- Tela de login simples com usuário e senha predefinidos, usando Spring Security para gerenciar a sessão.
- Upload de arquivo CSV pela interface web.
- Processamento dos dados em memória:
    - Ordenação dos registros pelo nome.
    - Contagem de homens e mulheres.
    - Correção da data de nascimento com base na idade informada.
    - Cálculo da média de idade por gênero.
- Persistência dos dados no banco PostgreSQL via JPA/Hibernate.
- Exibição dos dados processados em tela.
- Exportação dos dados corrigidos em CSV através de botão na interface.

---

## Tecnologias utilizadas

- Java 17+
- Spring Boot
- Spring MVC
- Spring Security
- Thymeleaf
- Flyway (para controle de versão do banco)
- PostgreSQL
- Docker

---

## Login padrão

| Usuário | Senha  |
|---------|---------|
| admin   | 123456  |

---

## Como executar

### 1. Clonar o projeto

```bash
git clone https://github.com/Newtroniano/mp-csv-migration.git
cd seuprojeto
```

### 2. Rodando via Docker
Para facilitar a execução do projeto, recomenda-se rodar a aplicação via Docker.

Passos:

1. Instale o Docker e o Docker Compose na sua máquina, caso ainda não tenha.

2. Na pasta raiz do projeto, execute o comando:

```bash
docker-compose up --build
```
3. Após a construção e inicialização do container, a aplicação estará rodando.

4. Acesse a aplicação no navegador pelo endereço:

```bash
http://localhost:8080
```


### 3. Rodando sem Docker
Caso não utilize Docker, é necessário:

1. Ter o PostgreSQL instalado e rodando na sua máquina.

2. Criar o banco de dados chamado csv_reader manualmente.


