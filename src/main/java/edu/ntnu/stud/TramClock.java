package edu.ntnu.stud;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 * The TramClock class manages the time in the TrainDispatchApp.
 *
 * <p>The clock field is a LocalTime object that stores the time.
 *
 * <p>The prevClock field is a LocalTime object that stores the previous time.
 *
 * <p>The listeners field is an ArrayList of listeners of a given TramClock object.
 * Each time the time changes, the listeners of the object will be notified.
 *
 * @author Jakob Huuse
 * @version 1.0.1
 * @since 30.11.2023
 */
public class TramClock {
  private LocalTime clock;
  private final ArrayList<TramClockListener> listeners;

  /**
   * Validates that clock won't contain units lower than minutes.
   *
   * @param clock A localtime object that doesn't contain units lower than minutes
   * @throws IllegalArgumentException if the given LocalTime object has units lower than minutes
   */
  public void validator(LocalTime clock) {
    if (clock.getSecond() != 0 || clock.getNano() != 0) {
      throw new IllegalArgumentException(
          "clock cannot contain time-units lower than minutes!");
    }
  }

  /**
   * A constructor for the TramClock class.
   * It first runs the validator on the given LocalTime object, then sets the clock field to it.
   * It also initializes the listeners field.
   *
   * @param clock A localtime object that describes the current time.
   *              Doesn't contain units lower than minutes
   */
  public TramClock(LocalTime clock) {
    validator(clock);
    this.clock = clock;
    listeners = new ArrayList<>();
  }

  /**
   * Adds a listener of type TramClockListener into listeners.
   *
   * @param listener an object implementing the TramClockListener interface
   */
  public void addListener(TramClockListener listener) {
    listeners.add(listener);
  }

  /**
   * Iterates over listeners and calls their update method.
   */
  public void updateListeners() {
    for (TramClockListener listener : listeners) {
      listener.update(clock);
    }
  }

  /**
   * Sets a new time for the clock.
   * It then updates listeners with the new time.
   *
   * @param newTime A LocalTime object describing the new time you want to set the clock to.
   * @throws IllegalArgumentException when trying to set the clock
   *                                  to an earlier time than it is currently.
   */
  public void setTime(LocalTime newTime) {
    if (newTime.isBefore(clock)) {
      throw new IllegalArgumentException("Cannot set the clock to an earlier time!");
    }
    clock = newTime;
    updateListeners();
  }

  @Override
  public String toString() {
    return "The time is " + clock.toString();
  }
}
