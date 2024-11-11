package saolei;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WinPane extends JDialog implements ActionListener {

    Panel frame;
    Font font;
    JButton bon1, bon2;
    JLabel label;
    JPanel panel;
    public WinPane(String s, Panel frame) {
        this.frame = frame;
        setTitle(s);
        addPart();
        setBounds(500, 200, 400,200);
        close();
        setModal(true);
        setVisible(true);
    }

    //设置默认关闭方式
    public void close() {
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.newGame();
                dispose();
            }
        });
    }
    public void addPart() {
        panel = new JPanel();
        panel.setLayout(null);

        font = new Font("宋体", 1, 18);

        label = new JLabel("恭喜！你赢了！");
        label.setFont(font);
        label.setBounds(130, 10, 200, 50);
        panel.add(label);

        bon1 = new JButton("开始新游戏");
        bon1.setFont(font);
        bon1.addActionListener(this);
        bon1.setMargin(new Insets(0, 0,0 ,0));
        panel.add(bon1);
        bon1.setBounds(30, 100, 150, 40);

        bon2 = new JButton("退出");
        bon2.setFont(font);
        bon2.addActionListener(this);
        bon2.setMargin(new Insets(0, 0,0 ,0));
        panel.add(bon2);
        bon2.setBounds(200, 100, 150, 40);

        add(panel, BorderLayout.CENTER);
    }
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == bon1) {
            frame.newGame();
            dispose();
        }
        if (e.getSource() == bon2) {
            System.exit(0);
        }
    }
}
