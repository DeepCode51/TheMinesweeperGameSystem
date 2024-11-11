package saolei;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;

public class Panel extends JFrame implements ActionListener, MouseListener {
	
    boolean flag, tt, isFirst;
    int row, column, mine;
    int flagCount, time;
    Font font1, font2;
    ImageIcon[] icon, iconChange;
    int num, num1, num2, mineEnd, bonEnd, level;
    int[][] isMine, isEnable, rightClick, right;
    int[] count1, count2;
    JPanel panel1, panel2, panel3, panel4, panel5;
    JButton[][] button;
    JButton[] bon;
    JCheckBoxMenuItem jitem;
    JLabel label1, label2;
    JMenuBar bar;
    JMenu menu1, menu2;
    JMenuItem[] item;
    JToolBar toolbar;
    Random ron;
    Record record;
    String[] str;
    Timer timer;

    // 构造方法
    public Panel(String s) {
        super(s);

        setLook();
        record = new Record();
        font1 = new Font("宋体", 0, 18);
        font2 = new Font("宋体", 1, 16);

        icon = new ImageIcon[16];
        iconChange = new ImageIcon[16];
        for (int i = 0; i < 16; i++) {
        	System.out.println(""+ i + ".jpg");
            icon[i] = new ImageIcon(Panel.class.getResource("/source/"+ i + ".jpg"));
        }
        getLevel();
        setResizable(false);
    }

    // 设置行数
    public void setRow(int row) {
        this.row = row;
    }

    // 设置列数
    public void setColumn(int column) {
        this.column = column;
    }

    // 设置雷数
    public void setMine(int mine) {
        this.mine = mine;
    }

    // 获得行数
    public int getRow() {
        return row;
    }

    // 获得列数
    public int getColumn() {
        return column;
    }

    // 获得雷数
    public int getMine() {
        return mine;
    }

    // 获取级别
    public void getLevel() {
        if (!record.readLevel().equalsIgnoreCase("")) {
            int i = Integer.parseInt(record.readLevel());
            level = i;
            if (i == 0) {
                setRow(11);
                setColumn(11);
                setMine(10);
                fresh();
                addPart(50, 50);
                arrangeMine();
                countTime();
            }
            if (i == 1) {
                setRow(18);
                setColumn(18);
                setMine(40);
                fresh();
                addPart(35, 35);
                arrangeMine();
                countTime();
            }
            if (i == 2) {
                setRow(18);
                setColumn(32);
                setMine(99);
                fresh();
                addPart(35, 35);
                arrangeMine();
                countTime();
            }
            if (i == 3) {
                String s = record.readLevel4();
                String[] str = s.split("<>");
                setRow(Integer.parseInt(str[0]));
                setColumn(Integer.parseInt(str[1]));
                setMine(Integer.parseInt(str[2]));
                fresh();
                addPart4(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
                arrangeMine();
                countTime();
            }
        } else {
            setRow(11);
            setColumn(11);
            setMine(10);
            fresh();
            addPart(50, 50);
            arrangeMine();
            countTime();
            level = 0;
        }
    }

    // 设置默认外观
    public void setLook() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());// 设置默认外观
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    // 初始化部分变量
    public void fresh() {
        isMine = new int[row][column];
        button = new JButton[row][column];
        isEnable = new int[row][column];
        rightClick = new int[row][column];
        right = new int[row][column];
        mineEnd = 0;
        bonEnd = 0;
        flagCount = mine;
        time = 0;
        isFirst = true;
    }

    // 改变图片显示大小
    public void changeIcon(int width, int height) {
        ImageIcon icon11;
        for (int i = 0; i < 14; i++) {
            Image temp = icon[i].getImage().getScaledInstance(width, height,
                    icon[i].getImage().SCALE_DEFAULT);
            icon11 = new ImageIcon(temp);
            iconChange[i] = icon11;
        }
    }

