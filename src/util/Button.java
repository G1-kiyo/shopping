package util;
import javax.swing.*;
import java.awt.*;
public class Button extends JButton {
    
    public Button(String text,Color bgColor,Color fontColor) {
        this.setText(text);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setBorderPainted(false);
        this.setBackground(bgColor);
        this.setForeground(fontColor);
        this.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
    }
}
