package ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {

    //创建选项下面的条目对象
    JMenuItem replayItem = new JMenuItem("重新开始");
    JMenuItem reloginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");
    JMenuItem aboutItem = new JMenuItem("公众号");
    int[][] data = new int[4][4];
    int step = 0;
    int x = 0;
    int y = 0;
    int win[][] = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
    String path = "D:\\ideapro\\puzzlegame\\image\\animal\\animal3\\";
    public GameJFrame() {
        //初始化界面
        initJFrame();
        //初始化菜单
        initJMenuBar();
        //初始化数据(打乱索引)
        initData();
        //初始化图片
        initImage();

        //设置界面的可见性
        this.setVisible(true);

    }

    //初始化数据(打乱索引)
    private void initData() {
        int arr[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            int index = random.nextInt(arr.length);
            int temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                x = i / 4;
                y = i % 4;
            }
            data[i / 4][i % 4] = arr[i];

        }
    }
    //初始化图片
    private void initImage() {
        //清空容器
        this.getContentPane().removeAll();
        if(victory()){
            //添加胜利图片
            JLabel winJLabel = new JLabel(new ImageIcon("D:\\ideapro\\puzzlegame\\image\\win.png"));
            winJLabel.setBounds(203, 283, 197, 73);
            this.getContentPane().add(winJLabel);
        }
        //添加步数计数器
        JLabel stepCount = new JLabel("步数："+step);
        stepCount.setBounds(50, 30, 100, 20);
        this.getContentPane().add(stepCount);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //获取当前要加载的图片的序号
                int index = data[i][j];
                //创建一个图片ImageIcon的对象
                //创建一个JLabel的对象
                JLabel jLabel = new JLabel(new ImageIcon(path + index + ".jpg"));
                //指定图片的位置
                jLabel.setBounds(105 * j+83, 105 * i+134, 105, 105);
//                //设置图片的透明度
//                jLabel.setOpaque(false);
                //设置图片边框
                jLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
                //把管理容器添加到界面中
                this.getContentPane().add(jLabel);
            }
        }
        //添加背景图片
        JLabel background = new JLabel(new ImageIcon("D:\\ideapro\\puzzlegame\\image\\background.png"));
        background.setBounds(40, 40, 508, 560);
        this.getContentPane().add(background);
        //刷新界面
        this.getContentPane().repaint();

    }

    //初始化菜单
    private void initJMenuBar() {

        //创建整个的菜单对象
        JMenuBar jMenuBar = new JMenuBar();
        //创建菜单上的两个对象
        JMenu functionJmenu = new JMenu("功能");
        JMenu aboutJmenu = new JMenu("关于我们");

        //将每一个选项下面的条目添加到菜单里
        functionJmenu.add(replayItem);
        functionJmenu.add(reloginItem);
        functionJmenu.add(closeItem);
        aboutJmenu.add(aboutItem);
        //将菜单添加到菜单栏里
        jMenuBar.add(functionJmenu);
        jMenuBar.add(aboutJmenu);
        //给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
        //添加选项点击事件
        replayItem.addActionListener(this);
        closeItem.addActionListener(this);
        aboutItem.addActionListener(this);
        reloginItem.addActionListener(this);

    }
    //初始化界面
    private void initJFrame() {
        //设置界面的宽高
        this.setSize(603, 680);
        //设置界面的标题
        this.setTitle("拼图游戏 v2.0");
        //设置界面置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置界面的关闭操作
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //设置界面的布局
        this.setLayout(null);
        //设置界面的背景颜色
//        this.setBackground(Color.blue);
        //给界面添加键盘监听
        this.addKeyListener(this);

    }
    //判断是否胜利
    public boolean victory(){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if(data[i][j]!=win[i][j]){
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    //键盘按下事件
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == 65){
            this.getContentPane().removeAll();
            JLabel all = new JLabel(new ImageIcon(path+"all.jpg"));
            all.setBounds(83, 134, 420, 420);
            this.getContentPane().add(all);
            //添加背景图片
            JLabel background = new JLabel(new ImageIcon("D:\\ideapro\\puzzlegame\\image\\background.png"));
            background.setBounds(40, 40, 508, 560);
            this.getContentPane().add(background);
            //刷新界面
            this.getContentPane().repaint();

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //判断是否胜利
        if(victory()){
            return;
        }
        //对上下左右进行判断
        //左：37上：38右：39下：40
        int code = e.getKeyCode();
        if (code == 37) {
            //左
            System.out.println("向左移动");
            if (y == 0) {
                return;
            }

            data[x][y]= data[x][y-1];
            data[x][y-1]=0;
            y--;
            //更新步数计数器
            step++;
            initImage();
        } else if (code == 38) {
            //上
            System.out.println("向上移动");
            if (x == 0) {
                return;
            }
            data[x][y]= data[x-1][y];
            data[x-1][y]=0;
            x--;
            //更新步数计数器
            step++;
            //刷新界面
            initImage();
        } else if (code == 39) {
            //右
            System.out.println("向右移动");
            if (y == 3) {
                return;
            }
            data[x][y]= data[x][y+1];
            data[x][y+1]=0;
            y++;
            //更新步数计数器
            step++;
            //刷新界面
            initImage();
        } else if (code == 40) {
            //下
            System.out.println("向下移动");
            if (x == 3) {
                return;
            }
            data[x][y]= data[x+1][y];
            data[x+1][y]=0;
            x++;
            //更新步数计数器
            step++;
            initImage();
        }else if(code == 65){
            initImage();
        } else if (code == 87) {
            data = new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
            initImage();
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //获取点击的选项名称
        Object obj = e.getSource();
        if(obj == reloginItem){
            //关闭当前界面
            this.setVisible(false);
            //打开登录界面
            new LoginJFrame();
        }else if(obj == closeItem){
//            this.dispose();
            //关闭游戏
            System.exit(0);
        }else if(obj == aboutItem){
            //打开关于界面
            JDialog jDialog = new JDialog();
            JLabel jLabel = new JLabel(new ImageIcon("D:\\ideapro\\puzzlegame\\image\\about.png"));
            jLabel.setBounds(0, 0, 258, 258);
            jDialog.getContentPane().add(jLabel);
            jDialog.setSize(344,344);
            jDialog.setAlwaysOnTop(true);
            //设置界面居中
            jDialog.setLocationRelativeTo(null);
            //设置界面的关闭操作
            jDialog.setModal(true);
            jDialog.setVisible(true);
        }else if(obj == replayItem){
            //重新开始游戏
            step = 0;
            initData();
            initImage();

        }

    }
}
