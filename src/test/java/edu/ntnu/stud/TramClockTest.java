package edu.ntnu.stud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * A test class for the TramClock class using JUnit.
 *
 * <p>Before each test it will initialize a new TramClock and TrainDepartureRegister,
 * and add the register as a listener to the TramClock.
 *
 * <p>It tests the validator by checking if it throws IllegalArgumentException
 * when given an invalid argument. It also checks if the exception gives the appropriate message.
 *
 * <p>It tests the updateListeners method by checking if the register removes expired departures
 * when called.
 *
 * @author Jakob Huuse
 * @version 1.0.0
 * @since 14.11.2023
 */
public class TramClockTest {
  private TramClock test;
  private TrainDepartureRegister testRegister;

  @BeforeEach
  void setup() {
    test = new TramClock(LocalTime.of(6, 0));
    testRegister = new TrainDepartureRegister();
    test.addListener(testRegister);
  }

  @Test
  @DisplayName("Test validator")
  void testValidator() {
    Exception departureTimeException = assertThrows(IllegalArgumentException.class,
        () -> new TramClock(LocalTime.of(6, 2, 1)),
        "Validator should throw");
    assertEquals(departureTimeException.getMessage(),
        "clock cannot contain time-units lower than minutes!");
  }

  @Test
  @DisplayName("Test listeners")
  void testListener() {
    testRegister.addTrainDeparture(new TrainDeparture(LocalTime.of(5, 0), "L2", "27", "Trondheim"));
    test.updateListeners();
    assertEquals(new ArrayList<>(), testRegister.searchDestination("Trondheim"));
  }
}
