package com.mipt.rezchikovsergey.sem2.spring_mvp;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

@Slf4j
public class PostgresExtension
    implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {
  public static final Network PG_NETWORK = Network.newNetwork();
  private static final Lock LOCK = new ReentrantLock();
  private static final AtomicBoolean STARTED = new AtomicBoolean(false);

  private static final PostgreSQLContainer<?> POSTGRES =
      new PostgreSQLContainer<>(DockerImageName.parse("postgres:15-alpine"))
          .withNetworkAliases("postgres")
          .withNetwork(PG_NETWORK)
          .withCommand("postgres", "-c", "max_connections=20000");

  @Override
  public void beforeAll(ExtensionContext context) {
    LOCK.lock();
    try {
      if (!STARTED.compareAndExchange(false, true)) {
        log.info("Start POSTGRES Container");
        Startables.deepStart(POSTGRES).join();

        System.setProperty("spring.datasource.url", POSTGRES.getJdbcUrl());
        System.setProperty("spring.datasource.username", POSTGRES.getUsername());
        System.setProperty("spring.datasource.password", POSTGRES.getPassword());
        System.setProperty("spring.datasource.driver-class-name", POSTGRES.getDriverClassName());

        context.getRoot().getStore(GLOBAL).put("POSTGRES Container", this);
      }
    } finally {
      LOCK.unlock();
    }
  }

  @Override
  public void close() {
    log.info("Close POSTGRES Container");
    POSTGRES.close();
    STARTED.set(false);
  }
}
