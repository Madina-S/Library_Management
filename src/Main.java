import javax.swing.*;

public class Main {

    public static void main(String args[]){
         try
         {
             UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
         }
         catch(Exception e){
             System.out.println("Could not set look and feel");
         }
         LoginWindow loginWindow = new LoginWindow();
         loginWindow.setVisible(true);
     }
}
