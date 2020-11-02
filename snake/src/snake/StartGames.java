package snake;

import javax.swing.*;

public class StartGames {
    public static void main(String[] args) {
        //绘制一个静态窗口jFrame
        JFrame jFrame = new JFrame("贪吃蛇小游戏");

        //设置界面大小
        jFrame.setBounds(10,10,900,720);
        //设置窗口大小不可改变
        jFrame.setResizable(false);
        //设置关闭事件，游戏可以关闭header.png
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //2 面板
        jFrame.add(new GamePanel());
        //让窗口能够展现出来
        jFrame.setVisible(true);



    }
}
