package Models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;

public class UserDatabase {
    private String path;
    private List<String> database = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public UserDatabase(String path) {
        String localDir = System.getProperty("user.dir");
        this.path = localDir + path;
    }

    private String getPath() {
        return path;
    }

    public User getUserByUserName(String email){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) {
                return users.get(i);
            }
        }
            System.out.println("NOBODY FOUND with name "+email);
            return null;
    }


    public int isInDatabase(String email, String password) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) {
                if (users.get(i).getPassword().equals(password)) {
                    return 0;
                }
                else return 1;
            }
        }
        return 2;
    }

    public boolean isInDatabase(String email){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) return true;
        }
        return false;
    }

    public void addUser(String email,String password){
        User tempUser = new User(email,password,0,0);
        users.add(tempUser);
    }

    /**
     * This method loads csv file into database of usernames and passwords
     */
    public void loadDatabase() {
         try {
            File f = new File(this.getPath());
            BufferedReader rd = new BufferedReader( new FileReader(f));
            String line = "";

            while ((line = rd.readLine())!=null ){
                 String[] userData = line.split(";");
                 for (int i = 0; i < userData.length; i++) {
                     userData[i] = userData[i].substring(userData[i].indexOf("\"") + 1, userData[i].lastIndexOf("\""));
                 }
                 users.add(new User(userData[0], userData[1],Integer.parseInt(userData[2]),Integer.parseInt(userData[3])));
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("Error loading DATABASE: File not found.");
        }
        catch (IOException e){
             System.err.println(e);
        }

    }

    public void saveToFile() {
        try{
            File f = new File(this.getPath());
            BufferedWriter out = new BufferedWriter(new FileWriter(f));

            for (int i=0;i<users.size();i++) {
                out.write("\""+users.get(i).getEmail()+"\";\""+users.get(i).getPassword()+"\";\""+users.get(i).getCompletedVotings()+"\";\""+users.get(i).getThisMonthCreated()+"\"");
                out.newLine();
            }

            out.close();
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}

