package edu.ntnu.stud;

import java.time.LocalTime;

/**
 * Listener interface for TramClock.
 *
 * @author Jakob Huuse
 * @version 1.0.0
 * @since 14.11.2023
 */
public interface TramClockListener {
  /**
   * Update method for listeners of TramClock.
   *
   * @param clock a LocalTime object with the newly set time
   *
   * @param prevClock  a LocalTime object with the previously set time
   */
  public void update(LocalTime prevClock, LocalTime clock);
}
