package edu.ntnu.stud;

import java.time.LocalTime;
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
 * <p>The field inp is of the type Scanner, and will take the users input.
 *
 * @author Jakob Huuse
 * @version 1.0.0
 * @since 14.11.2023
 */
public class TrainDispatchUserInterface {
  private TrainDepartureRegister register;
  private TramClock time;

  private Scanner inp;

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
    inp = new Scanner(System.in);
  }

  /**
   * Starts the user interface. It will run until the user exits it.
   * It will ask for input from the user, and if the user inputs something that throws an exception
   * it will catch it and print its message.
   */
  public void start() {
    while (true) {
      System.out.println("What do you want to do?");
      System.out.println("1. Print departures");
      System.out.println("2. Create new train departure");
      System.out.println("3. Search departure by destination");
      System.out.println("4. Search by train number");
      System.out.println("5. Assign new track to departure");
      System.out.println("6. Add delay to departure");
      System.out.println("7. Update clock");
      System.out.println("9. Exit");
      try {
        switch (inp.nextInt()) {
          case 1:
            System.out.println(this);
            break;
          case 2:
            System.out.println("When is the departure time? (Give time in the format hh:mm)");
            inp = new Scanner(System.in);
            String timeInput = inp.nextLine();
            String[] splitTimeInput = timeInput.split(":");
            LocalTime time = LocalTime.of(Integer.parseInt(splitTimeInput[0]),
                Integer.parseInt(splitTimeInput[1]));
            System.out.println("What is the line number?");
            String lineNr = inp.nextLine();
            System.out.println("What is the train number?");
            String trainNr = inp.nextLine();
            System.out.println("What is the destination?");
            String destination = inp.nextLine();
            System.out.println(
                "What track is it on? (Type an integer equal or lower than 0 if undefined)");
            int track = inp.nextInt();
            if (track <= 0) {
              register.addTrainDeparture(new TrainDeparture(time, lineNr, trainNr, destination));
            } else {
              register.addTrainDeparture(
                  new TrainDeparture(time, lineNr, trainNr, destination, track));
            }
            break;
          case 3:
            System.out.println("What destination are you searching after?");
            inp = new Scanner(System.in);
            String searchDestination = inp.nextLine();
            System.out.println("Departures going to " + searchDestination + "          Track" +
                "      Train Number");
            System.out.println("---------------------------------------------------------");
            for (TrainDeparture departure : register.searchDestination(searchDestination)) {
              System.out.println(departure);
            }
            System.out.println("\n");
            break;
          case 4:
            System.out.println("What train number are you searching after?");
            inp = new Scanner(System.in);
            String searchTrainNr = inp.nextLine();
            System.out.println("Departure with train number " + searchTrainNr + "   Track" +
                "      Train Number");
            System.out.println("---------------------------------------------------------");
            System.out.println(register.searchTrainNumber(searchTrainNr));
            System.out.println("\n");
            break;
          case 5:
            System.out.println("What train number has the departure you want to switch tracks on?");
            inp = new Scanner(System.in);
            String trackTrainNr = inp.nextLine();
            System.out.println("What track do you want to switch to?");
            int swapTrack = inp.nextInt();
            register.searchTrainNumber(trackTrainNr).setTrack(swapTrack);
            System.out.println(
                "Successfully swapped departure with train number " + trackTrainNr + " to track " +
                    swapTrack + "! \n");
            break;
          case 6:
            System.out.println("What train number has the departure you want to add delay to?");
            inp = new Scanner(System.in);
            String delayTrainNr = inp.nextLine();
            System.out.println("How many minutes is it delayed?");
            long delay = inp.nextLong();
            register.searchTrainNumber(delayTrainNr).setDelay(delay);
            System.out.println(
                "Successfully set delay of departure with train number " + delayTrainNr + " to " +
                    delay + " minutes! \n");
            break;
          case 7:

            break;
          case 9:
            return;
          default:
            System.out.println("Please give an integer on the list");
            break;
        }
      } catch (Exception e) {
        System.out.println("Input not valid: " + e.getMessage());
      }
    }
  }

  @Override
  public String toString() {
    return time.toString() + "\n" + register.toString();
  }
}
