package Models.Voting;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//This will be the Voting in general which has polls with questions.
//It will be used in an vector of Votings, where user can choose which voting he wants to choose.

public class Voting {
    private String title;
    private int pollCounter;
    private List<Poll> polls = new ArrayList<>();
    private LocalDate dateFrom;
    private LocalDate dateTo;


    public Voting(String title, int pollCounter, List<Poll> polls, LocalDate dateFrom, LocalDate dateTo){
        this.title = title;
        this.pollCounter = pollCounter;
        this.polls = polls;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
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

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
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

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }
}
