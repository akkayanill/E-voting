package Models.Voting;

import java.util.ArrayList;
import java.util.List;

//This will be the Voting in general which has polls with questions.
//It will be used in an vector of Votings, where user can choose which voting he wants to choose.

public class Voting {
    private String title;
    private List<Poll> voting = new ArrayList<Poll>();
    private int polls;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPolls(int polls) {
        this.polls = polls;
    }

    public void setVoting(List<Poll> voting) {
        this.voting = voting;
    }

    public String getTitle() {
        return title;
    }

    public List<Poll> getVoting() {
        return voting;
    }

    public int getPolls() {
        return polls;
    }
}
