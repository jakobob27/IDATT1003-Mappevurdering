package edu.ntnu.stud;

import java.time.LocalTime;

/**
 * The TrainDeparture class is responsible for storing information stored within a specific
 * train-departure.
 *
 * <p>It also has the responsibility of making sure that the information given when creating a
 * new train-departure is both valid and correctly formatted.
 *
 * <p>If given an invalid parameter when constructing an object of the class,
 * it will throw a IllegalArgumentException with an explanatory message.
 *
 * <p>The class has the following fields:
 *
 * <p>departureTime - A final localTime object
 * that describes the time the train departs in the format HH:mm
 *
 * <p>line - A final string that defines the route the train is running
 * in the format of "L1", "F4" etc. It can not have more than 5 characters.
 *
 * <p>trainNumber - A final string
 * with a unique number within a 24-hour window in the format "602", "45", "1951" etc.
 * It can not have more than 5 characters.
 *
 * <p>destination -  A final string that describes the train-departures destination.
 * It can not have more than 15 characters.
 *
 * <p>track - An integer that describes which track the train-departure is running on.
 * If the track is not defined the value is set to -1.
 *
 * <p>delay - A localtime object that describes the delay of the train in hours and minutes.
 * If no delay is set, the delay is 00:00
 *
 * @author Jakob Huuse
 * @version 1.0.3
 * @since 11.12.2023
 */
public class TrainDeparture implements Comparable<TrainDeparture> {

  private LocalTime departureTime;
  private String line;
  private final String trainNumber;
  private String destination;
  private int track = -1;
  private LocalTime delay = LocalTime.MIDNIGHT;


  /**
   * Validates that the LocalTime object is in the right format.
   *
   * @param time a localTime object.
   * @throws IllegalArgumentException if the LocalTime object contains units lower than minutes.
   */
  private void checkTime(LocalTime time) {
    if (time.getSecond() != 0 || time.getNano() != 0) {
      throw new IllegalArgumentException(
          "Cannot use time-units lower than minutes!");
    }

  }

  /**
   * A constructor for a TrainDeparture object that doesn't define which track it is on.
   *
   * @param departureTime a localTime object that cannot contain time-units lower than minutes
   * @param line          a String that describes the line, no longer than 5 characters
   * @param trainNumber   a String that describes the train number, no longer than 5 characters
   * @param destination   a String that describes the destination, no longer than 15 characters
   */
  public TrainDeparture(LocalTime departureTime, String line, String trainNumber,
                        String destination) {
    if (trainNumber.length() > 5) {
      throw new IllegalArgumentException("Train-number cannot be longer than 5 characters!");
    }
    setDepartureTime(departureTime);
    setLine(line);
    this.trainNumber = trainNumber;
    setDestination(destination);
  }

  /**
   * Another constructor for when you want to define the track when creating a TrainDeparture
   * object.
   *
   * @param departureTime a localTime object that cannot contain time-units lower than minutes
   * @param line          a String that describes the line
   * @param trainNumber   a String that describes the train number
   * @param destination   a String that describes the destination
   * @param track         a positive int that describes which track the train-departure is on.
   */
  public TrainDeparture(LocalTime departureTime, String line, String trainNumber,
                        String destination, int track) {
    this(departureTime, line, trainNumber, destination);
    setTrack(track);
  }

  public LocalTime getDepartureTime() {
    return departureTime;
  }

  public String getLine() {
    return line;
  }

  public String getTrainNumber() {
    return trainNumber;
  }

  public String getDestination() {
    return destination;
  }

  public int getTrack() {
    return track;
  }

  public LocalTime getDelay() {
    return delay;
  }

  /**
   * Method that adds the delay to the departureTime to get the actual departureTime.
   *
   * @return a LocalTime object describing departureTime with delay
   */
  public LocalTime getActualDepartureTime() {
    return departureTime.plusHours(delay.getHour()).plusMinutes(delay.getMinute());
  }

  private void setDepartureTime(LocalTime departureTime) {
    checkTime(departureTime);
    this.departureTime = departureTime;
  }

  private void setDestination(String destination) {
    if (destination.length() > 15) {
      throw new IllegalArgumentException("Destination names cannot be longer than 15 characters!");
    }
    this.destination = destination;
  }

  private void setLine(String line) {
    if (line.length() > 5) {
      throw new IllegalArgumentException("Line-number cannot be longer than 5 characters!");
    }
    this.line = line;
  }

  /**
   * Checks if the parameter is a positive integer, then sets the track to the parameter.
   *
   * @param track A positive integer.
   */
  public void setTrack(int track) {
    if (track < 1) {
      throw new IllegalArgumentException("The track must be a positive integer!");
    }
    this.track = track;
  }

  public void setDelay(LocalTime delay) {
    checkTime(delay);
    this.delay = delay;
  }

  /**
   * toString method that makes sure the spacing between the fields that are printed are consistent.
   *
   * @return String representing this class
   */
  @Override
  public String toString() {
    StringBuilder temp = new StringBuilder(getDepartureTime() + "   " + getLine());
    while (temp.length() < 14) {
      temp.append(" ");
    }
    temp.append(getTrainNumber());
    while (temp.length() < 20) {
      temp.append(" ");
    }
    temp.append(getDestination());
    while (temp.length() < 36) {
      temp.append(" ");
    }

    if (!getDelay().equals(LocalTime.MIDNIGHT)) {
      temp.append(getDelay());
    }
    while (temp.length() < 46) {
      temp.append(" ");
    }
    if (getTrack() != -1) {
      temp.append(getTrack());
    }
    while (temp.length() < 56) {
      temp.append(" ");
    }
    temp.append(getActualDepartureTime());
    return temp.toString();
  }

  /**
   * Implementation of the comparable interface.
   *
   * @param o the TrainDeparture object to be compared.
   * @return -1 if this departureTime is before the other,
   *        1 if this departureTime is after the other, and 0 if they are equal
   */
  @Override
  public int compareTo(TrainDeparture o) {
    if (this.getDepartureTime().isBefore(o.getDepartureTime())) {
      return -1;
    } else if (this.getDepartureTime().isAfter(o.getDepartureTime())) {
      return 1;
    } else {
      return 0;
    }
  }
}
