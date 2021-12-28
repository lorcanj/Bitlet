package bitlet;

import java.util.ArrayList;
/**
 * The commit graph is a DAG where the root is the first commit and
 * each leaf should be the most up to date commit on a given branch
 * The branches will store an array, maybe hashmap? of branches
 */
public class CommitGraph {
    private Commit head;
    // not sure whether this should be an arrayList or some sort of hashMap
    // will need to dynamically increase the information held in branches list and so cannot use
    // a normal array as will change as branches are added or removed
    private ArrayList<Commit> branches;


    // should create the Commits here as will need to move the head to the new commit and assign the
    // parent commit for the current head

    // when creating a commit, will have to reconstruct the graph from commits in the commits folder
    // at that point should
}
