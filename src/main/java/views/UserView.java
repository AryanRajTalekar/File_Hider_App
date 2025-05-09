package views;

import dao.DataDAO;
import model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private String email;
    UserView(String email){
        this.email = email;
    }
    public void home(){
        do{
            System.out.println("Welcome"+this.email);
            System.out.println("press 1 to show hidden files");
            System.out.println("Press 2 to hide a new file");
            System.out.println("press 3 to reveal a file");
            System.out.println("Press 0 to exit");
            Scanner sc = new Scanner(System.in);
            int ch = Integer.parseInt(sc.nextLine());
            switch (ch){
                case 1 -> {
                    try {
                        List<Data> files = DataDAO.getAllFiles(this.email);
                        System.out.println("Id - File Name");
                        for(Data file:files){
                            System.out.println(file.getId()+"_"+file.getFileName());
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 2 ->{
                    System.out.println("Enter the file path");
                    String path = sc.nextLine();
                    File f = new File(path);
                    Data file = new Data(0,f.getName(),path,this.email);
                    try {
                        DataDAO.hideFile(file);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 3 ->{
                    List<Data> files = null;
                    try {
                        files = DataDAO.getAllFiles(this.email);

                    System.out.println("Id - File Name");
                    for(Data file:files){
                        System.out.println(file.getId()+"_"+file.getFileName());
                    }
                    System.out.println("enter the id of file you want to reveal");
                    int id = Integer.parseInt(sc.nextLine());
                    boolean isValidID = false;
                    for(Data file:files){
                        if(file.getId()==id){
                            isValidID = true;
                            break;
                        }
                    }
                    if(isValidID){
                        DataDAO.reveal(id);
                    }else{
                        System.out.println("Wrong ID");
                    }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }catch (IOException e){
                        throw new RuntimeException(e);
                    }
                }
                case 0 ->{
                    System.exit(0);
                }
            }
        }
        while(true);
    }
}
