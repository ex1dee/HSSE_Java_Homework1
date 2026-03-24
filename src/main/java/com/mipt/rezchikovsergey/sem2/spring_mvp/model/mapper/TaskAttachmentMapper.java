package com.mipt.rezchikovsergey.sem2.spring_mvp.model.mapper;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response.AttachmentResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.TaskAttachment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskAttachmentMapper {
  AttachmentResponseDto toResponseDto(TaskAttachment attachment);
}
