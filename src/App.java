import javax.swing.*;

import global.View;
import view.main.MainWindow;
import view.user.UserLoginPanel;

import java.awt.*;

public class App {
    
    
    public static void main(String[] args) throws Exception {
        
        // 先创建JFrame，再通过getContentPane添加其他组件
        // 整体框架是800×600，按像素计
        View.app = new MainWindow();
        View.contentPane = View.app.getContentPane();
        /* JScrollPane scrollPane = new JScrollPane(new Panel());
        scrollPane.setVerticalScrollBarPolicy(20);
        scrollPane.setHorizontalScrollBarPolicy(30); */
        // 添加登录组件
        View.loginPanel = new UserLoginPanel();
        View.contentPane.setLayout(null);
        View.loginPanel.setLocation(350, 150);
        View.loginPanel.setSize(400,160);
        View.contentPane.add(View.loginPanel);
        View.app.pack();
        View.app.setVisible(true);;
    }
}
