package edu.ntnu.stud;

import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

/**
 * A class that manages the user interface for the train dispatch application.
 *
 * <p>The register field is of the type TrainDepartureRegister,
 * and will manage the TrainDepartures the interface will display.
 *
 * <p>The time field is of the type TramClock,
 * and will manage the time of the application.
 *
 * <p>The input field is of the type Scanner, and it will read the user input.
 *
 * <p>Lastly, there is multiple final static int used for the switch-cases.
 *
 * @author Jakob Huuse
 * @version 1.0.2
 * @since 12.12.2023
 */
public class TrainDispatchUserInterface {
  private TrainDepartureRegister register;
  private TramClock time;
  private Scanner input;
  private static final int PRINT_DEPARTURES = 1;
  private static final int NEW_DEPARTURE = 2;
  private static final int SEARCH_DESTINATION = 3;
  private static final int SEARCH_TRAIN_NUMBER = 4;
  private static final int ASSIGN_TRACK = 5;
  private static final int ADD_DELAY = 6;
  private static final int UPDATE_CLOCK = 7;
  private static final int EXIT = 9;

  /**
   * Initializes the fields with some test values. Also adds the register as a listener of time.
   */
  public void init() {
    register = new TrainDepartureRegister();
    register.addTrainDeparture(new TrainDeparture(LocalTime.of(13, 25), "F14", "608", "Oslo"));
    register.addTrainDeparture(
        new TrainDeparture(LocalTime.of(15, 15), "F15", "628", "Trondheim", 1));
    register.addTrainDeparture(new TrainDeparture(LocalTime.of(10, 25), "A125", "68", "Oslo", 2));
    time = new TramClock(LocalTime.of(0, 0));
    time.addListener(register);
    register.searchTrainNumber("628").setDelay(LocalTime.of(0, 29));
  }

  /**
   * Starts the user interface. It will run until the user exits it.
   * It will ask for input from the user, and if the user inputs something that throws an exception
   * it will catch it and print its message.
   */
  public void start() {
    while (true) {
      System.out.println(
          "\nWhat do you want to do? (Type the number with the function you want to use)");
      System.out.println("1. Print departures");
      System.out.println("2. Create new train departure");
      System.out.println("3. Search departure by destination");
      System.out.println("4. Search by train number");
      System.out.println("5. Assign new track to departure");
      System.out.println("6. Add delay to departure");
      System.out.println("7. Update clock");
      System.out.println("9. Exit");
      input = new Scanner(System.in);
      try {
        switch (input.nextInt()) {
          case PRINT_DEPARTURES -> System.out.println(this);
          case NEW_DEPARTURE -> {
            LocalTime departureTime = askTime("When is the departure time?");
            System.out.println("What is the line number? (Max 5 characters)");
            String lineNr = input.nextLine();
            System.out.println("What is the train number? (Max 5 characters)");
            String trainNr = input.nextLine();
            System.out.println("What is the destination? (Max 15 characters)");
            String destination = input.nextLine();
            System.out.println(
                "What track is it on? (Type an integer equal or lower than 0 if undefined)");
            int track = input.nextInt();
            if (track <= 0) {
              register.addTrainDeparture(
                  new TrainDeparture(departureTime, lineNr, trainNr, destination));
            } else {
              register.addTrainDeparture(
                  new TrainDeparture(departureTime, lineNr, trainNr, destination, track));
            }
          }
          case SEARCH_DESTINATION -> {
            System.out.println("What destination are you searching after?");
            input = new Scanner(System.in);
            String searchDestination = input.nextLine();
            final List<TrainDeparture> foundDestinations =
                register.searchDestination(searchDestination);

            StringBuilder searchString =
                new StringBuilder("Departures going to " + searchDestination);
            while (searchString.length() < 36) {
              searchString.append(" ");
            }
            searchString.append("Delay     Track     ETA");
            System.out.println(searchString);
            System.out.println("-------------------------------------------------------------");
            for (TrainDeparture departure : foundDestinations) {
              System.out.println(departure);

            }
          }
          case SEARCH_TRAIN_NUMBER -> {
            System.out.println("What train number are you searching after?");
            input = new Scanner(System.in);
            String searchTrainNr = input.nextLine();
            register.searchTrainNumber(searchTrainNr);
            StringBuilder searchNumberString =
                new StringBuilder("Departure with train number " + searchTrainNr);
            while (searchNumberString.length() < 36) {
              searchNumberString.append(" ");
            }
            searchNumberString.append("Delay     Track     ETA");
            System.out.println(searchNumberString);
            System.out.println("-------------------------------------------------------------");
            System.out.println(register.searchTrainNumber(searchTrainNr));
          }
          case ASSIGN_TRACK -> {
            System.out.println("What train number has the departure you want to switch tracks on?");
            input = new Scanner(System.in);
            String trackTrainNr = input.nextLine();
            System.out.println("What track do you want to switch to?");
            int swapTrack = input.nextInt();
            register.searchTrainNumber(trackTrainNr).setTrack(swapTrack);
            System.out.println(
                "Successfully swapped departure with train number " + trackTrainNr + " to track "
                    + swapTrack + "!");
          }
          case ADD_DELAY -> {
            System.out.println("What train number has the departure you want to add delay to?");
            input = new Scanner(System.in);
            String delayTrainNr = input.nextLine();
            LocalTime delay = askTime("What is the delay?");
            register.searchTrainNumber(delayTrainNr).setDelay(delay);
            System.out.println(
                "Successfully set delay of departure with train number " + delayTrainNr + " to "
                    + register.searchTrainNumber(delayTrainNr).getDelay() + "!");
          }
          case UPDATE_CLOCK -> {
            time.setTime(askTime("What do you want to set the time to?"));
            System.out.println(time);
          }
          case EXIT -> {
            return;
          }
          default -> System.out.println("Please give an integer on the list!");
        }
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      } catch (Exception e) {
        System.out.println("Input not valid!");
      }
    }
  }

  /**
   * Asks the user to input time and turns it into a LocalTime object of that time.
   *
   * @param message A string with the message you want to be displayed.
   * @return A LocalTime object defined by user input.
   */
  private LocalTime askTime(String message) {
    System.out.println(message + " (Give time in the format hh:mm)");
    input = new Scanner(System.in);
    String clockInput = input.nextLine();
    String[] splitClockInput = clockInput.split(":");

    return LocalTime.of(Integer.parseInt(splitClockInput[0]),
        Integer.parseInt(splitClockInput[1]));
  }

  @Override
  public String toString() {
    return time.toString() + "\n" + register.toString();
  }
}
