package fall2018.csc207.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.regex.Pattern;

/**
 * Helps with loading and saving GameStates.
 */
public class GameStateIO {

    /**
     * The folder that holds all the saves for this game.
     */
    private final File gameFolder;

    /**
     * Constructs GameStateIO.
     *
     * @param username The username of the current user.
     * @param gameName The name of the current game.
     * @param filesDir The location of the files/ directory. Typically retrieved through
     *                 context.getFilesDir()
     */
    public GameStateIO(String username, String gameName, File filesDir) {
        this.gameFolder = new File(
                filesDir, String.format("%s/%s", username, gameName));
        gameFolder.mkdirs(); // Makes the directories just in case they don't exist.
    }

    /**
     * Constructs GameStateIO.
     *
     * @param file The file to construct GameStateIO from.
     *             This should be pointing to a save file (and not a folder).
     *             We assume the file exists.
     */
    public GameStateIO(File file) {
        this.gameFolder = file.getParentFile();
    }

    /**
     * Retrieves all the file names that are saved for the current game.
     *
     * @return A list of String that can be used in getGameSave to retrieve the GameState.
     */
    public File[] getAllSaves() {
        return gameFolder.listFiles();
    }

    /**
     * Determines if the given string is a valid file name for a new game.
     *
     * @param name The string to test.
     * @return Whether the file name is unused and valid.
     */
    public boolean isValidUnusedFileName(String name) {
        File file = new File(gameFolder, name);

        // If the name matches the regex and it doesn't exist already,
        // then it's a valid unused file name.
        return Pattern.matches("^[\\w ]+$", name)
                && !file.exists();
    }

    /**
     * Loads a GameState from the given file.
     *
     * @param file The file to load.
     * @return The loaded GameState.
     */
    public GameState getGameSave(String file) throws IOException, ClassNotFoundException {
        try (InputStream inputStream = new FileInputStream(getFile(file))) {
            ObjectInputStream input = new ObjectInputStream(inputStream);
            return (GameState) input.readObject();
        }
    }

    /**
     * Saves a given GameState.
     *
     * @param state    The GameState to save.
     * @param fileName The file name to save the state to.
     */
    public void saveState(GameState state, String fileName) throws IOException {
        File saveDir = getFile(fileName);
        FileOutputStream fos = new FileOutputStream(saveDir);
        ObjectOutput oos = new ObjectOutputStream(fos);

        oos.writeObject(state);
        oos.close();
    }

    /**
     * Deletes a save file from the given fileName.
     * @param fileName The file to delete.
     */
    public void deleteSave(String fileName) throws IOException {
        if (!getFile(fileName).delete())
            throw new IOException(String.format("%s could not be deleted!", fileName));
    }

    /**
     * Deletes the save from the given File.
     * @param file The File to delete.
     */
    public void deleteSave(File file) throws IOException {
        if (!file.delete())
            throw new IOException(String.format("%s could not be deleted!", file.toString()));
    }

    /**
     * Retrieves the File for a particular fileName. Also creates the directories
     * if they don't already exist.
     *
     * @param fileName The name of the file.
     * @return The File with path "getFilesDir()/username/gameName/fileName"
     */
    private File getFile(String fileName) {
        return new File(gameFolder, fileName);
    }
}
