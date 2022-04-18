package com.sithroo.r2dbc.demo.db.dao;

import static com.sithroo.r2dbc.demo.db.generated.tables.DemoEntry.DEMO_ENTRY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sithroo.r2dbc.demo.db.DbConfiguration;
import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(classes = {
    TestConfig.class,
    DbConfiguration.class
}, webEnvironment = WebEnvironment.NONE)
public class DemoEntryDaoTest {

  @Autowired
  private DSLContext dslContext;

  @Autowired
  private DatabaseClient databaseClient;

  @Autowired
  private DemoEntryDao demoEntryDao;

  @BeforeEach
  void setUp() {
    databaseClient.sql(dslContext.deleteFrom(DEMO_ENTRY).getSQL())
        .then()
        .block();
  }

  @Test
  void createEntry() {
    var result = demoEntryDao.withCreatedEntry(Mono::just);
    StepVerifier.create(result)
        .expectNext(1)
        .verifyComplete();

    assertEquals(1, entryCount());
  }

  @Test
  void rollbackEntry() {
    var result = demoEntryDao.withCreatedEntry(__ -> Mono.error(new RuntimeException()));
    StepVerifier.create(result)
            .expectError(RuntimeException.class)
            .verify();

    assertEquals(0, entryCount());
  }

  Integer entryCount() {
    var sql = dslContext.selectCount()
        .from(DEMO_ENTRY)
        .getSQL();

    return databaseClient.sql(sql)
        .map(row -> row.get(0, Integer.class))
        .one()
        .block();
  }

}
