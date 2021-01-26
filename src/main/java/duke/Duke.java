package duke;

import duke.command.Command;
import duke.exception.DukeException;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

public class Duke {
    private Ui ui;
    private TaskList tasks;
    private Storage storage;

    public Duke() {
        this.ui = new Ui();
        this.tasks = new TaskList();
        this.storage = new Storage();
        try {
            storage.readFile(tasks);
        } catch (DukeException e) {
            ui.printError(e.getMessage());
        }
    }

    private static final String[] commands = { "bye", "list", "done", "todo", "deadline", "event",
            "delete"};

    private static final String PARENT_DIRECTORY = "data";
    private static final String FILENAME = "duke.txt";

//    // Iterates through ArrayList and prints each element.
//    public void iterateList() {
//        String outputString = "";
//
//        for (int i = 0; i < tasks.size(); i++) {
//            outputString += duke.ui.formatString((i + 1) + ". " + tasks.get(i));
//        }
//
//        duke.ui.display(outputString);
//    }

//    // Marks duke.task.Task at index number to the desired boolean value.
//    public void setTask(int index, boolean b) {
//        duke.task.Task targetTask = tasks.get(index - 1);
//        targetTask.setDone(b);
//    }

    public void checkInput(String input) throws DukeException {
        String[] splittedInput = input.split(" ");
        for (String command : commands) {
            if (splittedInput[0].equals(command)) {
                break;
            } else {
                throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that " +
                        "means :-(");
            }
        }
    }

    public void checkArgument(String task, String input) throws DukeException {
        String[] splittedInput = input.split(" ");
        if (splittedInput.length == 1) {
            throw new DukeException("☹ OOPS!!! The description of a " + task +
                    " cannot be empty.");
        }
    }

    public void run() {
        ui.showGreeting();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExitCommand();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Duke().run();
//        new duke.Duke().run();
//
//        Scanner sc = new Scanner(System.in);
//        boolean toExit = false;
//        while (!toExit) {
//            String input = sc.nextLine();
//            String[] arr = input.split(" ", 2);
//
//            // First word of input is used as switch argument.
//            switch (arr[0]) {
//            case "bye":
//                duke.exit();
//                toExit = true;
//                break;
//            case "list":
//                duke.iterateList();
//                break;
//            case "done":
//
//                try {
//                    duke.checkArgument("done", input);
//                } catch (duke.exception.DukeException de){
//                    duke.printDivider();
//                    duke.indentedPrint(de.getMessage());
//                    duke.printDivider();
//                    break;
//                }
//
//                int index = Integer.parseInt(arr[1]);
//                duke.setTask(index, true);
//
//                duke.printDivider();
//                duke.indentedPrint("Nice! I've marked this duke.task as done:");
//                duke.indentedPrint(duke.getTask(index).toString());
//                duke.printDivider();
//                break;
//            case "todo":
//
//                try {
//                    duke.checkArgument("todo", input);
//                } catch (duke.exception.DukeException de){
//                    duke.printDivider();
//                    duke.indentedPrint(de.getMessage());
//                    duke.printDivider();
//                    break;
//                }
//
//                duke.task.Todo t = new duke.task.Todo(arr[1]);
//                duke.addTask(t);
//
//                duke.printDivider();
//                duke.indentedPrint("Got it. I've added this duke.task:");
//                duke.indentedPrint(" " + t.toString());
//                duke.indentedPrint("Now you have " + duke.getTasksSize() +
//                        " duke.task(s) in the list.");
//                duke.printDivider();
//                break;
//            case "deadline":
//
//                try {
//                    duke.checkArgument("deadline", input);
//                } catch (duke.exception.DukeException de){
//                    duke.printDivider();
//                    duke.indentedPrint(de.getMessage());
//                    duke.printDivider();
//                    break;
//                }
//
//                String[] dSplit = arr[1].split(" /by ");
//                duke.task.Deadline d = new duke.task.Deadline(dSplit[0], dSplit[1]);
//                duke.addTask(d);
//
//                duke.printDivider();
//                duke.indentedPrint("Got it. I've added this duke.task:");
//                duke.indentedPrint(" " + d.toString());
//                duke.indentedPrint("Now you have " + duke.getTasksSize() +
//                        " duke.task(s) in the list.");
//                duke.printDivider();
//                break;
//            case "event":
//                String[] eSplit = arr[1].split(" /at ");
//                duke.task.Event e = new duke.task.Event(eSplit[0], eSplit[1]);
//                duke.addTask(e);
//
//                duke.printDivider();
//                duke.indentedPrint("Got it. I've added this duke.task:");
//                duke.indentedPrint(" " + e.toString());
//                duke.indentedPrint("Now you have " + duke.getTasksSize() +
//                        " duke.task(s) in the list.");
//                duke.printDivider();
//                break;
//            case "delete":
//                try {
//                    duke.checkArgument("delete", input);
//                } catch (duke.exception.DukeException de){
//                    duke.printDivider();
//                    duke.indentedPrint(de.getMessage());
//                    duke.printDivider();
//                    break;
//                }
//
//                int index1 = Integer.parseInt(arr[1]);
//
//                duke.printDivider();
//                duke.indentedPrint("Noted. I've removed this duke.task: ");
//                duke.indentedPrint(" " + duke.getTask(index1).toString());
//                duke.indentedPrint("Now you have " + (duke.getTasksSize() - 1) +
//                        " duke.task(s) in the list.");
//                duke.printDivider();
//
//                duke.deleteTask(index1);
//                break;
//            default:
//                duke.printDivider();
//
//                try {
//                    duke.checkInput(input);
//                } catch (duke.exception.DukeException de) {
//                    duke.indentedPrint(de.getMessage());
//                }
//
//                duke.printDivider();
//                break;
//            }
//        }
    }
}