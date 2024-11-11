package saolei;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Setting extends JDialog implements ActionListener, KeyListener,
        Runnable {

    JButton bon1, bon2;
    ButtonGroup group;
    Font font;
    JLabel[] label;
    JRadioButton[] radio;
    JPanel panel;
    int level;
    String[] str, str1;
    TextField[] file;
    Panel mine;

    public Setting(Panel mine, int level, String s) {
        setTitle(s);
        setModal(true);
        this.level = level;
        this.mine = mine;
    }

    // 添加组件
    public void addPart() {
        panel = new JPanel();
        panel.setLayout(null);
        font = new Font("宋体", 1, 14);

        radio = new JRadioButton[4];
        str1 = new String[] { "10个雷", "40个雷", "99个雷", "自定义" };
        group = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            radio[i] = new JRadioButton(str1[i]);
            radio[i].setFont(font);
            radio[i].addActionListener(this);
            group.add(radio[i]);
            panel.add(radio[i]);
            radio[i].addKeyListener(this);
        }

        radio[0].setBounds(30, 30, 100, 20);
        radio[1].setBounds(30, 110, 100, 20);
        radio[2].setBounds(30, 190, 100, 20);
        radio[3].setBounds(260, 10, 100, 20);
        radio[level].setSelected(true);

        label = new JLabel[9];
        str = new String[] { "初级", "9*9平铺网络", "中级", "16*16平铺网络", "高级",
                "16*30平铺网络", "高度(9-24):", "宽度(9-40):", "雷数(0-900):" };

        for (int i = 0; i < 9; i++) {
            label[i] = new JLabel(str[i]);
            label[i].setFont(font);
            label[i].setBackground(Color.white);
            panel.add(label[i]);
        }
        label[0].setBounds(50, 10, 100, 20);
        label[1].setBounds(50, 50, 100, 20);
        label[2].setBounds(50, 90, 100, 20);
        label[3].setBounds(50, 130, 100, 20);
        label[4].setBounds(50, 170, 100, 20);
        label[5].setBounds(50, 210, 100, 20);
        label[6].setBounds(280, 50, 100, 20);
        label[7].setBounds(280, 85, 100, 20);
        label[8].setBounds(280, 120, 100, 20);

        file = new TextField[3];
        for (int i = 0; i < 3; i++) {
            file[i] = new TextField();
            file[i].setFont(font);
            file[i].addActionListener(this);
            file[i].addKeyListener(this);

            panel.add(file[i]);
        }
        file[0].setBounds(390, 50, 60, 25);
        file[1].setBounds(390, 85, 60, 25);
        file[2].setBounds(390, 120, 60, 25);
        if (level == 3) {
            setFile(true);
            setNumber(mine.getRow() - 2, mine.getColumn() - 2, mine.getMine());
        } else {
            setFile(false);
            setNumber(24, 40, 900);
        }

        bon1 = new JButton("确认");
        bon1.setFont(font);
        bon1.addActionListener(this);
        bon1.setBounds(200, 300, 100, 30);
        panel.add(bon1);
        bon2 = new JButton("取消");
        bon2.setFont(font);
        bon2.addActionListener(this);
        bon2.setBounds(350, 300, 100, 30);
        panel.add(bon2);

        add(panel, BorderLayout.CENTER);
    }

    // 设定自定义框是否可用
    public void setFile(boolean flag) {
        file[0].setEditable(flag);
        file[1].setEditable(flag);
        file[2].setEditable(flag);
        file[0].setEnabled(flag);
        file[1].setEnabled(flag);
        file[2].setEnabled(flag);
    }

    // 设置自定义框显示数字
    public void setNumber(int a, int b, int c) {
        file[0].setText(a + "");
        file[0].setFont(font);
        file[1].setText(b + "");
        file[1].setFont(font);
        file[2].setText(c + "");
        file[2].setFont(font);
    }

    public void actionPerformed(ActionEvent e) {

        int a = 0;//
        // 取消按钮
        if (e.getSource() == bon2) {
            a = 1;
            dispose();
        }
        // 初级
        if (radio[0].isSelected()) {
            setFile(false);
            setNumber(24, 40, 900);
            if (e.getSource() == bon1) {
                mine.firstLevel();
                dispose();
            }
        }

        // 中级
        if (radio[1].isSelected()) {
            setFile(false);
            setNumber(24, 40, 900);
            if (e.getSource() == bon1) {
                mine.secondLevel();
                dispose();
            }
        }

        // 高级
        if (radio[2].isSelected()) {
            setFile(false);
            setNumber(24, 40, 900);
            if (e.getSource() == bon1) {
                mine.thirdLevel();
                dispose();
            }
        }

        // 自定义
        if (radio[3].isSelected()) {
            setFile(true);
            try {
                int s1 = Integer.parseInt(file[0].getText()) + 2;
                int s2 = Integer.parseInt(file[1].getText()) + 2;
                int s3 = Integer.parseInt(file[2].getText());
                if (e.getSource() == bon1) {
                    if (s1 > 10 && s1 < 27 && s2 > 10 && s2 < 43 && s3 > 9
                            && s3 < 901 && s3 < (s1 - 2) * (s2 - 2)) {
                        mine.setLevel(s1, s2, s3);
                        dispose();
                    } else if (s3 >= (s1 - 2) * (s2 - 2))
                        JOptionPane.showMessageDialog(this, "输入雷数错误！！！", "错误",
                                JOptionPane.ERROR_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(this, "输入的数字超出范围！", "错误",
                                JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e1) {
                if (a == 0)
                    JOptionPane.showMessageDialog(this, "请输入数字！", "错误",
                            JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void run() {
        addPart();
        setBounds(450, 150, 500, 400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            bon1.doClick();
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            bon2.doClick();
        }
    }

    public void keyReleased(KeyEvent arg0) {

    }

    public void keyTyped(KeyEvent e) {

    }
}