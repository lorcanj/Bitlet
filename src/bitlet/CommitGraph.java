package bitlet;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The commit graph is a DAG where the root is the first commit and
 * each leaf should be the most up to date commit on a given branch
 * The branches will store an array, maybe hashmap? of branches
 */
public class CommitGraph {
    // should the head be a reference or just the hash?

    private Commit head;
    private ArrayList<Commit> branches;
    private HashMap<String, Commit> runTimeCommitMap;

    public CommitGraph(Commit head, ArrayList<Commit> branches) {
        this.head = head;
        this.branches = branches;
        this.runTimeCommitMap = Utils.createRunTimeCommitMap();
    }

}
