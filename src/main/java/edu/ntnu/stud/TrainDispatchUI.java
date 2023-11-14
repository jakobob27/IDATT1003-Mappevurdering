package edu.ntnu.stud;

import java.time.LocalTime;

public class TrainDispatchUI {
  private TrainDepartureRegister register;
  private TramClock time;

  public void init() {
    TrainDeparture test1 = new TrainDeparture(LocalTime.of(13, 25), "F14", "608", "Drammen");
    TrainDeparture test2 = new TrainDeparture(LocalTime.of(15, 15), "F15", "628", "Trondheim", 1);
    TrainDeparture test3 = new TrainDeparture(LocalTime.of(10, 25), "A125", "68", "Oslo", 2);

    register = new TrainDepartureRegister();
    register.addTrainDeparture(test1);
    register.addTrainDeparture(test2);
    register.addTrainDeparture(test3);
    time = new TramClock(LocalTime.of(0, 0));
    time.addListener(register);
  }

  public void start() {
    System.out.println(this);
    time.addTime(14, 0);
    System.out.println(this);
  }

  @Override
  public String toString() {
    return time.toString() + "\n" + register.toString();
  }
}
