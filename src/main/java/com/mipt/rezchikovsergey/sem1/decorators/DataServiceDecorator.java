package com.mipt.rezchikovsergey.sem1.decorators;

public abstract class DataServiceDecorator implements DataService {
  DataService decoratedComponent;

  public DataServiceDecorator(DataService decoratedComponent) {
    this.decoratedComponent = decoratedComponent;
  }
}
