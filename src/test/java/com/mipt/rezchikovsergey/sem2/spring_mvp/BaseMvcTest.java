package com.mipt.rezchikovsergey.sem2.spring_mvp;

import com.mipt.rezchikovsergey.sem2.spring_mvp.config.props.AppProperties;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.mapper.TaskAttachmentMapper;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.model.mapper.TaskMapper;
import com.mipt.rezchikovsergey.sem2.spring_mvp.external.service.TaskStatisticsService;
import com.mipt.rezchikovsergey.sem2.spring_mvp.service.GatewayAttachmentService;
import com.mipt.rezchikovsergey.sem2.spring_mvp.service.GatewayFavoritesService;
import com.mipt.rezchikovsergey.sem2.spring_mvp.service.GatewayTaskService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
