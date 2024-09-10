package hypebot.storage;

import hypebot.task.Deadline;
import hypebot.task.Event;
import hypebot.task.Task;
import hypebot.task.TaskDateTimeParseException;
import hypebot.task.ToDo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static hypebot.common.Messages.ERROR_LOAD_TASKLIST;

public class TasklistDecoder {
    private File tasklistFile;

    public TasklistDecoder(File tasklistFile) {
        this.tasklistFile = tasklistFile;
    }

    private Task loadTask(String[] taskTextLineElements) throws TaskDateTimeParseException {
        String taskType = taskTextLineElements[0];
        String taskName = taskTextLineElements[2];
        Task newTask = null;
        switch (taskType) {
        case "T":
            newTask = new ToDo(taskName);
            break;
        case "D":
            String dueDate = taskTextLineElements[3];
            newTask = new Deadline(taskName, dueDate);
            break;
        case "E":
            String startTime = taskTextLineElements[3];
            String endTime = taskTextLineElements[4];
            newTask = new Event(taskName, startTime, endTime);
            break;
        }
        return newTask;
    }

    public ArrayList<Task> decode() throws FileNotFoundException {
        if (!tasklistFile.exists()) {
            throw new FileNotFoundException(ERROR_LOAD_TASKLIST);
        }
        Scanner scanner = new Scanner(tasklistFile);
        ArrayList<Task> tasks = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String taskTextLine = scanner.nextLine();
            String[] taskTextLineElements = taskTextLine.split(" , ");
            Task newTask = loadTask(taskTextLineElements);
            if (newTask != null && taskTextLineElements[1].equals("1")) {
                newTask.mark();
            }
            tasks.add(newTask);
        }
        scanner.close();
        return tasks;
    }
}
