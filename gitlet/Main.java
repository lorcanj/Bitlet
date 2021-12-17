package gitlet;

import java.util.Calendar;
import java.util.Date;
import java.time.Instant;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    // remember to make first before running your files as they won't do anything uncompiled
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(Utils.error("Please enter a valid command").getMessage());
            System.exit(0);
        }
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // asserting that init should not have any other arguments with it
                validateNumArgs(firstArg, args, 1);
                // TODO: handle the `init` command
                // if the repo already exists just exit
                if (Repository.GITLET_DIR.exists()) {
                    System.out.println("A Gitlet version-control system already exists in the current directory.");
                    break;
                }
                Repository.setupPersistence();
                // need to create a commit with the default values which are shown in the requirements


                break;
            // for the below cases, need to check whether there is a .gitlet directory
            // in the current directory
            case "add":
                Repository.checkGitletDirExists();
                validateNumArgs(firstArg, args, 2);
                // need to first check that that file name actually exists in the current directory
                Repository.checkFileExistsInDirectory(args[1]);
                // add means we are adding a specific file to the stage
                // before adding the file we want to check whether it exists in the current directory and
                // whether it already exists in the stage, if it is then we overwrite that file

                // TODO: handle the `add [filename]` command
                break;
            case "merge":
                Repository.checkGitletDirExists();
            // TODO: FILL THE REST IN
                break;
            default:
                Utils.error("Please enter a valid action");
                System.exit(0);
        }
    }

    /**
     * Checks the number of arguments versus the expected number,
     * throws a RuntimeException if they do not match.
     *
     * @param cmd Name of command you are validating
     * @param args Argument array from command line
     * @param n Number of expected arguments
     */
    public static void validateNumArgs(String cmd, String[] args, int n) {
        if (args.length != n) {
            System.out.println("Incorrect number of arguments for argument: " + args[0]);
            System.exit(0);
        }
    }
}