    // 初始化北部面板
    public void addNorthPanel(int length) {
        panel2 = new JPanel();
        JPanel panel21 = new JPanel();
        JPanel panel22 = new JPanel();
        FlowLayout f = new FlowLayout();
        FlowLayout f1 = new FlowLayout();
        panel2.setLayout(f);
        f.setHgap(length);
        f.setVgap(0);
        panel21.setLayout(f1);
        panel22.setLayout(f1);
        f1.setHgap(15);
        bon = new JButton[4];
        for (int i = 0; i < 4; i++) {
            bon[i] = new JButton("");
            bon[i].setMargin(new Insets(0, 0, 0, 0));
            if (i < 2)
                panel21.add(bon[i]);
            else
                panel22.add(bon[i]);
        }
        bon[0].setPreferredSize(new Dimension(40, 40));
        bon[0].setIcon(icon[14]);
        bon[0].setBorder(new BevelBorder(BevelBorder.RAISED));
        bon[1].setPreferredSize(new Dimension(70, 35));
        bon[1].setFont(new Font("宋体", 1, 24));
        bon[1].setText(String.valueOf(time));
        bon[1].setBorder(new BevelBorder(BevelBorder.LOWERED));
        panel2.add(panel21);

        bon[2].setPreferredSize(new Dimension(40, 40));
        bon[2].setIcon(icon[15]);
        bon[2].setBorder(new BevelBorder(BevelBorder.RAISED));
        bon[3].setPreferredSize(new Dimension(70, 35));
        bon[3].setFont(new Font("宋体", 1, 24));
        bon[3].setText(mine + "");
        bon[3].setBorder(new BevelBorder(BevelBorder.LOWERED));
        panel2.add(panel22);
    }

