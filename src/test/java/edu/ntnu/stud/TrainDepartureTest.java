package edu.ntnu.stud;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(13, testObj.getDepartureTime().getHour(), "The hour of the departure should be 13");
        assertEquals(25, testObj.getDepartureTime().getMinute(), "The minutes of the departure should be 25");
        assertEquals("F14", testObj.getLine(), "The line should be F14");
        assertEquals("608", testObj.getTrainNumber(), "The train number should be 608");
        assertEquals("Drammen", testObj.getDestination(), "The destination should be Drammen");
        assertEquals(-1, testObj.getTrack(), "The track-number should be -1 if not specified");
        assertEquals(2, secondTestObj.getTrack(), "The track-number of secondTestObj should be 2");
    }

    @Test
    @DisplayName("Check if the validator works")
    void testValidator() {
        Exception  departureTimeException = assertThrows(IllegalArgumentException.class, () -> {
            new TrainDeparture(LocalTime.of(13, 25, 22), "F14", "608", "Drammen");}, "Validator should throw");
        assertEquals(departureTimeException.getMessage(), "departureTime cannot contain time-units lower than minutes!");

        Exception  firstLineException = assertThrows(IllegalArgumentException.class, () -> {
            new TrainDeparture(LocalTime.of(13, 25), "314", "608", "Drammen");}, "Validator should throw");
        assertEquals(firstLineException.getMessage(), "The first character for the line can't be a number!");

        Exception  secondLineException = assertThrows(IllegalArgumentException.class, () -> {
            new TrainDeparture(LocalTime.of(13, 25), "FF15", "608", "Drammen");}, "Validator should throw");
        assertEquals(secondLineException.getMessage(), "Every character after the first for the line must be a number!");

        Exception  trainNumberException = assertThrows(IllegalArgumentException.class, () -> {
            new TrainDeparture(LocalTime.of(13, 25), "F14", "FF15", "Drammen");}, "Validator should throw");
        assertEquals(trainNumberException.getMessage(), "Every character in the trainNumber must be a number!");
    }

    @Test
    @DisplayName("Check if checkTrack works")
    void testCheckTrack() {
        Exception checkTrackException = assertThrows(IllegalArgumentException.class, () -> {
            testObj.setTrack(0);
        });
        assertEquals(checkTrackException.getMessage(), "The track must be a positive integer!");

        Exception checkSetTrackException = assertThrows(IllegalArgumentException.class, () -> {
            secondTestObj.setTrack(3);
        });
        assertEquals(checkSetTrackException.getMessage(), "The track has already been set!");

        testObj.setTrack(1);
        assertEquals(1, testObj.getTrack());
    }
}
