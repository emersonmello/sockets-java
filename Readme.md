[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

# Exemplo de sockets TCP com a API Java I/O

Java possui diferentes APIs para lidar com operações de I/O:. Aqui é apresentado um exemplo com a API Java I/O, que segue modelo bloqueante.

A API Java NIO.2 permite desenvolver aplicações com comunicação assíncrona, porém seu entendimento demanda mais tempo. Veja mais detalhes na [documentação oficial da Oracle](https://docs.oracle.com/javase/8/docs/technotes/guides/io/index.html). 

No arquivo [build.gradle](app/build.gradle) foram criadas tarefas para permitir executar o servidor e o cliente diretamente com o [gradle](https://www.gradle.org).

## Como executar o servidor

```bash
# No Linux ou macOS
./gradlew servidor

# No Windows
gradle.bat servidor
```

## Como executar o cliente 

```bash
# Tentará conectar na porta 12345 na localhost
./gradlew cliente

# Passando o IP e porta do servidor como argumentos de linha de comando
./gradlew cliente --args "localhost 12345"
```