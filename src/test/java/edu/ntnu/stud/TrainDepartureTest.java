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
 * <p>Before each test two constructors with valid parameters will run.
 * These will be used as test objects where applicable.
 *
 * <p>It tests the constructor by checking if all the values given
 * is correctly set for both constructors.
 *
 * <p>It then tests if the constructor throws when given an invalid parameter.
 *
 * <p>It tests the checkTrack method by firstly checking if it throws an IllegalArgumentException
 * if giving it an invalid track parameter in the setTrack() method. It then tests if the track is
 * set correctly when giving it a valid parameter.
 *
 * <p>It tests the compareTo method by comparing the expected output when
 * comparing the two test objects to the actual output. It also tests when comparing the two test
 * objects the other way around. Finally, it checks if the method returns 0 when comparing to
 * TrainDeparture objects with the same departureTime.
 *
 * <p>Lastly, it checks if the toString() method works by checking if it gives the same String as
 * the expected output.
 *
 * @author Jakob Huuse
 * @version 1.0.2
 * @since 11.12.2023
 */
public class TrainDepartureTest {

  private TrainDeparture testObj;
  private TrainDeparture secondTestObj;

  @BeforeEach
  void setup() {
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
  @DisplayName("Check if the constructor throws")
  void testValidator() {
    Exception departureTimeException = assertThrows(IllegalArgumentException.class,
        () -> new TrainDeparture(LocalTime.of(13, 25, 22), "F14", "608", "Drammen"),
        "Constructor should throw");
    assertEquals("Cannot use time-units lower than minutes!", departureTimeException.getMessage()
    );

    Exception lineException = assertThrows(IllegalArgumentException.class,
        () -> new TrainDeparture(LocalTime.of(13, 25), "F14235", "608", "Drammen"),
        "Constructor should throw");
    assertEquals("Line-number cannot be longer than 5 characters!", lineException.getMessage()
    );

    Exception trainNumberException = assertThrows(IllegalArgumentException.class,
        () -> new TrainDeparture(LocalTime.of(13, 25), "F14", "608113", "Drammen"),
        "Constructor should throw");
    assertEquals("Train-number cannot be longer than 5 characters!",
        trainNumberException.getMessage()
    );

    Exception destinationException = assertThrows(IllegalArgumentException.class,
        () -> new TrainDeparture(LocalTime.of(13, 25), "F14", "608",
            "LLANFAIRPWLLGWYNGYLLGOGERYCHWYRNDROBWLLLLANTYSILIOGOGOGOCH"),
        "Constructor should throw");
    assertEquals("Destination names cannot be longer than 15 characters!",
        destinationException.getMessage()
    );

    Exception trackException = assertThrows(IllegalArgumentException.class,
        () -> new TrainDeparture(LocalTime.of(13, 25), "F14", "608", "Drammen", -1),
        "Constructor should throw");
    assertEquals("The track must be a positive integer!", trackException.getMessage()
    );
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
  @DisplayName("Check if compareTo works")
  void testCompareTo() {
    assertEquals(-1, testObj.compareTo(secondTestObj));
    assertEquals(1, secondTestObj.compareTo(testObj));

    TrainDeparture testSameDepartureTime =
        new TrainDeparture(LocalTime.of(15, 15), "F21", "13", "Trondheim", 2);
    assertEquals(0, secondTestObj.compareTo(testSameDepartureTime));

  }

  @Test
  @DisplayName("Check if the toString() method works")
  void testToString() {
    assertEquals("13:25   F14   608   Drammen                             13:25",
        testObj.toString(),
        "The given string should be equal to testObj.toString()");
  }
}
