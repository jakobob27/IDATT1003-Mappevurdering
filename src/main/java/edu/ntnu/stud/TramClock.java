package edu.ntnu.stud;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 * The TramClock class manages the time in the TrainDispatchApp.
 *
 * <p>The clock field is a LocalTime object that stores the time.
 *
 * <p>The listeners field is an ArrayList of listeners of a given TramClock object.
 * Each time the time changes, the listeners of the object will be notified.
 *
 * @author Jakob Huuse
 * @version 1.0.0
 * @since 14.11.2023
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
   * @param clock A localtime object that doesn't contain units lower than minutes
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
   * Adds hours and minutes to the clock. It then updates listeners with the new time.
   *
   * @param hours   a long of how many hours you want to add
   * @param minutes a long of how many minutes you want to add
   */
  public void addTime(long hours, long minutes) {
    // TODO: Gjøre rede for spesialtilfeller
    clock = clock.plusHours(hours);
    clock = clock.plusMinutes(minutes);
    updateListeners();
  }

  @Override
  public String toString() {
    return "The time is " + clock.toString();
  }
}
