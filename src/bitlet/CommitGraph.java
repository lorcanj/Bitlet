package bitlet;



/**
 * The commit graph is a DAG where the root is the first commit and
 * each leaf should be the most up to date commit on a given branch
 * The branches will store an array, maybe hashmap? of branches
 */
public class CommitGraph {
    private Commit head;
    // not sure whether this should be an arrayList or some sort of hashMap
    private Commit[] branches;
}
