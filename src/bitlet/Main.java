package bitlet;

import static bitlet.Stage.getStageInstance;


/** Driver class for Bitlet, a subset of the Git version-control system.
 *  @author Lorcan
 */
public class Main {

    /** Usage: java bitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    // remember to make first before running your files as they won't do anything uncompiled
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(Utils.error("Please enter a valid command").getMessage());
            System.exit(0);
        }
        // the below deserialises the files in the stage if there are any still currently in the folder
        // that have not yet been committed
        Stage stage = getStageInstance();

        // want to set up the objects which we will use for example example the staging


        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // asserting that init should not have any other arguments with it
                validateNumArgs(firstArg, args, 1);
                // TODO: handle the `init` command
                // if the repo already exists just exit
                if (Repository.BITLET_DIR.exists()) {
                    System.out.println("A Bitlet version-control system already exists in the current directory.");
                    break;
                }
                Repository.setupPersistence();
                // TODO: need to create a commit with the default values which are shown in the requirements
                Commit.createFirstCommit();
                break;
            // for the below cases, need to check whether there is a .bitlet directory
            // in the current directory
            case "add":
                // first check that a .bitlet directory exists
                Repository.checkBitletDirExists();
                // then check that the correct number of arguments has been supplied
                validateNumArgs(firstArg, args, 2);
                // next need to check that the file they want to add actually exists in the current directory
                if (Repository.fileNotExistInCurrentDir(args[1])) {
                    System.out.println("No file with this name could be found");
                    break;
                }

                // at this point don't want to necessarily stage the file, because the file might exist in the
                // staging area and if the file and contents are the same then we should remove it from the stage

                // if the two files with the same name have the same contents then remove the file from the stage
                // and probably want to inform the user??


                // here only want to delete if the stage contains that file and the contents of the file are the same
                // checks whether the file is in the tree in O(log(N)) because data is kept in a BST
                if (Stage.getStageInstance().getStageTree().contains(args[1])) {
                    if (Repository.checkTwoFilesAreTheSame(args[1])) {
                        // TODO: remove the file from the stage and remove the file from the tree
                    }
                }
                // here we know that the staged file
                Repository.stageFile(args[1]);

                // do I need to save something in the data directory at this point? Probably not
                // add means we are adding a specific file to the stage
                // before adding the file we want to check whether it exists in the current directory and
                // whether it already exists in the stage, if it is then we overwrite that file

                // TODO: handle the `add [filename]` command
                break;
            case "merge":
                Repository.checkBitletDirExists();
                System.out.println("Please implement");
                break;
            case "show_stage":
                Repository.checkBitletDirExists();
                validateNumArgs(firstArg, args, 1);
                System.out.println("Please implement");
                break;
            // TODO: FILL THE REST IN
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
