package com.mipt.rezchikovsergey.patterns.decorators;

public abstract class DataServiceDecorator implements DataService {
  DataService decoratedComponent;

  public DataServiceDecorator(DataService decoratedComponent) {
    this.decoratedComponent = decoratedComponent;
  }
}
