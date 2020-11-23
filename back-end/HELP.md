# Back-end

### Subindo ambiente

#### Com Docker

Necessário:

- Docker 19+
- Docker Compose 1.25+

```shell script
## Executar comandos dentro desta pasta
# build
docker-compose build
# subir aplicação
docker-compose up -d
```

#### Sem Docker 

- Java 11+
- MariaDB

Você deve criar o banco de dados de acordo com os dados do arquivo `application.properties`.

```shell script
## Gerar build da aplicação
./mvnw clean package
## Executar aplicação após criar o banco de dados
./mvnw spring-boot:run
```

Caso preferir, você pode usar o Docker para executar somente o banco de dados conform abaixo:

```shell script
# Sobe serviço de banco de dados
docker-compose up -d db
```

-------

### API

Abaixo segue a estrutura em que os endpoints estarão disponíveis:

- Criar objeto: [GET] `/api/{objeto}`
- Consultar objeto por ID: [GET] `/api/{objeto}/{id}`
- Deletar objeto por ID: [DELETE] `/api/{objeto}/{id}`
- Atualizar objeto por ID: [PUT] `/api/{objeto}/{id}`
- Criar objeto: [POST] `/api/{objeto}`


#### Usuário `/user`

```json
{
  "login": "Fulano123",
  "password": "senha123",
  "mobileId": "MEU_MOBILE_UNICO"
} 
```

#### Dispositivo `/device`

```json
{
  "macAddress": "A4-CE-28-D7-8D-1D",
  "displayName": "Meu exemplo de dispositivo",
  "motorStatus": false
} 
```

#### Agendamento `/schedule`

```json
{
   "device": {
        "id": 1
    },
    "cron": "TIME_15:00",
    "description": "Regar às 3 da tarde"
} 
```

#### Dados `/data`

```json
{
    "device": {
        "id": 1
    },
    "luminosity": 50,
    "humidity": 10,
    "soil": 10,
    "waterHigh": false,
    "waterLow": false,
    "motorStatus": 1
}
```

#### Ações `/actions`
```json
{
  "schedule": {
    "id": 1
  },
  "data": "Lorem ipsum"
}
```

