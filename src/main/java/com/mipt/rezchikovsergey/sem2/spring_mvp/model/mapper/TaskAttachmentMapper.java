package com.mipt.rezchikovsergey.sem2.spring_mvp.model.mapper;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response.AttachmentResponseDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity.TaskAttachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskAttachmentMapper {
  @Mapping(source = "createdAt", target = "uploadedAt")
  AttachmentResponseDto toResponseDto(TaskAttachment attachment);
}
