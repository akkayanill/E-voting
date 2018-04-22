package Models.User;

public class User {
    private String email;
    private String password;
    private int completedVotings;
    private int thisMonthVotings;

    User(String email, String password,int total) {
        this.email = email;
        this.password = password;
        setCompletedVotings(total);
        setThisMonthVotings(0);
    }

    public User(String email){
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() { return password; }

    public void setCompletedVotings(int completedVotings) {
        this.completedVotings = completedVotings;
    }

    public void setThisMonthVotings(int thisMonthVotings) {
        this.thisMonthVotings = thisMonthVotings;
    }

    public int getCompletedVotings() {
        return completedVotings;
    }

    public int getThisMonthVotings() {
        return thisMonthVotings;
    }

    public void addCompletedVoting(){
        this.thisMonthVotings++;
        this.completedVotings++;
    }

}
