package com.sithroo.r2dbc.demo.db.dao;

import org.jooq.DSLContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.transaction.reactive.TransactionalOperator;

@TestConfiguration
public class TestConfig {

  @Bean
  DemoEntryDao dataEntryDao(DatabaseClient dbClient, TransactionalOperator operator, DSLContext dslContext) {
    return new DemoEntryDao(dbClient, operator, dslContext);
  }

}
