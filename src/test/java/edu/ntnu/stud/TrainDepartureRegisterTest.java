package edu.ntnu.stud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * A test class for the TrainDepartureRegister class using JUnit.
 *
 * <p>This test class will initialize a TrainDepartureRegister with some
 * TrainDepartures before each test, and use it as the test object where applicable.
 *
 * <p> Firstly, it will check if the addDeparture method throws an IllegalArgumentException when it
 * should.
 * It then checks if it correctly adds a TrainDeparture when it should be valid to add it.
 *
 * <p>It then tests the searchTrainNumber method by checking if it gives the expected values
 * for searching for a train number that both is and isn't in the register.
 * It does this by confirming that the returned TrainDeparture is the same as expected, and if it
 * returns null when there is no train number matching in the register
 *
 * <p>It tests the searchDestination method by checking if it returns the expected values for
 * searching by a destination that both is and isn't in the register.
 * It does this by comparing the ArrayList the method returns to
 * an expected manually made ArrayList
 *
 * <p>The removeExpiredDepartures method is tested by checking if the expected TrainDeparture
 * objects get removed from the register. It then checks if it correctly accounts for delay.
 * It does this by comparing the returned ArrayList from searching by
 * destination to an expected manually created ArrayList
 *
 * <p>It checks if the register correctly sort the TrainDeparture objects by departureTime.
 * It does this by comparing a manually sorted ArrayList with the ArrayList the method returns.
 *
 * <p>It checks if the update method correctly removes the expired departures when called.
 *
 * <p>Lastly, it checks if the toString() method gives the expected output.
 *
 * @author Jakob Huuse
 * @version 1.0.2
 * @since 11.12.2023
 */
public class TrainDepartureRegisterTest {
  private TrainDepartureRegister testObj;
  private TrainDeparture testDeparture1;
  private TrainDeparture testDeparture2;
  private TrainDeparture testDeparture3;

  @BeforeEach
  void setup() {
    testObj = new TrainDepartureRegister();
    testDeparture1 = new TrainDeparture(LocalTime.of(13, 25), "F14", "608", "Drammen");
    testDeparture2 = new TrainDeparture(LocalTime.of(15, 15), "F22", "1337", "Trondheim", 2);
    testDeparture3 = new TrainDeparture(LocalTime.of(12, 18), "F21", "H684", "Trondheim", 4);
    testObj.addTrainDeparture(testDeparture1);
    testObj.addTrainDeparture(testDeparture2);
    testObj.addTrainDeparture(testDeparture3);
  }

  @Test
  @DisplayName("Check if adding a TrainDeparture works")
  void testAddTrainDeparture() {
    assertEquals(testDeparture1, testObj.searchTrainNumber("608"));

    Exception addDepartureException = assertThrows(IllegalArgumentException.class,
        () -> testObj.addTrainDeparture(
            new TrainDeparture(LocalTime.of(14, 25), "F15", "608", "Skoger")),
        "Validator should throw");
    assertEquals("The train number is already being used!", addDepartureException.getMessage()
    );

    Exception addDepartureException1 = assertThrows(IllegalArgumentException.class,
        () -> testObj.addTrainDeparture(
            new TrainDeparture(LocalTime.of(12, 18), "F15", "627", "Skoger", 4)),
        "Validator should throw");
    assertEquals("There can't be two trains on one track at the same departure time!",
        addDepartureException1.getMessage()
    );

    Exception addDepartureException2 = assertThrows(IllegalArgumentException.class,
        () -> testObj.addTrainDeparture(
            new TrainDeparture(LocalTime.of(12, 18), "F21", "637", "Skoger", 3)),
        "Validator should throw");
    assertEquals("There can't be two trains with the same line at the same departure time!",
        addDepartureException2.getMessage()
    );
  }

  @Test
  @DisplayName("Check if searching by train number works")
  void testSearchTrainNumber() {
    assertEquals(testDeparture1, testObj.searchTrainNumber("608"));
    Exception searchDepartureException = assertThrows(IllegalArgumentException.class,
        () -> testObj.searchTrainNumber("607"));
    assertEquals("That train number is not in the register!",
        searchDepartureException.getMessage()
    );
  }

  @Test
  @DisplayName("Check if searching by destination works")
  void testSearchDestination() {
    ArrayList<TrainDeparture> expectedArrayList = new ArrayList<>();
    expectedArrayList.add(testDeparture3);
    expectedArrayList.add(testDeparture2);
    assertEquals(expectedArrayList, testObj.searchDestination("Trondheim"));
    Exception searchDestinationException = assertThrows(IllegalArgumentException.class,
        () -> testObj.searchDestination("No mans land"));
    assertEquals("That destination is not in the register!",
        searchDestinationException.getMessage()
    );
  }

  @Test
  @DisplayName("Check if removing expired departures works")
  void testRemoveExpiredDeparture() {
    ArrayList<TrainDeparture> expectedArrayList = new ArrayList<>();
    expectedArrayList.add(testDeparture2);
    testObj.removeExpiredDepartures(LocalTime.of(14, 0));
    assertEquals(expectedArrayList, testObj.searchDestination("Trondheim"));
  }

  @Test
  @DisplayName("Check if removing expired departures accounts for delay")
  void testRemoveExpiredDepartureDelay() {
    ArrayList<TrainDeparture> expectedArrayList = new ArrayList<>();
    expectedArrayList.add(testDeparture3);
    expectedArrayList.add(testDeparture2);
    testDeparture3.setDelay(LocalTime.of(2, 0));
    testObj.removeExpiredDepartures(LocalTime.of(14, 0));
    assertEquals(expectedArrayList, testObj.searchDestination("Trondheim"));
  }

  @Test
  @DisplayName("Check if the register sorts the TrainDeparture objects correctly")
  void testSortByTime() {
    ArrayList<TrainDeparture> expectedArrayList = new ArrayList<>();
    expectedArrayList.add(testDeparture3);
    expectedArrayList.add(testDeparture1);
    expectedArrayList.add(testDeparture2);
    assertEquals(expectedArrayList, testObj.sortByTime());
  }

  @Test
  @DisplayName("Check if the update method works")
  void testUpdate() {
    testObj.update(LocalTime.of(14, 0));
    ArrayList<TrainDeparture> expectedArrayList = new ArrayList<>();
    expectedArrayList.add(testDeparture2);
    assertEquals(expectedArrayList, testObj.sortByTime());
  }

  @Test
  @DisplayName("Check if the toString method works")
  void testToString() {
    String expectedString = """
        Time    Line  Nr.   Destination     Delay     Track     ETA
        --------------------------------------------------------------
        12:18   F21   H684  Trondheim                 4         12:18
        13:25   F14   608   Drammen                             13:25
        15:15   F22   1337  Trondheim                 2         15:15""";
    assertEquals(expectedString, testObj.toString());
  }
}
