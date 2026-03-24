package com.mipt.rezchikovsergey.sem2.spring_mvp;

import com.mipt.rezchikovsergey.sem2.spring_mvp.config.props.AppProperties;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.mapper.TaskAttachmentMapper;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.mapper.TaskMapper;
import com.mipt.rezchikovsergey.sem2.spring_mvp.service.AttachmentService;
import com.mipt.rezchikovsergey.sem2.spring_mvp.service.FavoritesService;
import com.mipt.rezchikovsergey.sem2.spring_mvp.service.TaskStatisticsService;
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
  @MockitoBean protected AttachmentService attachmentService;
  @MockitoBean protected FavoritesService favoritesService;
}
