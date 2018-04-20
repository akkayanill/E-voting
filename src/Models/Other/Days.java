package Models.Other;

public class Days {
    private int today;
    private String[] days = { "Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday" };

    public void setToday(int today) { this.today = today; }

    public int getToday() { return today; }

    public String getTodayFullName() {
        return days[getToday()-1];
    }

    public String getDayAbbr(int index) {
        return days[index-1].substring(0,3);
    }
}
