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
 * <p>It tests the addTime method by first checking if the added time adds up to the expected time.
 * Then it checks if the addTime method throws IllegalArgumentException
 * when trying to add 24 hours or more.
 *
 * @author Jakob Huuse
 * @version 1.0.1
 * @since 11.12.2023
 */
public class TramClockTest {
  private TramClock test;
  @BeforeEach
  void setup() {
    test = new TramClock(LocalTime.of(6, 0));
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
    TrainDepartureRegister testRegister = new TrainDepartureRegister();
    test.addListener(testRegister);
    testRegister.addTrainDeparture(new TrainDeparture(LocalTime.of(7, 0), "L2", "27", "Trondheim"));
    TrainDeparture testDeparture = new TrainDeparture(LocalTime.of(11, 0), "L2", "67", "Oslo");
    testRegister.addTrainDeparture(testDeparture);
    test.setTime(LocalTime.of(10, 0));
    ArrayList<TrainDeparture> expectedArrayList = new ArrayList<>();
    expectedArrayList.add(testDeparture);
    assertEquals(expectedArrayList, testRegister.sortByTime());
  }

  @Test
  @DisplayName("Test addTime method")
  void testAddTime() {
    test.setTime(LocalTime.of(8, 6));
    assertEquals("The time is 08:06", test.toString());

    Exception addTimeException = assertThrows(IllegalArgumentException.class,
        () -> test.setTime(LocalTime.of(7,0)),
        "method should trow");
    assertEquals(addTimeException.getMessage(),
        "Cannot set the clock to an earlier time!");
  }
}
