package edu.ntnu.stud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test-class for the TrainDeparture class using JUnit.
 *
 * <p>It tests the constructor by checking if all the values given
 * is correctly set for both constructors
 *
 * <p>It then tests if the validator works by checking if it throws an IllegalArgumentException
 * when giving the constructor invalid parameters
 *
 * <p>It tests the checkTrack method by firstly checking if it throws an IllegalArgumentException
 * if giving it an invalid track parameter in the setTrack() method. It then tests if the track
 * is set correctly when giving it a valid parameter.
 *
 * <p>Lastly, it checks if the toString() method works by checking if it gives the same String as
 * the expected output.
 *
 * @author Jakob Huuse
 * @version 1.0.1
 * @since 07.11.2023
 */
public class TrainDepartureTest {

  TrainDeparture testObj;
  TrainDeparture secondTestObj;

  @BeforeEach
  void setUp() {
    testObj = new TrainDeparture(LocalTime.of(13, 25), "F14", "608", "Drammen");
    secondTestObj = new TrainDeparture(LocalTime.of(15, 15), "F22", "1337", "Trondheim", 2);
  }

  @Test
  @DisplayName("Check if both constructors works")
  void testConstructor() {
    assertEquals(13, testObj.getDepartureTime().getHour(),
        "The hour of the departure should be 13");
    assertEquals(25, testObj.getDepartureTime().getMinute(),
        "The minutes of the departure should be 25");
    assertEquals("F14", testObj.getLine(), "The line should be F14");
    assertEquals("608", testObj.getTrainNumber(), "The train number should be 608");
    assertEquals("Drammen", testObj.getDestination(), "The destination should be Drammen");
    assertEquals(-1, testObj.getTrack(), "The track-number should be -1 if not specified");
    assertEquals(2, secondTestObj.getTrack(), "The track-number of secondTestObj should be 2");
  }

  @Test
  @DisplayName("Check if the validator works")
  void testValidator() {
    Exception departureTimeException = assertThrows(IllegalArgumentException.class,
        () -> new TrainDeparture(LocalTime.of(13, 25, 22), "F14", "608", "Drammen"),
        "Validator should throw");
    assertEquals(departureTimeException.getMessage(),
        "departureTime cannot contain time-units lower than minutes!");

  }

  @Test
  @DisplayName("Check if checkTrack works")
  void testCheckTrack() {
    Exception checkTrackException = assertThrows(IllegalArgumentException.class,
        () -> testObj.setTrack(0));
    assertEquals(checkTrackException.getMessage(),
        "The track must be a positive integer!");

    testObj.setTrack(1);
    assertEquals(1, testObj.getTrack());
  }

  @Test
  @DisplayName("Check if the toString() method works")
  void testToString() {
    assertEquals("Line F14 with train-number 608 to Drammen on track number -1"
            + " departs at 13:25", testObj.toString(),
        "The given string should be equal to testObj.toString()");
  }
}
