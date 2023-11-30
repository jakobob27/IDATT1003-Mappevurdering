package edu.ntnu.stud;

import java.time.LocalTime;

/**
 * Listener interface for TramClock.
 *
 * @author Jakob Huuse
 * @version 1.0.0
 * @since 30.11.2023
 */
public interface TramClockListener {
  /**
   * Update method for listeners of TramClock.
   *
   * @param clock a LocalTime object with the newly set time
   */
  public void update(LocalTime clock);
}
