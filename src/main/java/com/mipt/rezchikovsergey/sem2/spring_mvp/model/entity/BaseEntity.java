package com.mipt.rezchikovsergey.sem2.spring_mvp.model.entity;

import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class BaseEntity {
  protected UUID id;
}
