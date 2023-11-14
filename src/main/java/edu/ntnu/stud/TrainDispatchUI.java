package edu.ntnu.stud;

import java.time.LocalTime;

public class TrainDispatchUI {

  public void init() {

  }

  public void start() {
    TrainDeparture test1 = new TrainDeparture(LocalTime.of(13, 25), "F14", "608", "Drammen");
    TrainDeparture test2 = new TrainDeparture(LocalTime.of(15, 15), "F15", "608", "Trondheim", 1);
    TrainDeparture test3 = new TrainDeparture(LocalTime.of(10, 25), "A125", "608", "Oslo", 2);

    System.out.println(test1);
    System.out.println(test2);
    System.out.println(test3);
  }
}
