package bitlet;

import java.io.File;
// not sure whether want a TreeMap or TreeSet to store the files in the stage
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Stage class is using the Singleton paradigm because should only have one stage per Bitlet repo
 * Once a user has commited the files, then the stage should be emptied of all of the files in the stage
 * And singleStageInstance should be set to null
 */

// when staging a file need to know not only know whether the file exists but also that the content of the file is the same
// which means we need to hash the file name and the contents of the file to know whether the two files are the same or not

public class Stage {

    private static Stage singleStageInstance;
    private int numberOfFiles = 0;
    private TreeSet<String> stageTree;


    private Stage() {
        stageTree = new TreeSet<String>();
        File[] directoryListing = Repository.STAGE.listFiles();
        if (directoryListing == null || directoryListing.length == 0) {
            System.out.println("No files in the stage which need to be completed");
        } else {
            for (File individualFile : directoryListing) {
                numberOfFiles += 1;
                // below we are hashing just the name of the file
                //String hashOfFile = Utils.sha1(individualFile.getName());
                stageTree.add(individualFile.getName());
            }
        }
    }

    /**
     * public constructor for a stage that ensures that only a maximum of a single instance of Stage can exist
     * at any single point
     * @return either a newly created or the previously created Stage object
     */
    public static Stage getStageInstance() {
        if (singleStageInstance == null) {
            singleStageInstance = new Stage();
        }
        return singleStageInstance;
    }

    public TreeSet<String> getStageTree() {
        return stageTree;
    }

    public boolean checkFileExistsInStage(String fileName) {
        return stageTree.contains(fileName);
    }

}
