# Using R2dbc DatabaseClient

### Configure Spring r2dbc DatabaseClient
Declare `ConnectionFactory` similar to `DataSource` beans of the Jdbc support.

```java 
  @Bean
  public ConnectionFactory connectionFactory() {
    return new PostgresqlConnectionFactory(
        PostgresqlConnectionConfiguration.builder()
            .host("localhost")
            .database("demo")
            .username("postgres")
            .password("password")
            .build()
    );
  }
```

Declare `DatabaseClient` bean that uses existing `ConnectionFactory`.

```java 
  @Bean
  DatabaseClient databaseClient(ConnectionFactory connectionFactory) {
    return DatabaseClient.builder()
        .connectionFactory(connectionFactory)
        .build();
  }
```

Define `TransactionalOperator` bean for programmatic transaction control.
This is similar to `TransactionTemplate` in traditional jdbc.

```java 
  @Bean
  ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
    return new R2dbcTransactionManager(connectionFactory);
  }

  @Bean
  TransactionalOperator transactionalOperator(ReactiveTransactionManager transactionManager) {
    return TransactionalOperator.create(transactionManager);
  }
```

### Spring r2dbc DatabaseClient usage

Use jooq to generate the database query for the execution.

```java 
  private String generateInsertQuery() {
    return dslContext.insertInto(DEMO_ENTRY)
            .columns(DEMO_ENTRY.ENTRY)
            .values(UUID.randomUUID().toString())
            .getSQL(INLINED);
  }
```

Use r2dbc DatabaseClient to execute the generated query.
```java 
  private Mono<Integer> createEntry() {
    var insertQuery = generateInsertQuery();

    return databaseClient.sql(insertQuery)
        .fetch()
        .rowsUpdated();
  }
```

Use `TransactionalOperator` as the following to make the reactive pipeline a transactional unit.

```java 
  public <T> Mono<T> withCreatedEntry(Function<Integer, Mono<T>> action) {
    return createEntry()
        .flatMap(action)
        .as(operator::transactional);
  }
```

The following test verify an entry is created in db.
```java 
  @Test
  void createEntry() {
    var result = demoEntryDao.withCreatedEntry(Mono::just);
    StepVerifier.create(result)
        .expectNext(1)
        .verifyComplete();

    assertEquals(1, entryCount());
  }
```

When there is an error in the reactive pipeline, transaction should be rollback.
```java 
  @Test
  void rollbackEntry() {
    var result = demoEntryDao.withCreatedEntry(__ -> Mono.error(new RuntimeException()));
    StepVerifier.create(result)
            .expectError(RuntimeException.class)
            .verify();

    assertEquals(0, entryCount());
  }
```

### Generate jooq models
The following generate jooq source code with maven.

`mvn clean compile -P codegen`