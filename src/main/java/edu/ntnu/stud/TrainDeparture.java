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
 * in the format of "L1", "F4" etc.
 *
 * <p>trainNumber - A final string
 * with a unique number within a 24-hour window in the format "602", "45", "1951" etc.
 *
 * <p>destination -  A final string that describes the train-departures destination.
 *
 * <p>track - An integer that describes which track the train-departure is running on.
 * If the track is not defined the value is set to -1.
 *
 * <p>delay - A long that describes the delay of the train in minutes
 *
 * @author Jakob Huuse
 * @version 1.0.1
 * @since 14.11.2023
 */
public class TrainDeparture implements Comparable<TrainDeparture> {

  private final LocalTime departureTime;
  private final String line;
  private final String trainNumber;
  private final String destination;
  private int track = -1;
  private long delay;


  /**
   * Validates that the departureTime is in the right format.
   *
   * <p>The other fields in this object are in their correct format as given that
   * they are their designated type.
   *
   * <p>Throws IllegalArgumentExceptions with fitting messages if one of the parameters
   * is not in the right format.
   *
   * @param departureTime a localTime object that cannot contain time-units lower than minutes
   * @throws IllegalArgumentException if one of the parameters are not in a valid format
   */
  private void validator(LocalTime departureTime) {
    if (departureTime.getSecond() != 0 || departureTime.getNano() != 0) {
      throw new IllegalArgumentException(
          "departureTime cannot contain time-units lower than minutes!");
    }

  }

  /**
   * Checks if the given track is a positive integer, and throws IllegalArgumentException if it's
   * not.
   *
   * @param track a positive integer
   * @throws IllegalArgumentException if the int given is not a positive integer or track has
   *                                  already been set.
   */
  public void checkTrack(int track) {
    if (track < 1) {
      throw new IllegalArgumentException("The track must be a positive integer!");
    }
  }

  /**
   * A constructor for a TrainDeparture object that doesn't define which track it is on.
   *
   * @param departureTime a localTime object that cannot contain time-units lower than minutes
   * @param line          a String that describes the line
   * @param trainNumber   a String that describes the train number
   * @param destination   a String that describes the destination
   */
  public TrainDeparture(LocalTime departureTime, String line, String trainNumber,
                        String destination) {
    validator(departureTime);
    this.departureTime = departureTime;
    this.line = line;
    this.trainNumber = trainNumber;
    this.destination = destination;
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
    checkTrack(track);
    this.track = track;
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

  public long getDelay() {
    return delay;
  }

  /**
   * Method that adds the delay to the departureTime to get the actual departureTime.
   *
   * @return a LocalTime object describing departureTime with delay
   */
  public LocalTime getActualDepartureTime() {
    return departureTime.plusMinutes(delay);
  }

  /**
   * Checks if the parameter is valid, then sets the track to the parameter.
   *
   * @param track a positive integer
   */
  public void setTrack(int track) {
    checkTrack(track);
    this.track = track;
  }

  public void setDelay(long delay) {
    this.delay = delay;
  }

  /**
   * toString method that makes sure the spacing between the fields that are printed are consistent.
   *
   * @return String representing this class
   */
  @Override
  public String toString() {
    StringBuilder temp = new StringBuilder(getActualDepartureTime() + "   " + line);
    while (temp.length() < 14) {
      temp.append(" ");
    }
    temp.append(destination);
    while (temp.length() < 34) {
      temp.append(" ");
    }
    if (track == -1) {
      temp.append("undefined");
    } else {
      temp.append(track);
    }

    while (temp.length() < 45) {
      temp.append(" ");
    }
    temp.append(trainNumber);
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
