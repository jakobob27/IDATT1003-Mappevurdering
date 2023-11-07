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
 * If the track is not defined the value is set to -1. Once a track is defined, it can not be
 * changed.
 *
 * <p>delay - A localTime object that describes the delay of the train in the format HH:mm.
 *
 * @author Jakob Huuse
 * @version 1.0.1
 * @since 31.10.2023
 */
public class TrainDeparture {

  private final LocalTime departureTime;
  private final String line;
  private final String trainNumber;
  private final String destination;
  private int track = -1;
  private LocalTime delay;


  /**
   * Validates that the departureTime and trainNumber parameter is in the right format.
   *
   * <p>The other fields in this object are in their correct format as given that
   * they are their designated type.
   *
   * <p>Throws IllegalArgumentExceptions with fitting messages if one of the parameters
   * is not in the right format.
   *
   * @param departureTime a localTime object that cannot contain time-units lower than minutes
   * @param trainNumber   a String where all the characters are numbers
   * @throws IllegalArgumentException if one of the parameters are not in a valid format
   */
  private void validator(LocalTime departureTime, String trainNumber) {
    if (departureTime.getSecond() != 0 || departureTime.getNano() != 0) {
      throw new IllegalArgumentException(
          "departureTime cannot contain time-units lower than minutes!");
    }

    for (int i = 0; i < trainNumber.length(); i++) {
      if (!Character.isDigit(trainNumber.charAt(i))) {
        throw new IllegalArgumentException("Every character in the trainNumber must be a number!");
      }
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
   * @param trainNumber   a String where all the characters are numbers
   * @param destination   a String that describes the destination
   */
  public TrainDeparture(LocalTime departureTime, String line, String trainNumber,
      String destination) {
    validator(departureTime, trainNumber);
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
   * @param trainNumber   a String where all the characters are numbers
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

  public LocalTime getDelay() {
    return delay;
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

  public void setDelay(LocalTime delay) {
    this.delay = delay;
  }

  /**
   * toString method that prints the returns a string in the following format: "Line {line} with
   * train-number {trainNumber} to {destination} on track number {track} departs at
   * {departureTime}".
   *
   * @return String
   */
  @Override
  public String toString() {
    return "Line " + getLine() + " with train-number " + getTrainNumber() + " to "
        + getDestination() + " on track number " + getTrack() + " departs at " + getDepartureTime();
  }
}