    // 初始化中部面板
    public void addCenterPanel(int width, int height) {
        panel1 = new JPanel();
        panel1.setBorder(new BevelBorder(BevelBorder.LOWERED));
        panel1.setLayout(new GridLayout(row - 2, column - 2));
        changeIcon(width, height);
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < column - 1; j++) {
                button[i][j] = new JButton();
                button[i][j].setPreferredSize(new Dimension(width, height));
                button[i][j].setMargin(new Insets(0, 0, 0, 0));
                button[i][j].setBorder(BorderFactory.createLineBorder(
                        Color.black, 1));
                button[i][j].setIcon(iconChange[10]);
                button[i][j].addActionListener(this);
                button[i][j].addMouseListener(this);
                panel1.add(button[i][j]);
            }
        }
        panel1.revalidate();
    }

    // 初始化菜单
    public void addMenu() {
        // 添加菜单
        menu1 = new JMenu("游戏");
        menu1.setFont(font1);
        menu2 = new JMenu("关于");
        menu2.setFont(font1); 
        
        str = new String[]{"新游戏", "设置", "退出", "版本信息"};
        item = new JMenuItem[4];
        for (int i = 0; i < 4; i++) {
            item[i] = new JMenuItem(str[i]);
            item[i].setFont(font2);
            item[i].addActionListener(this);
        }

        jitem = new JCheckBoxMenuItem("背景音乐  ");
        jitem.setFont(font2);
        jitem.addActionListener(this);

        menu1.add(item[0]);
        menu1.addSeparator();
        menu1.add(item[1]);
        menu1.addSeparator();
        menu1.add(item[2]);
        menu2.add(item[3]);

        bar = new JMenuBar();
        bar.setBorder(new BevelBorder(BevelBorder.RAISED));
        bar.add(menu1);
        bar.add(menu2);
        
        setJMenuBar(bar);// 添加菜单
    }

    // 添加组件
    public void addPart(int width, int height) {

        addMenu();
        addNorthPanel(70);// 初始化上部面板
        addCenterPanel(width, height);
        panel3 = new JPanel();
        panel3.setPreferredSize(new Dimension(45, panel1.getHeight()));
        panel4 = new JPanel();
        panel4.setPreferredSize(new Dimension(panel1.getWidth(), 40));
        panel5 = new JPanel();
        panel5.setPreferredSize(new Dimension(45, panel1.getHeight()));
        add(panel1, BorderLayout.CENTER);
        add(panel4, BorderLayout.SOUTH);
        add(panel3, BorderLayout.WEST);
        add(panel5, BorderLayout.EAST);
        add(panel2, BorderLayout.NORTH);
    }

    // 居中显示
    public void showOnCenter() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
    }

    // 判断布雷是否重复
    public boolean check(int a, int[] b, int c) {
        for (int i = 0; i <= c; i++) {
            if (a == b[i])
                return true;
        }
        return false;
    }

    // 布雷
    public void arrangeMine() {
        count1 = new int[(row - 2) * (column - 2)];
        count2 = new int[mine];
        num1 = 0;
        num2 = 0;
        int num3 = 0;// 看布雷是否正确
        ron = new Random();
        for (int i = 0; i < mine; i++) {
            num2 = ron.nextInt((row - 2) * (column - 2));
            count2[i] = num2;
            flag = check(num2, count2, i - 1);
            while (flag) {
                num2 = ron.nextInt((row - 2) * (column - 2));
                count2[i] = num2;
                flag = check(num2, count2, i - 1);
            }
            count1[num2] = 10;
        }
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < column - 1; j++) {
                isMine[i][j] = count1[num1];
                if (count1[num1] == 10) {
                    num3++;
                }
                num1++;
            }
        }
        System.out.println(num3);
    }

    // 数雷
    public int countMine(int a, int b) {
        int number1 = 0;
        for (int i = a - 1; i < a + 2; i++) {
            for (int j = b - 1; j < b + 2; j++) {
                if (isMine[i][j] == 10) {
                    number1++;
                }
            }
        }
        return number1;
    }

    // 数周围红旗数
    public int countFlag(int a, int b) {
        int number2 = 0;
        for (int i = a - 1; i < a + 2; i++) {
            for (int j = b - 1; j < b + 2; j++) {
                if (right[i][j] == 1) {
                    number2++;
                }
            }
        }
        return number2;
    }

    // 鼠标左右击
    public void openFlag(int a, int b) {
        int num3 = 0;
        for (int i = a - 1; i < a + 2; i++) {
            for (int j = b - 1; j < b + 2; j++) {
                if (isEnable[i][j] != 1 && i >= 1 && j >= 1 && i < row - 1
                        && j < column - 1) {
                    if (isMine[i][j] != 10 && right[i][j] != 1) {
                        open(i, j);
                    }
                    if (isMine[i][j] == 10 && right[i][j] != 1)
                        num3++;
                    if (bonEnd == ((row - 2) * (column - 2)) - mine) {
                        win();
                        return;
                    }
                }
            }
        }
        if (num3 != 0) {
            fail();
        }
    }

    // 鼠标左右击时旗数和显示数字不同
    public void openFlagFail(int a, int b, int c) {

        for (int i = a - 1; i < a + 2; i++) {
            for (int j = b - 1; j < b + 2; j++) {
                if (c == 1 && isEnable[i][j] != 1 && right[i][j] != 1 && i >= 1
                        && j >= 1 && i < row - 1 && j < column - 1) {
                    button[i][j].setIcon(iconChange[0]);
                    tt = true;
                }
                if (c == 0 && isEnable[i][j] != 1 && right[i][j] != 1 && i >= 1
                        && j >= 1 && i < row - 1 && j < column - 1) {
                    button[i][j].setIcon(iconChange[10]);
                    tt = false;
                }
            }
        }
    }

    // 掀开
    public void open(int a, int b) {
        num = 0;
        bonEnd++;
        isEnable[a][b] = 1;
        num = countMine(a, b);
        isMine[a][b] = num;
        button[a][b].setIcon(iconChange[num]);
        if (num == 0)
            isNull(a, b);
    }

    // 掀开的按钮周围没有雷
    public void isNull(int a, int b) {
        for (int i = a - 1; i < a + 2; i++) {
            for (int j = b - 1; j < b + 2; j++) {
                if ((i != a || j != b) && i >= 1 && j >= 1 && i < row - 1
                        && j < column - 1) {
                    if (isEnable[i][j] != 1 && isMine[i][j] != 10
                            && right[i][j] != 1) {
                        open(i, j);
                    }
                }
            }
        }
    }

    // 掀开所有的雷
    public void openAllMine() {
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < column - 1; j++) {
                if (isMine[i][j] == 10) {
                    button[i][j].setIcon(iconChange[9]);
                }
            }
        }
    }

    // 胜利
    public void win() {
        if (Integer.parseInt(bon[3].getText()) >= 0) {
            timer.stop();
            new WinPane("游戏胜利", this);
        }
    }

    // 失败
    public void fail() {
        openAllMine();
        timer.stop();
        new FailPane("游戏失败", this);
    }

    // 新游戏
    public void newGame() {
        isFirst = true;
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < column - 1; j++) {
                button[i][j].setIcon(iconChange[10]);
                isEnable[i][j] = 0;
                right[i][j] = 0;
                rightClick[i][j] = 0;
                isMine[i][j] = 0;
            }
        }
        arrangeMine();
        mineEnd = 0;
        bonEnd = 0;
        flagCount = mine;
        bon[3].setText("" + mine);
        timer.stop();
        time = 0;
        bon[1].setText("0");
    }

    // 重新开始
    public void restart() {
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < column - 1; j++) {
                button[i][j].setIcon(iconChange[10]);
                isEnable[i][j] = 0;
                right[i][j] = 0;
                rightClick[i][j] = 0;
            }
        }
        mineEnd = 0;
        bonEnd = 0;
        flagCount = mine;
        bon[3].setText("" + mine);
        timer.stop();
        time = 0;
        bon[1].setText("0");
    }

    // 第一次点时不遇雷
    public void firstClick(int a, int b) {
        int i;
        do {
            i = countMine(a, b);
            isFirst = false;
            if (i != 0) {
                arrangeMine();
                isFirst = true;
            }
        } while (isFirst);
        open(a, b);
    }

    // 初级
    public void firstLevel() {
        timer.stop();
        bon[1].setText("0");
        remove(panel1);
        setRow(11);
        setColumn(11);
        setMine(10);
        fresh();
        arrangeMine();
        mineEnd = 0;
        bonEnd = 0;
        flagCount = mine;
        bon[3].setText("" + mine);
        addCenterPanel(50, 50);
        add(panel1, BorderLayout.CENTER);
        validate();
        pack();
        showOnCenter();
        level = 0;
    }

    // 中级
    public void secondLevel() {
        timer.stop();
        bon[1].setText("0");
        remove(panel1);
        setRow(18);
        setColumn(18);
        setMine(40);
        fresh();
        arrangeMine();
        mineEnd = 0;
        bonEnd = 0;
        flagCount = mine;
        bon[3].setText("" + mine);
        addCenterPanel(35, 35);
        add(panel1, BorderLayout.CENTER);
        validate();
        pack();
        showOnCenter();
        level = 1;
    }

    // 高级
    public void thirdLevel() {
        timer.stop();
        bon[1].setText("0");
        remove(panel1);
        setRow(18);
        setColumn(32);
        setMine(99);
        fresh();
        arrangeMine();
        mineEnd = 0;
        bonEnd = 0;
        flagCount = mine;
        bon[3].setText("" + mine);
        addCenterPanel(35, 35);
        add(panel1, BorderLayout.CENTER);
        validate();
        pack();
        showOnCenter();
        level = 2;
    }

    // 自定义
    public void setLevel(int a, int b, int c) {
        timer.stop();
        bon[1].setText("0");
        remove(panel1);
        setRow(a);
        setColumn(b);
        setMine(c);
        fresh();
        arrangeMine();
        mineEnd = 0;
        bonEnd = 0;
        flagCount = mine;
        bon[3].setText("" + mine);
        setMineSize(a, b);
        add(panel1, BorderLayout.CENTER);
        validate();
        pack();
        showOnCenter();
        level = 3;
        if (a * b / c <= 5)
            isFirst = false;
    }

    // 程序开始时自定义布局
    public void addPart4(int a, int b) {
        if (a <= 12 && b <= 14)
            addPart(50, 50);
        else {
            if (a <= 14 && b <= 17)
                addPart(45, 45);
            else {
                if (a <= 16 && b <= 21)
                    addPart(40, 40);
                else {
                    if (a <= 18 && b <= 25)
                        addPart(35, 35);
                    else {
                        if (a <= 22 && b <= 34)
                            addPart(30, 30);
                        else
                            addPart(25, 25);
                    }
                }
            }
        }
    }

    // 自定义布雷设置方块大小
    public void setMineSize(int a, int b) {
        if (a <= 12 && b <= 14)
            addCenterPanel(50, 50);
        else {
            if (a <= 14 && b <= 17)
                addCenterPanel(45, 45);
            else {
                if (a <= 16 && b <= 21)
                    addCenterPanel(40, 40);
                else {
                    if (a <= 18 && b <= 25)
                        addCenterPanel(35, 35);
                    else {
                        if (a <= 22 && b <= 34)
                            addCenterPanel(30, 30);
                        else
                            addCenterPanel(25, 25);
                    }
                }
            }
        }
    }

    // 计时
    public void countTime() {
        timer = new Timer(1000, this);
    }

    // 添加监听
    public void actionPerformed(ActionEvent e) {

        // 新游戏
        if (e.getSource() == item[0]) {
            newGame();
        }
        
        // 设置
        if (e.getSource() == item[1])
            new Thread(new Setting(this, level, "设置")).start();

        // 退出
        if (e.getSource() == item[2]) {
                System.exit(0);
        }

        // 版本信息
        if (e.getSource() == item[3]) {
            JOptionPane.showMessageDialog(this, "名称：扫雷游戏\n版本：1.1\n作者：陈丽丽\n班级：170118\n学号：17013164\n", "版本信息",
                    JOptionPane.INFORMATION_MESSAGE);
        }
       
        // 计时
        if (e.getSource() == timer) {
            time++;
            bon[1].setText(time + "");
        }
    }

    public void mouseClicked(MouseEvent e) {

    }

    // 鼠标移到
    public void mouseEntered(MouseEvent e) {

        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < column - 1; j++) {
                if (isEnable[i][j] != 1 && rightClick[i][j] % 3 == 0) {
                    if (e.getSource() == button[i][j])
                        button[i][j].setIcon(iconChange[13]);
                }
            }
        }
    }

    // 鼠标离开
    public void mouseExited(MouseEvent e) {

        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < column - 1; j++) {
                if (isEnable[i][j] != 1 && rightClick[i][j] % 3 == 0) {
                    if (e.getSource() == button[i][j])
                        button[i][j].setIcon(iconChange[10]);
                }
            }
        }
    }

    // 鼠标右击左右击事件
    public void mousePressed(MouseEvent e) {
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < column - 1; j++) {
                // 鼠标左右击
                if (e.getModifiersEx() == (e.BUTTON3_DOWN_MASK + e.BUTTON1_DOWN_MASK)
                        && e.getSource() == button[i][j] && isEnable[i][j] == 1) {
                    int number3 = countFlag(i, j);
                    if (isMine[i][j] == number3) {
                        openFlag(i, j);
                    } else {
                        openFlagFail(i, j, 1);
                    }
                }
                // 鼠标右击
                if (e.getSource() == button[i][j] && isEnable[i][j] != 1
                        && e.getModifiersEx() == e.BUTTON3_DOWN_MASK) {
                    if (time == 0)
                        timer.start();
                    rightClick[i][j]++;
                    if (rightClick[i][j] % 3 == 1) {
                        button[i][j].setIcon(iconChange[11]);
                        right[i][j] = 1;
                        flagCount--;
                        bon[3].setText("" + flagCount);
                        if (isMine[i][j] == 10)
                            mineEnd++;
                        if (mineEnd == mine) {
                            win();
                        }
                    }
                    if (rightClick[i][j] % 3 == 2) {
                        button[i][j].setIcon(iconChange[12]);
                        right[i][j] = 0;
                        flagCount++;
                        bon[3].setText("" + flagCount);
                        if (isMine[i][j] == 10)
                            mineEnd--;
                        if (mineEnd == mine) {
                            win();
                        }
                    }
                    if (rightClick[i][j] % 3 == 0)
                        button[i][j].setIcon(iconChange[10]);
                }
            }
        }

        // 鼠标左击
        if (e.getModifiersEx() == e.BUTTON1_DOWN_MASK) {
            for (int i = 1; i < row - 1; i++) {
                for (int j = 1; j < column - 1; j++) {
                    if (e.getSource() == button[i][j]) {
                        if (time == 0)
                            timer.start();
                        if (isFirst == true) {
                            firstClick(i, j);
                        } else {
                            if (isEnable[i][j] != 1 && right[i][j] != 1) {
                                if (isMine[i][j] != 10)
                                    open(i, j);
                                else {
                                    fail();
                                }
                                if (bonEnd == ((row - 2) * (column - 2)) - mine)
                                    win();
                            }
                        }
                    }
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < column - 1; j++) {
                // 鼠标左右击
                if (e.getSource() == button[i][j] && isEnable[i][j] == 1
                        && tt == true) {
                    openFlagFail(i, j, 0);
                }
            }
        }
    }
}