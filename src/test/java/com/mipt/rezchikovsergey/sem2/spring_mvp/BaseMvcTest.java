package com.mipt.rezchikovsergey.sem2.spring_mvp;

import com.mipt.rezchikovsergey.sem2.spring_mvp.config.props.AppProperties;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.mapper.TaskAttachmentMapper;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.mapper.TaskMapper;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.service.TaskStatisticsService;
import com.mipt.rezchikovsergey.sem2.spring_mvp.security.AppUserDetails;
import com.mipt.rezchikovsergey.sem2.spring_mvp.service.GatewayAttachmentService;
import com.mipt.rezchikovsergey.sem2.spring_mvp.service.GatewayFavoritesService;
import com.mipt.rezchikovsergey.sem2.spring_mvp.service.GatewayTaskService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@MyWebMvcTest
public class BaseMvcTest {
  @Autowired protected MockMvc mockMvc;
  @Autowired protected AppProperties appProperties;

  @MockitoBean protected TaskStatisticsService taskStatisticsService;
  @MockitoBean protected TaskAttachmentMapper attachmentMapper;
  @MockitoBean protected TaskMapper taskMapper;
  @MockitoBean protected GatewayAttachmentService attachmentService;
  @MockitoBean protected GatewayFavoritesService favoritesService;
  @MockitoBean protected GatewayTaskService taskService;

  protected void mockAuth() {
    AppUserDetails mockUser =
        new AppUserDetails(
            UUID.randomUUID(),
            "test user",
            "pass",
            List.of(new SimpleGrantedAuthority("ROLE_USER")));

    Authentication auth =
        new UsernamePasswordAuthenticationToken(mockUser, null, mockUser.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(auth);
  }
}
