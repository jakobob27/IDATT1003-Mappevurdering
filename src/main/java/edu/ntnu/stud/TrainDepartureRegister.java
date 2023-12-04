package edu.ntnu.stud;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * The TrainDepartureRegister class is responsible for managing a collection
 * of TrainDeparture objects.
 *
 * <p>It has only one field, called register. The register is a hashmap where the keys are
 * a String with the train number to the TrainDeparture value attached to the key.
 *
 * @author Jakob Huuse
 * @version 1.0.0
 * @since 03.12.2023
 */
public class TrainDepartureRegister implements TramClockListener {
  private final HashMap<String, TrainDeparture> register;

  /**
   * A constructor that initializes the register field.
   */
  public TrainDepartureRegister() {
    register = new HashMap<>();
  }

  /**
   * Checks if you are allowed to add a train departure to the register,
   * then adds itAdds a TrainDeparture object to the register.
   *
   * @param departure A TrainDeparture object
   * @throws IllegalArgumentException If the given TrainDeparture uses the same train number
   *                                  of another departure that is in the register.
   *                                  Also throws if two departures have the same departure time
   *                                  and has the same track or line.
   */
  public void addTrainDeparture(TrainDeparture departure) {
    if (register.containsKey(departure.getTrainNumber())) {
      throw new IllegalArgumentException("The train number is already being used!");
    }
    for (TrainDeparture i : register.values()) {
      if (i.getDepartureTime().equals(departure.getDepartureTime())) {
        if (i.getTrack() == departure.getTrack()) {
          throw new IllegalArgumentException(
              "There can't be two trains on one track at the same departure time!");
        }
        if (i.getLine().equals(departure.getLine())) {
          throw new IllegalArgumentException(
              ("There can't be two trains with the same line at the same departure time!"));
        }
      }
    }
    register.put(departure.getTrainNumber(), departure);
  }

  /**
   * Searches after the trainDeparture object with the given train number in the register.
   *
   * @param trainNumber A string that describes the train number
   *                    for the TrainDeparture object you want to find
   * @return The TrainDeparture object with the given train number.
   * @throws IllegalArgumentException if the train number is not in the register.
   */
  public TrainDeparture searchTrainNumber(String trainNumber) {
    if (!register.containsKey(trainNumber)) {
      throw new IllegalArgumentException("That train number is not in the register!");
    }
    return register.get(trainNumber);
  }

  /**
   * Searches after TrainDeparture objects with the given destination
   * by iterating over all the TrainDeparture objects in the register, ignoring capitalization.
   * If the destination of a TrainDeparture object matches the given
   * destination it is added to a temporary ArrayList. It is then sorted.
   *
   * @param destination A string that describes
   *                    the destination of the TrainDeparture objects you want to find
   * @return A sorted temporary ArrayList
   *        containing the TrainDeparture objects with the given destination
   */
  public ArrayList<TrainDeparture> searchDestination(String destination) {
    ArrayList<TrainDeparture> temp = new ArrayList<>();
    for (TrainDeparture departure : register.values()) {
      if (departure.getDestination().equalsIgnoreCase(destination)) {
        temp.add(departure);
      }
    }
    Collections.sort(temp);
    return temp;
  }

  /**
   * Removes expired TrainDeparture objects from the register
   * by iterating over a copy the TrainDeparture objects in the register
   * and checks if the actual departureTime is before the current time.
   * If it is, it is removed from the register.
   *
   * @param clock A LocalTime object that describes the current time.
   */
  public void removeExpiredDepartures(LocalTime clock) {
    HashMap<String, TrainDeparture> registerCopy = new HashMap<>(register);
    for (TrainDeparture departure : registerCopy.values()) {
      if (departure.getActualDepartureTime().isBefore(clock)) {
        register.remove(departure.getTrainNumber());
      }
    }
  }

  /**
   * Makes a temporary list and sorts the TrainDeparture objects.
   *
   * @return A temporary ArrayList that is sorted by the objects departureTime
   */
  public ArrayList<TrainDeparture> sortByTime() {
    ArrayList<TrainDeparture> temp = new ArrayList<>(register.values());
    Collections.sort(temp);
    return temp;
  }

  @Override
  public void update(LocalTime clock) {
    removeExpiredDepartures(clock);
  }

  /**
   * Makes headings for the departures, then uses toString() on each TrainDeparture
   * in a sorted list and appends it on a new line.
   *
   * @return String representing this class
   */
  @Override
  public String toString() {
    StringBuilder temp =
        new StringBuilder(
            "Time" + "    " + "Line" + "  " + "Nr." + "   " + "Destination" + "     " + "Delay"
                + "     " + "Track" + "     " + "ETA");
    temp.append("\n" + "--------------------------------------------------------------");
    for (TrainDeparture departure : sortByTime()) {
      temp.append("\n");
      temp.append(departure.toString());
    }
    temp.append("\n");
    return temp.toString();
  }
}
