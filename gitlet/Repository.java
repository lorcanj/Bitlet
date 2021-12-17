package gitlet;

import java.io.File;
import java.io.IOException;
import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  This class provides an API for persisting objected created in the programme
 *  All persistence actions will be written and called from this Class
 *
 * .gitlet/ -- top level folder for all persistent data that is saved by Gitlet
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
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    public static final File COMMIT_DIR = join(GITLET_DIR, "commits");

    public static final File BLOB_DIR = join(GITLET_DIR, "blobs");

    public static final File STAGE = join(GITLET_DIR, "stage");
    /* TODO: fill in the rest of this class. */

    /**
     * If the directories below have not been created, then create these at the start
     * of the programme
     * Files created in these directories will be created elsewhere
     */
    public static void setupPersistence() {
        GITLET_DIR.mkdir();
        COMMIT_DIR.mkdir();
        BLOB_DIR.mkdir();
        STAGE.mkdir();
    }

    public static void checkGitletDirExists() {
        if (!GITLET_DIR.exists()) {
            System.out.println("Please init a repository before trying other actions");
            System.exit(0);
        }
    }

    public static void checkFileExistsToBeAdded(String fileName) {
        File fileToCheck = Utils.join(CWD, fileName);

    }

    public static void checkFileExistsInDirectory(String fileName) {
        File fileToCheck = Utils.join(Repository.STAGE, fileName);

        /**
         * Atomically creates a new, empty file named by this abstract pathname if and only if
         * a file with this name does not yet exist.
         */

        // want to get the contents of the file from the current directory
        // and save it to the newly created file
        try {
            if (fileToCheck.createNewFile()) {
                System.out.println("A new file was created");
            }
            else {
                System.out.println("A file with this name already exists in the staging area and not yet implemented");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // need to serialize the commit and save it under the commits folder
    // to have the commit we need to get the hashcode of the commit to use as the name which then should
    // basically always be unique
    // when committing will need to access all of the data in the stage and save these to the given commit
    //
    public static void persistCommit(Commit commit) {

    }


}
