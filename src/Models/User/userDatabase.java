package Models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class userDatabase {
    private String path;
    private List<String> database = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public userDatabase(String path) {
        String localDir = System.getProperty("user.dir");
        this.path = localDir + path;
    }

    private String getPath() {
        return path;
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
        User tempUser = new User(email,password);
        users.add(tempUser);
    }

    public void loadDatabase() {
        try {
            File f = new File(this.getPath());
            Scanner sc = new Scanner(new FileReader(f));
            while (sc.hasNext()) {
                users.add(new User(sc.next(), sc.next()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading DATABASE: File not found.");
        }
    }

    public void saveToFile() {
        try{
            File f = new File(this.getPath());
            BufferedWriter out = new BufferedWriter(new FileWriter(f));

            for (int i=0;i<users.size();i++) {
                out.write(users.get(i).getEmail() + " " + users.get(i).getPassword());
                out.newLine();
            }

            out.close();
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}

