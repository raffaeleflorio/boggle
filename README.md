# Boggle: Object oriented boggle with HTTP interface

[![Licensed under Apache-2.0](https://img.shields.io/github/license/raffaeleflorio/boggle)](https://raw.githubusercontent.com/raffaeleflorio/boggle/main/LICENSE)
[![CircleCI build status](https://img.shields.io/circleci/build/github/raffaeleflorio/boggle/main?label=circleci)](https://circleci.com/gh/raffaeleflorio/boggle/)
[![Codecov reports](https://img.shields.io/codecov/c/github/raffaeleflorio/boggle)](https://codecov.io/gh/raffaeleflorio/boggle)
[![Hits-of-Code](https://hitsofcode.com/github/raffaeleflorio/boggle?branch=main)](https://hitsofcode.com/github/raffaeleflorio/boggle/view?branch=main)
[![Swagger UI](https://img.shields.io/swagger/valid/3.0?specUrl=https%3A%2F%2Fraw.githubusercontent.com%2Fraffaeleflorio%2Fboggle%2Fmain%2Fsrc%2Fmain%2Fresources%2Fopenapi.yml)](https://petstore.swagger.io/?url=https://raw.githubusercontent.com/raffaeleflorio/boggle/main/src/main/resources/openapi.yml)

A reactive object oriented implementation of the [Boggle game](https://en.wikipedia.org/wiki/Boggle) with a HTTP
interface. The latter is implemented with [Vert.x](https://vertx.io).

# How to run

You should first build the uber jar, then you can run it with java. Port and interface are mandatory. For example to run
on the `127.0.0.1` interface on the `8080` port:

```shell
$ ./mvnw clean package
$ java -jar target/boggle-1.0.0-SNAPSHOT.jar 8080 127.0.0.1
```

# Javadoc

The project is documented through javadoc. To generate it in `target/site/apidocs`:

```shell
$ ./mvnw clean javadoc:javadoc
```

# Code coverage report

The project is covered by tests. To generate code coverage report in `target/site/jacoco`:
```shell
$ ./mvnw clean test jacoco:report
```
