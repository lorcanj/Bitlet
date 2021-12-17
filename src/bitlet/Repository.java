package bitlet;

import java.io.File;
import java.io.IOException;
import static bitlet.Utils.*;

// TODO: any imports you need here

/** Represents a bitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  This class provides an API for persisting objected created in the programme
 *  All persistence actions will be written and called from this Class
 *
 * .bitlet/ -- top level folder for all persistent data that is saved by Gitlet
 *      - commits/ -- folder containing all of the persistent data for commits
 *      - data -- will have hashed files of each commit serialised and saved
 *  @author Lorcan
 */
public class Repository {

    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));

    /** The .bitlet directory. */
    public static final File BITLET_DIR = join(CWD, ".bitlet");

    public static final File COMMIT_DIR = join(BITLET_DIR, "commits");

    public static final File DATA_DIR = join(BITLET_DIR, "data");

    public static final File STAGE = join(BITLET_DIR, "stage");
    /* TODO: fill in the rest of this class. */

    /**
     * If the directories below have not been created, then create these at the start
     * of the programme
     * Files created in these directories will be created elsewhere
     */
    public static void setupPersistence() {
        BITLET_DIR.mkdir();
        COMMIT_DIR.mkdir();
        DATA_DIR.mkdir();
        STAGE.mkdir();
    }

    public static void checkBitletDirExists() {
        if (!BITLET_DIR.exists()) {
            System.out.println("Please init a repository before trying other actions");
            System.exit(0);
        }
    }

    /**
     * Method to check whether the file name given exists in the current working directory for bitlet
     * @param fileName
     * @return true if file does not exist in CWD
     */
    public static boolean fileNotExistInCurrentDir(String fileName) {
        File fileToCheck = Utils.join(CWD, fileName);
        if (!fileToCheck.exists()) {
            return true;
        }
        return false;
    }





    /**
     *
     * @param fileName
     */
    public static void stageFile(String fileName) {

        File fileWithContentsToSave = Utils.join(CWD, fileName);
        File fileToStage = Utils.join(STAGE, fileName);
        try {
            fileToStage.createNewFile();
            writeContents(fileToStage, (Object) readContents(fileWithContentsToSave));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // need to serialize the commit and save it under the commits folder
    // to have the commit we need to get the hashcode of the commit to use as the name which then should
    // basically always be unique
    // when committing will need to access all of the data in the stage and save these to the given commit
    //



}
