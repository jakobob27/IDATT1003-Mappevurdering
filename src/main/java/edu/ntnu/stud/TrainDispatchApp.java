package edu.ntnu.stud;

/**
 * This is the main class for the train dispatch application.
 */
public class TrainDispatchApp {
  /**
   * Main method for the train dispatch application.
   */
  public static void main(String[] args) {
    TrainDispatchUserInterface app = new TrainDispatchUserInterface();
    app.init();
    app.start();
  }
}
