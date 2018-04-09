package Models.Voting;

import java.util.ArrayList;
import java.util.List;

//This will be the Voting in general which has polls with questions.
//It will be used in an vector of Votings, where user can choose which voting he wants to choose.

public class Voting {
    private String title;
    private int pollCounter;
    private List<Poll> polls = new ArrayList<>();
    private List<Integer> days = new ArrayList<>();


    public Voting(String title,int pollCounter,List<Poll> polls,List<Integer> days){
        this.title = title;
        this.pollCounter = pollCounter;
        this.polls = polls;
        this.days = days;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPollCounter(int pollCounter) {
        this.pollCounter = pollCounter;
    }

    public void setPolls(List<Poll> polls) {
        this.polls = polls;
    }

    public String getTitle() {
        return title;
    }

    public List<Poll> getPolls() {
        return polls;
    }

    public int getPollCounter() {
        return pollCounter;
    }

    public void setDayAvailable(int dayIndex) {
        days.add(dayIndex);
    }

    public List<Integer> getDaysAvailable(){
        return this.days;
    }

    public boolean isDayAvailable(int dayIndex){
        if (days.contains(dayIndex)) return true;
        else return false;
    }
}
