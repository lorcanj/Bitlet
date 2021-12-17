package bitlet;

import java.io.File;
// not sure whether want a TreeMap or TreeSet to store the files in the stage
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Stage class is using the Singleton paradigm because should only have one stage per Bitlet repo
 */
public class Stage {

    private static Stage singleStageInstance;
    private int numberOfFiles = 0;
    private TreeSet<String> tree;


    private Stage() {
        tree = new TreeSet();
        File[] directoryListing = Repository.STAGE.listFiles();
        if (directoryListing == null || directoryListing.length == 0) {
            System.out.println("No files in the stage which need to be completed");
        } else {
            for (File individualFile : directoryListing) {
                numberOfFiles += 1;
                String hashOfFile = Utils.sha1(individualFile.toString());
                tree.add(hashOfFile);
            }
        }
    }

    /**
     *
     * @return
     */
    public static Stage getInstance() {
        if (singleStageInstance == null) {
            singleStageInstance = new Stage();
        }
        return singleStageInstance;
    }

}
