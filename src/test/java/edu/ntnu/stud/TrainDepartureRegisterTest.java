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
 * <p> Firstly, it will check if trying to add a TrainDeparture with the
 * same train number as one in the register will throw an IllegalArgumentException.
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
 * <p>Lastly, it checks if the register correctly sort the TrainDeparture objects by departureTime.
 * It does this by comparing a manually sorted ArrayList with the ArrayList the method returns.
 *
 * @author Jakob Huuse
 * @version 1.0.0
 * @since 07.11.2023
 */
public class TrainDepartureRegisterTest {
  TrainDepartureRegister testObj;
  TrainDeparture testDeparture1;
  TrainDeparture testDeparture2;
  TrainDeparture testDeparture3;

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
  }

  @Test
  @DisplayName("Check if searching by train number works")
  void testSearchTrainNumber() {
    assertEquals(testDeparture1, testObj.searchTrainNumber("608"));
    assertNull(testObj.searchTrainNumber("607"));
  }

  @Test
  @DisplayName("Check if searching by destination works")
  void testSearchDestination() {
    ArrayList<TrainDeparture> expectedArrayList = new ArrayList<>();
    expectedArrayList.add(testDeparture2);
    expectedArrayList.add(testDeparture3);
    assertEquals(expectedArrayList, testObj.searchDestination("Trondheim"));
    assertEquals(new ArrayList<>(), testObj.searchDestination("No man's land"));
  }

  @Test
  @DisplayName("Check if removing expired departures works")
  void testRemoveExpiredDeparture() {
    ArrayList<TrainDeparture> expectedArrayList = new ArrayList<>();
    expectedArrayList.add(testDeparture2);
    testObj.removeExpiredDepartures(LocalTime.of(14, 0));
    assertEquals(expectedArrayList, testObj.searchDestination("Trondheim"));

    testObj.removeExpiredDepartures(LocalTime.of(16, 0));
    assertEquals(new ArrayList<>(), testObj.searchDestination("Trondheim"));
  }

  @Test
  @DisplayName("Check if removing expired departures accounts for delay")
  void testRemoveExpiredDepartureDelay() {
    ArrayList<TrainDeparture> expectedArrayList = new ArrayList<>();
    expectedArrayList.add(testDeparture2);
    expectedArrayList.add(testDeparture3);
    testDeparture3.setDelay(120);
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

}