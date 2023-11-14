package edu.ntnu.stud;

import java.time.LocalTime;

public class TrainDispatchUI {

  public void init() {

  }

  public void start() {
    TrainDeparture test1 = new TrainDeparture(LocalTime.of(13, 25), "F14", "608", "Drammen");
    TrainDeparture test2 = new TrainDeparture(LocalTime.of(15, 15), "F15", "628", "Trondheim", 1);
    TrainDeparture test3 = new TrainDeparture(LocalTime.of(10, 25), "A125", "68", "Oslo", 2);

    TrainDepartureRegister test = new TrainDepartureRegister();
    test.addTrainDeparture(test1);
    test.addTrainDeparture(test2);
    test.addTrainDeparture(test3);

    System.out.println(test);
  }
}
