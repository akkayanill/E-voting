package Models.Voting;

//This is class for poll question in voting. Each voting has it's custom number of polls.
//Polls can be only question polls or image polls.
//The statistics will be in percents and will be shown to voter after voting.


public class Poll {
    private String type;
    private String question;
    private String[] choices = new String[2];
    private double[] stats = new double[2];

    public void setType(String type){
        this.type = type;
    }
    public void setQuestion(String question){
        this.question = question;
    }
    public void setChoices(String choice1,String choice2){
        choices[0] = choice1;
        choices[1] = choice2;
    }
    public void setStats(double stat1,double stat2){
        stats[0] = stat1;
        stats[1] = stat2;
    }


    public String getType() {
        return type;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getChoices() {
        return choices;
    }

    public double[] getStats() {
        return stats;
    }
}




