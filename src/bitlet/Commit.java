package bitlet;


import jdk.jshell.execution.Util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

// commits will need a hashtable to map the file name to the blob
// will need to think whether need blobs to be an object or just a hash of the file it points to itself

// each commit can have multiple files which are being committed

// first iteration will only allow 1 file to be added (i.e. staged)
// can add multiple times to add these files to the staging area
// when commit, will create a new commit object with

/** Represents a bitlet commit object.
 */
public class Commit implements Serializable {

    // for each commit there needs to be a mapping from the name of the file to the blob, which contains the
    // file itself, which should just be a mapping to the hashcode of the file itself, which we can then

    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /**
     * A commit, therefore, will consist of:
     * - the metadata listed below:
     *      - a log message
     *      - timestamp
     *      - author
     * - a mapping of filenames to blob references
     * - a parent reference
     * - (For merges) a second parent reference
     * - a reference to a tree
     */

    /** The message of this Commit. */
    private String message;
    private Instant date;
    private String branch;

    // the parent will be the SHA1 hash of the parent commit
    // the first commit will have a parent of null

    private String parent;

    // Each commit will have a treeMap linking the file name to the blob hash (which is the hash of the name of the file
    // and the contents of the file
    private TreeMap<String, String> filesToData;

    // TODO: add unit tests for this
    @SuppressWarnings(value = "unchecked")
    public TreeMap<String, String> mapFilesToData(String branch) {
        // this should give us the file to the head of the branch 'branch'
        File previousCommit = Utils.join(Repository.BRANCH_DIR, branch);
        Commit c = Utils.readObject(Utils.join(Repository.COMMIT_DIR, Utils.readContentsAsString(previousCommit)), Commit.class);
        System.out.println(c.getMessage());
        if (c.filesToData == null) {
            filesToData = new TreeMap<String, String>();
        } else {
            filesToData = (TreeMap<String, String>) c.filesToData.clone();
        }

        File[] listOfFiles = Utils.join(Repository.STAGE).listFiles();
        System.out.println(listOfFiles.length);
        if (listOfFiles == null) {
            return filesToData;
        }
        for (File file : listOfFiles) {
            // below check that the file name is the same and the hashed contents are the same
            // if so then just remove this file from the stage as no different from previous commit
            if (filesToData.containsKey(file.getName()) &&
                    filesToData.get(file.getName()).equals(Utils.sha1(file.getName() + Utils.readContentsAsString(file)))){
                    Utils.restrictedDelete(file);
                }
            // if the map contains the key, update the hash with the new contents from the stage
            else if (filesToData.containsKey(file.getName())) {
                filesToData.replace(file.getName(), Utils.sha1(file.getName() + Utils.readContentsAsString(file)));
            }
            // if file not in the map, then add it to the map
            else {
                filesToData.put(file.getName(), file.getName() + Utils.sha1(Utils.readContentsAsString(file)));
            }
        }
        return filesToData;
    }

    public Commit(String message, Instant date, String branch, String parent) {
        this.message = message;
        this.date = date;
        this.branch = branch;
        this.parent = parent;
    }

    public TreeMap<String, String> getFilesToData() {
        return filesToData;
    }

    // TODO: want to update the files to data
    // also want to update the Commit so it saves the file, or should this be done in Repo
    // and then called in the Commit class
    public Commit(String message, Instant date, String branch) {
        this.message = message;
        this.date = date;
        this.branch = branch;
        // we can get the parent commit by finding the head of the parent branch
        File file = Utils.join(Repository.BRANCH_DIR, branch);
        this.parent = Utils.readContentsAsString(file);
        // below we want to create the TreeMap which links the file names to the blobs in the data folder
        this.filesToData = mapFilesToData(this.branch);
    }

    public String getMessage() {
        return message;
    }

    public Instant getDate() {
        return date;
    }

    public String getParent() {
        return parent;
    }

    public String getBranch() {
        return branch;
    }

    /**
     * Create the first commit
     * Persist the hash of the head to the branches directory
     */
    public static void createFirstCommit() {
        Commit firstCommit = new Commit("initial commit", Instant.EPOCH, "main",  "");
        byte[] commitByteArray = Utils.serialize(firstCommit);
        String commitHash = Utils.sha1(commitByteArray);
        File commitFile = Utils.join(Repository.COMMIT_DIR, commitHash);
        File head = Utils.join(Repository.BRANCH_DIR, firstCommit.branch);
        Utils.writeContents(commitFile, commitByteArray);
        Utils.writeContents(head, commitHash);
        try {
            commitFile.createNewFile();
            head.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
