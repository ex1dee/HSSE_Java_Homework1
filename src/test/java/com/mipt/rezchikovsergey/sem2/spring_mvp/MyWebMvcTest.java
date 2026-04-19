package com.mipt.rezchikovsergey.sem2.spring_mvp;

import com.mipt.rezchikovsergey.sem2.spring_mvp.config.bean.SecurityConfig;
import com.mipt.rezchikovsergey.sem2.spring_mvp.config.props.AppProperties;
import com.mipt.rezchikovsergey.sem2.spring_mvp.mock.user.WithMockAppUser;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ActiveProfiles;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@WebMvcTest
@EnableConfigurationProperties(AppProperties.class)
@Import(SecurityConfig.class)
@WithMockAppUser
@ActiveProfiles("test")
public @interface MyWebMvcTest {
  @AliasFor(annotation = WebMvcTest.class, attribute = "controllers")
  Class<?>[] value() default {};
}
