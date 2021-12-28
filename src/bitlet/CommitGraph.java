package bitlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;

/**
 * The commit graph is a DAG where the root is the first commit and
 * each leaf should be the most up to date commit on a given branch
 * The branches will store an array, maybe hashmap? of branches
 */
public class CommitGraph {
    // should the head be a reference or just the hash?

    private String currentBranch;
    private String head;
    private HashMap<String, Commit> runTimeCommitMap;

    // so how do we construct the
    public CommitGraph() {
        File head = Utils.join(Repository.BRANCH_DIR,"HEAD");

        this.runTimeCommitMap = Utils.createRunTimeCommitMap();
    }

    // it's a branch!!!
    private Commit getHeadCommit() {
        for (Commit c : runTimeCommitMap.values()) {
            if (c.getParent() == "") {
                return c;
            }
        }
        System.exit(0);
        return null;
    }


    // the head is the most recent
}
