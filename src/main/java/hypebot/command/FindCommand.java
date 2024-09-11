package hypebot.command;

import hypebot.storage.StorageManager;
import hypebot.tasklist.Tasklist;
import hypebot.ui.Ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the FindCommand created when user prompts 'find {keywords}'.
 *
 * @author Youngseo Park (@youngseopark05)
 */
public class FindCommand extends Command {
    private Pattern searchQuery;

    /**
     * Takes in a search query containing keywords to search for in HypeBot's Tasklist
     * and creates a new FindCommand.
     *
     * @param searchQuery Search query containing keywords to search for in the Task names.
     */
    public FindCommand(String searchQuery) {
        super();
        Pattern searchPattern = Pattern.compile(
                searchQuery.replace(" ", "|").substring(0, searchQuery.length() - 1),
                Pattern.CASE_INSENSITIVE);
        this.searchQuery = searchPattern;
    }

    /**
     * Triggers Tasklist to return a new Tasklist containing Tasks with any of the
     * keywords in the search query, then triggers Ui to output Tasks in this
     * Tasklist onto user interface.
     *
     * @param tasks Tasklist containing Tasks.
     * @param ui User interface that user interacts with.
     * @param storageManager StorageManager containing File where tasks are loaded / saved.
     */
    @Override
    public void execute(Tasklist tasks, Ui ui, StorageManager storageManager) {
        Tasklist tasksWithSearchQuery = tasks.getNameContains(searchQuery);
        ui.showTasksWithSearchQuery(String.valueOf(searchQuery), tasksWithSearchQuery);
    }
}
