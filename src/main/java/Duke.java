import java.util.Scanner;

public class Duke {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Task[] tasksList = new Task[100];

    private static int tasksCount = 0;
    public static final String LOGO = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";

    public static void main(String[] args) {
        sendWelcomeMessage();
        printLine();
        while(true) {
            String[] listOfUserInputs = getUserInput();
            String userCommand = listOfUserInputs[0];
            String inputDetails = listOfUserInputs[1];
            if (userCommand.equalsIgnoreCase("bye")){
                exitDuke();
                break;
            }
            if (userCommand.equalsIgnoreCase("list")){
                listOutTasks();
            } else if (userCommand.equalsIgnoreCase("done" )) {
                markTaskAsDone(inputDetails);
            } else  if (isValidInput(userCommand)){
                //process Todo, event or deadline
                processUserRequest(userCommand, inputDetails);
            } else {
                errorMessage();
            }
            printLine();
        }
    }

    private static void errorMessage() {
        System.out.println("Please check your spelling.");
    }

    private static boolean isValidInput(String userCommand) {
        return userCommand.equalsIgnoreCase("todo") | userCommand.equalsIgnoreCase("deadline") | userCommand.equalsIgnoreCase("event");
    }

    private static void sendWelcomeMessage() {
        System.out.println("Hello from\n" + LOGO);
        printLine();
        System.out.println("Hello! I'm Duke");
        System.out.println("What can I do for you?");
    }

    private static void processUserRequest(String userCommand, String inputDetails) {
        printLine();
        tasksList[tasksCount] = new Task(inputDetails, userCommand);
        Task selectedTask = tasksList[tasksCount];
        tasksCount++;
        notifyUser(inputDetails, selectedTask);

    }

    private static void notifyUser(String inputDetails, Task selectedTask) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + selectedTask.getTaskType() + selectedTask.getStatusIcon() + " " + selectedTask.description);
        System.out.println("Now you have " + tasksCount + " tasks in the list.");
    }

    private static void markTaskAsDone(String s) {
        int taskNumber = Integer.parseInt(s);
        taskNumber--;
        Task selectedTask = tasksList[taskNumber];
        selectedTask.markAsDone();
        System.out.println("Nice! Following task is now marked as done:");
        System.out.println("[X] " + selectedTask.description);
    }

    private static void exitDuke() {
        printLine();
        System.out.println("Bye. Hope to see you again soon!");
    }

    private static void printLine() {
        System.out.println("---------------------------------------------------");
    }


    private static String[] getUserInput() {
        String userInput = SCANNER.nextLine();
        String[] listOfInputs = userInput.split(" ", 2);
        if (listOfInputs.length == 1) {
            listOfInputs = new String[]{userInput, "filler"};
        }
        return listOfInputs;
    }


    private static void listOutTasks() {
        int i = 0;
        while (i < tasksCount) {
            Task selectedTask = tasksList[i];
            i++;
            System.out.println(i + ". " + selectedTask.getTaskType() + selectedTask.getStatusIcon() + " " + selectedTask.description);
        }
    }

}
