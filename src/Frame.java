import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame{

    public void setOptimalLocation(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dim.width - this.getSize().width) / 2, (dim.height - this.getSize().height) / 2);
    }
}
