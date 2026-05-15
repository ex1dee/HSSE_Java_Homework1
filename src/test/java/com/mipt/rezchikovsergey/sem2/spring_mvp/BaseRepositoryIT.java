package com.mipt.rezchikovsergey.sem2.spring_mvp;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

// При запуске через @DataJpaTest спринг всеми силами пытается инициализировать
// SecurityConfig и падает из-за отсутствия необходимых ему бинов.
// Решено юзать @SpringBootTest вместо него и даже не @SpringBootTest(webEnvironment = NONE),
// т.к. SecurityConfig требует наличие веб окружения
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(PostgresExtension.class)
@Transactional
public abstract class BaseRepositoryIT {}
