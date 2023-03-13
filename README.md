# Spring Exception Handler
Biblioteca para padronização de tratamento de exceções de APIs Rest construídas com Spring Boot

## Tópicos
- [Instalação com Maven](#instalação-com-maven)
- [Deploy manual](#deploy-manual)
- [Configuração](#configuração)

## Instalação com Maven
Crie o arquivo de configuração do maven ou inclua o repositório e o servidor no arquivo já existente:
```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" 
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
  <servers>
    <server>
      <id>github</id>
      <username>${server.github.username}</username>
      <password>${server.github.password}</password>
    </server>
  </servers>
   
  <activeProfiles>
    <activeProfile>general</activeProfile>
  </activeProfiles>

  <profiles>
    <profile>
      <id>general</id>
      <repositories>
        <repository>
          <id>central</id>
          <url>https://repo1.maven.org/maven2</url>
        </repository>
        <repository>
          <id>github</id>
          <url>https://maven.pkg.github.com/felipemenezesdm/spring-exception-handler</url>
        </repository>
      </repositories>
    </profile>
  </profiles>
</settings>
```

Inclua a dependência no arquivo pom:
```xml
<dependency>
  <groupId>br.com.felipemenezesdm</groupId>
  <artifactId>spring-exception-handler</artifactId>
  <version>1.0.0</version>
</dependency>
```

Execute com comando abaixo para download de dependências:
```
mvn install
```

## Deploy manual
O deploy da biblioteca é realizado automaticamente sempre que houver a criação de uma nova tag de versão. Entretanto, se for necessário realizar seu deploy manual, é preciso seguir os passos abaixo:

1. No _settings.xml_, confirmar que o servidor do GitHub está configurado:
    ```xml
      <servers>
        <server>
          <id>github</id>
          <username>${server.github.username}</username>
          <password>${server.github.password}</password>
        </server>
      </servers>
    ```
2. Executar o comando abaixo, substuindo os parâmetros por seus respectivos valores:
    ```
    mvn deploy -s settings.xml -Dserver.github.username=USERNAME -Dserver.github.password=PASSWORD
    ```

## Configuração
Por padrão, o Spring Boot desabilita a opção de tratamento de exceções que não possuem um _handler_, como é o caso de exceções _**404 NOT FOUND**_. Para que todas as exceções sejam tratadas de forma padronizada pela biblioteca, é necessário adicionar a seguinte configuração para a aplicação:
```yaml
spring:
  mvc:
    throw-exception-if-no-handler-found: true
  web:
    resources:
      add-mappings: false
```

Além disso, é possível também customizar as mensagens de erro usando as seguintes propriedades:
```yaml
app:
  exceptions:
    400: Não foi possível validar a integridade dos dados da requisição
    401: Não há permissões suficientes para validar validar esta requisição
    403: Não há permissões suficientes para completar esta requisição
    404: Não foi possível localizar o serviço solicitado
    405: Não foi possível localizar o método solicitado
    406: Não foi possível localizar um retorno válido para esta requisição
    408: O tempo limite de processamento para a solicitação foi atingido
    422: A entidade solicitada não pôde ser processada
    500: Um erro interno no servidor não permitiu completar a solicitação
    501: O servidor não suporta o método solicitado
    502: Ocorreu um erro interno durante a conexão com o gateway
    503: O servidor não está disponível para esta solicitação
    504: O tempo limite de processamento foi atingido durante conexão com o gateway
```