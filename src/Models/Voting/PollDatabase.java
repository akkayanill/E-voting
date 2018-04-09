package Models.Voting;

import java.util.ArrayList;
import java.util.List;

public class PollDatabase {
    private String path;
    private int votingCounter;
    private List<Voting> votings = new ArrayList<>();

    public PollDatabase(String path){
        this.path = path;
    }

    public void loadDatabase(){
        //TODO LOAD FROM FILE, UNPARSE
    }

    public void saveToFile(){
        //TODO PARSE AND WRITE TO FILE
    }

    public void parse(){

    }

    public void unparse(){

    }

    public void addVoting(Voting newVoting){
        votings.add(newVoting);
        votingCounter++;
    }

    public Voting getVoting(int index){
        return votings.get(index);
    }

    public int size(){
        return votingCounter;
    }


}
