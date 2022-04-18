package com.sithroo.r2dbc.demo.db.dao;

import static com.sithroo.r2dbc.demo.db.generated.tables.DemoEntry.DEMO_ENTRY;
import static org.jooq.conf.ParamType.INLINED;

import java.util.UUID;
import java.util.function.Function;
import org.jooq.DSLContext;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Repository
public class DemoEntryDao {
  private final DatabaseClient databaseClient;
  private final TransactionalOperator operator;
  private final DSLContext dslContext;

  public DemoEntryDao(DatabaseClient databaseClient, TransactionalOperator operator, DSLContext dslContext) {
    this.databaseClient = databaseClient;
    this.operator = operator;
    this.dslContext = dslContext;
  }

  public <T> Mono<T> withCreatedEntry(Function<Integer, Mono<T>> action) {
    return createEntry()
        .flatMap(action)
        .as(operator::transactional);
  }

  private Mono<Integer> createEntry() {
    var insertQuery = generateInsertQuery();

    return databaseClient.sql(insertQuery)
        .fetch()
        .rowsUpdated();
  }

  private String generateInsertQuery() {
    return dslContext.insertInto(DEMO_ENTRY)
            .columns(DEMO_ENTRY.ENTRY)
            .values(UUID.randomUUID().toString())
            .getSQL(INLINED);
  }

}
