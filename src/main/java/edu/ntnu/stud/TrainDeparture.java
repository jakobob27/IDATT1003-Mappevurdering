package edu.ntnu.stud;

import java.time.LocalTime;

/**
 * The TrainDeparture class is responsible for storing information
 * stored within a specific train-departure.
 *
 * <p>It also has the responsibility of making sure that the information given when creating a
 * new train-departure is both valid and correctly formatted.
 *
 * <p>If given an invalid parameter when constructing an object of the class,
 * it will throw a IllegalArgumentException with an explanatory message.
 */
public class TrainDeparture {
  /**
   * A final localTime object that describes the time the train departs in the format HH:mm
   *
   * <p>A LocalTime object will make it easier to both format and do operations regarding time.
   * The field is final because the planned departureTime won't change, only they amount of delay.
   */
  private final LocalTime departureTime;
  /**
   * A final string that defines the route the train is running in the format of "L1", "F4" etc.
   *
   * <p>It is a String because the task specified that the field is a text.
   * The field is final because a train-departure won't change its line mid-departure.
   */
  private final String line;
  /**
   * A final string with a unique number within a 24-hour window in the format "602", "45", "1951"
   * etc.
   *
   * <p>It is a String because the task specified that the field is a text.
   * The field is final because a train-departure won't change its number mid-departure.
   */
  private final String trainNumber;
  /**
   * A final string that describes the train-departures' destination.
   *
   * <p>It is a String because the destination is a noun.
   * The field is final because a train-departure won't change its destination.
   */
  private final String destination;
  /**
   * An integer that describes which track the train-departure is running on.
   *
   * <p>If the track is not defined the value is set to -1.
   * Once a track is defined, it can not be changed.
   *
   * <p>It is an int because the track is represented by a whole number.
   */
  private int track = -1;
  /**
   * A localTime object that describes the delay of the train in the format HH:mm.
   *
   * <p>A localTime object will make it easier to do operations regarding time.
   */
  private LocalTime delay;

  /**
   * A boolean that describes if the track has been set or not.
   *
   * <p>It is a boolean because the track is either set, or it is not.
   */
  private boolean trackIsSet = false;

  /**
   * Validates that the departureTime, line and trainNumber parameter is in the right format.
   *
   * <p>The other fields in this object are in their correct format as given that
   * they are their designated type.
   *
   * <p>Throws IllegalArgumentExceptions with fitting messages if one of the parameters
   * is not in the right format.
   *
   * @param departureTime a localTime object that cannot contain time-units lower than minutes
   * @param line          a String where the first character is not a number,
   *                      but all the other characters is a number.
   * @param trainNumber   a String where all the characters are numbers
   *
   * @throws IllegalArgumentException if one of the parameters are not in a valid format
   */
  private void validator(LocalTime departureTime, String line, String trainNumber) {
    if (departureTime.getSecond() != 0 || departureTime.getNano() != 0) {
      throw new IllegalArgumentException(
          "departureTime cannot contain time-units lower than minutes!");
    }
    String lastPart = line.substring(1);
    if (Character.isDigit(line.charAt(0))) {
      throw new IllegalArgumentException("The first character for the line can't be a number!");
    }
    for (int i = 0; i < lastPart.length(); i++) {
      if (!Character.isDigit(lastPart.charAt(i))) {
        throw new IllegalArgumentException(
            "Every character after the first for the line must be a number!");
      }
    }

    for (int i = 0; i < trainNumber.length(); i++) {
      if (!Character.isDigit(trainNumber.charAt(i))) {
        throw new IllegalArgumentException("Every character in the trainNumber must be a number!");
      }
    }
  }

  /**
   * Checks if the given track is a positive integer,
   * and throws IllegalArgumentException if it's not.
   *
   * <p>It will also throw IllegalArgumentException if the track has already been set.
   *
   * @param track a positive integer
   * @throws IllegalArgumentException if the int given is not a positive integer
   *                                  or track has already been set.
   */
  public void checkTrack(int track) {
    if (track < 1) {
      throw new IllegalArgumentException("The track must be a positive integer!");
    }

    if (trackIsSet) {
      throw new IllegalArgumentException("The track has already been set!");
    }
  }

  /**
   * A constructor for a TrainDeparture object that doesn't define which track it is on.
   *
   * @param departureTime a localTime object that cannot contain time-units lower than minutes
   * @param line          a String where the first character is not a number,
   *                      but all the other characters is a number.
   * @param trainNumber   a String where all the characters are numbers
   * @param destination   a String that describes the destination
   */
  public TrainDeparture(LocalTime departureTime, String line, String trainNumber,
                        String destination) {
    validator(departureTime, line, trainNumber);
    this.departureTime = departureTime;
    this.line = line;
    this.trainNumber = trainNumber;
    this.destination = destination;
  }

  /**
   * Another constructor for when you want to define the track
   * when creating a TrainDeparture object.
   *
   * @param departureTime a localTime object that cannot contain time-units lower than minutes
   * @param line          a String where the first character is not a number,
   *                      but all the other characters is a number.
   * @param trainNumber   a String where all the characters are numbers
   * @param destination   a String that describes the destination
   * @param track         a positive int that describes which track the train-departure is on.
   */
  public TrainDeparture(LocalTime departureTime, String line, String trainNumber,
                        String destination, int track) {
    this(departureTime, line, trainNumber, destination);
    checkTrack(track);
    this.track = track;
    trackIsSet = true;
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
   * Checks if the given int is valid, then sets the track to the given int.
   *
   * @param track a positive integer
   */
  public void setTrack(int track) {
    checkTrack(track);
    this.track = track;
    trackIsSet = true;
  }

  public void setDelay(LocalTime delay) {
    this.delay = delay;
  }

  /**
   * toString method that prints the returns a string in the following format:
   * "Line {line} with train-number {trainNumber} to {destination} on track number {track}
   * departs at {departureTime}".
   *
   * @return String
   */
  @Override
  public String toString() {
    return "Line " + getLine() + " with train-number " + getTrainNumber() + " to "
        + getDestination() + " on track number " + getTrack() + "departs at " + getDepartureTime();
  }
}
