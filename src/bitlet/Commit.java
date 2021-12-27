package bitlet;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.util.TreeMap;

// commits will need a hashtable to map the file name to the blob
// will need to think whether need blobs to be an object or just a hash of the file it points to itself

// each commit can have multiple files which are being committed

// first iteration will only allow 1 file to be added (i.e. staged)
// can add multiple times to add these files to the staging area
// when commit, will create a new commit object with


/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Lorcan
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
    private String author;
    private String branch;
    // the parent will be the SHA1 hash of the parent commit
    private String parent;
    private String UID;

    // want a treemap which given an input SHA1 hash of a filename, returns the SHA1 hash of the blob to return
    // with the returned has it should be possible to deserialise the file to a File object using the contents

    // each commit will contain a Treemap of file names and is mapped to the hash of the data
    // at commit will
    private TreeMap<String, String> filesToBlobs;


    public Commit(String message, Instant date, String author, String branch) {
        this.message = message;
        this.date = date;
        this.author = author;
        this.branch = branch;
    }

    //public void

    // when commit, will take the files from the stage and hash the contents and use these to fill the TreeMap
    // with references from the file name to the hash contents
    // data will store a list of hashes which can then be retrieved

    /**
     * Create the first empty commit
     * @return
     */
    public static void createFirstCommit() {
        Commit firstCommit = new Commit("initial commit", Instant.EPOCH, "",  "main");
        byte[] commitByteArray = Utils.serialize(firstCommit);
        String commitHash = Utils.sha1(commitByteArray);
        File commitFile = Utils.join(Repository.COMMIT_DIR, commitHash);
        try {
            commitFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* TODO: fill in the rest of this class. */
}
