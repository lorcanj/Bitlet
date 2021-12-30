package bitlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static bitlet.Stage.getStageInstance;
import java.time.Instant;

/** Driver class for Bitlet, a subset of the Git version-control system.
 *  @author Lorcan
 */
public class Main {

    /** Usage: java bitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println(Utils.error("Please enter a valid command").getMessage());
            System.exit(0);
        }
        // TODO: move the below to the CommitGraph Class
        // TODO: move move most of processing here to CommitGraph
        Stage stage = getStageInstance();
        HashMap<String, Commit> runTimeCommitMap = Utils.createRunTimeCommitMap();
        String firstArg = args[0];

        switch(firstArg) {
            case "init":
                // asserting that init should not have any other arguments with it
                validateNumArgs(firstArg, args, 1);
                // if the repo already exists just exit
                if (Repository.BITLET_DIR.exists()) {
                    System.out.println("A Bitlet version-control system already exists in the current directory.");
                    break;
                }
                Repository.setupPersistence();
                Commit.createFirstCommit();
                // TODO: once the commit has been created it will need to be added to the commit graph
                break;
            case "add":

                Repository.checkBitletDirExists();
                validateNumArgs(firstArg, args, 2);
                if (Repository.fileNotExistInCurrentDir(args[1])) {
                    System.out.println("No file with this name could be found");
                    break;
                }
                File fileToStage = Utils.join(Repository.CWD, args[1]);
                // want to get the latest commit, first get the file that is saved
                File latestCommit = Utils.join(Repository.BRANCH_DIR, "main");
                System.out.println(Utils.readContentsAsString(latestCommit));
                // Then use the contents of the file to find the commit hash in the branch directory
                Commit c = Utils.readObject(Utils.join(Repository.COMMIT_DIR, Utils.readContentsAsString(latestCommit)),Commit.class);
                System.out.println(c.getMessage());
                //System.out.println(c.getFilesToData().size());
                //TODO: for some reason the filesToData is empty when the commit is deserialised
                if (c.getFilesToData() != null && c.getFilesToData().size() > 0) {
                    // first check if the previous commit contained the file that we are adding
                    if (c.getFilesToData().containsKey(args[1])) {
                        System.out.println(c.getFilesToData().get(args[1]));
                        if (c.getFilesToData().get(args[1]).equals(Utils.sha1(args[1] + Utils.readContentsAsString(fileToStage)))) {
                            System.out.println("The file you want to commit is the same as the latest commit and cannot be added");
                            break;
                        }
                    }
                }
                Repository.stageFile(args[1]);
                break;
            case "commit":
                //TODO: handle the commit command to actually create a new commit and empty the stage
                Repository.checkBitletDirExists();
                validateNumArgs(firstArg, args, 2);

                // the below creates a new commit object
                // the commit also contains a TreeMap that maps the files to the data hashes in the data directory
                Commit newCommit = new Commit(args[1], Instant.EPOCH, "main");

                Repository.moveFilesToData();

                // want to do this in the commit class
                byte[] commitByteArray = Utils.serialize(newCommit);
                String commitHash = Utils.sha1(commitByteArray);
                File commitFile = Utils.join(Repository.COMMIT_DIR, commitHash);
                File head = Utils.join(Repository.BRANCH_DIR, newCommit.getBranch());
                Utils.writeContents(commitFile, commitByteArray);
                Utils.writeContents(head, commitHash);

                try {
                    commitFile.createNewFile();
                    head.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(runTimeCommitMap.size());

                for (String element : runTimeCommitMap.keySet()) {
                    System.out.println(element);
                }
                //Commit c = runTimeCommitMap.get(Utils.join(Repository.BRANCH_DIR, "main").getName());
                //System.out.println(c.getParent());

                // next want to remove the files in the stage
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
