package Duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DukeStorage {
    public static final String FILE_PATH_TO_SAVE_TASKS = "duke.txt";

    public static void createFileIfThereIsNone() {
        File toCheck = new File(FILE_PATH_TO_SAVE_TASKS);
        if (!toCheck.exists()) {
            createNewFile(toCheck);
        }
    }

    private static void createNewFile(File fForCheck) {
        try {
            fForCheck.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void emptyFileAfterInitializing() {
        FileWriter fw = null;
        fw = createFileWriterObject(null);
        writeEmptyStringToFile(fw);
    }

    private static FileWriter createFileWriterObject(FileWriter fw) {
        try {
            fw = new FileWriter(FILE_PATH_TO_SAVE_TASKS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fw;
    }

    private static void writeEmptyStringToFile(FileWriter fw) {
        try {
            fw.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TaskManager loadData() {
        final TaskManager taskManager = new TaskManager();
        File f = new File(FILE_PATH_TO_SAVE_TASKS);
        DukeUI.print("Loading data");
        try {
            Scanner s = new Scanner(f);
            while(s.hasNext()) {
                String[] listOfDataFromFile = DukeParser.splitInputIntoString(s.nextLine());
                String userCommand = listOfDataFromFile[0];
                String inputDetails = listOfDataFromFile[1];
                Task newTask = DukeParser.processSavedData(userCommand, inputDetails);
                taskManager.addTask(newTask);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        emptyFileAfterInitializing();
        return taskManager;
    }

    private static void writeToFile(String filePath, Task tasks) throws IOException {
        FileWriter fw = new FileWriter(filePath, true);
        String description = tasks.getDescriptionWithoutBrackets();
        String taskType = tasks.getTaskTypeInWords();
        Boolean status = tasks.getStatusInWords();
        if (status) {
            fw.write(taskType + "done " + description + "\n");
        } else {
            fw.write(taskType + ' ' + description + "\n");
        }
        fw.close();
    }

    private static void exitDuke(TaskManager latestTaskManager) throws IOException {
        DukeUI.printLine();
        ArrayList<Task> finalTasksList = latestTaskManager.returnTaskList();
        for (Task task : finalTasksList) {
            try {
                writeToFile(FILE_PATH_TO_SAVE_TASKS, task);
            } catch (IOException e) {
                DukeUI.print("Something went wrong: " + e.getMessage());
            }
        }
        DukeUI.printExitingMessage();
    }

    public static void endDuke(TaskManager latestTaskManager) {
        try {
            exitDuke(latestTaskManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
