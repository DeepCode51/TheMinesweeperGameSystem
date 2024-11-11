package saolei;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FailPane extends JDialog implements ActionListener {
	Panel frame;
    Font font;
    JButton bon1, bon2, bon3;
    JLabel label;
    JPanel panel;

    public FailPane(String s, Panel frame) {
        this.frame = frame;
        setTitle(s);
        addPart();
        setBounds(450, 200, 500, 200);
        close();
        setModal(true);
        setVisible(true);
    }

    // 设置默认关闭方式
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

        label = new JLabel("不好意思，你输了！ 再接再励！");
        label.setFont(font);
        label.setBounds(100, 10, 300, 50);
        panel.add(label);

        bon1 = new JButton("开始新游戏");
        bon1.setFont(font);
        bon1.addActionListener(this);
        bon1.setMargin(new Insets(0, 0, 0, 0));
        panel.add(bon1);
        bon1.setBounds(30, 100, 120, 40);

        bon2 = new JButton("重新开始");
        bon2.setFont(font);
        bon2.addActionListener(this);
        bon2.setMargin(new Insets(0, 0, 0, 0));
        panel.add(bon2);
        bon2.setBounds(190, 100, 120, 40);

        bon3 = new JButton("退出");
        bon3.setFont(font);
        bon3.addActionListener(this);
        bon3.setMargin(new Insets(0, 0, 0, 0));
        panel.add(bon3);
        bon3.setBounds(350, 100, 120, 40);

        add(panel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        // 新游戏
        if (e.getSource() == bon1) {
            frame.newGame();
            dispose();
        }

        // 重新开始
        if (e.getSource() == bon2) {
            frame.restart();
            dispose();
        }
        //退出
        if (e.getSource() == bon3) {
            System.exit(0);
        }
    }
}