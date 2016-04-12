# Cadastro-api
#### API RESTful para cadastro

Este é um aplicativo back-end que possui uma API RESTful. As requisições funcionam com JSON (entrada e saída). 

**Tecnologias**
- Java 8, Maven,
- Hibernate, HSQLDB
- JUnit, Fixture Factory, MockMvc
- Spring Boot, Spring Security, Spring Data Rest, Spring hateoas
- JWT
- Continuous Integration - Heroku integrado com o GitHub

É possível acessar a aplicação rodando no Heroku em:
https://cadastro-api.herokuapp.com

Para rodar a aplicação basta fazer o clone dela e rodar o seguinte comando:
```
mvn spring-boot:run
```

## Registro
Ao iniciar, é possível realizar um cadastro:
##### Exemplo: #####
```
URL: /v1/usuarios
método: POST
```
##### Body: #####
```json
{
   "nome":"andre",
   "email":"andre@mail.com",
   "senha":"s3nh@",
   "telefones":[
      {
         "numero":"123456789",
         "ddd":"11"
      },
      {
         "numero":"987654321",
         "ddd":"47"
      }
   ]
}
```
Isto retornará um JSON com o usuário e o token que dará acesso ao sistema.

*Obs.: O Token é válido por 30 minutos.*

![Consulta de usuário](/screenshots/1-cadastro.png?raw=true "Consulta de usuário")


## Consulta de usuário
Com o token, é possível fazer a consulta do usuário através da seguinte URL:
```
URL: /v1/usuarios
método: GET
```
E no header da requisição é necessário informar o key: `Authorization`
No value é necessário informar `Bearer {token}`
Isto retornará os dados atualizados do usuário

![Registro](/screenshots/2-consulta.png?raw=true "Registro")

## Login
Caso não possua o token e já tenha feito o cadastro, é possível realizar o login através da seguinte URL:
##### Exemplo: #####
```
URL: /auth
método: POST
```
##### Body: #####
```json
{
   "email":"andre@mail.com",
   "senha":"s3nh@"
}
```
Isto retornará um JSON com o usuário e o token que dará acesso ao sistema.

![Login](/screenshots/3-login.png?raw=true "Login")

**As mensagens de erro seguem o seguinte padrão:**
```json
{ "mensagem": "mensagem de erro" }
```
