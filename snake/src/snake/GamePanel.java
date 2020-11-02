package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
    //画板，画界面，画蛇
    int length;
    int[] snakeX = new int[600]; //X坐标
    int[] snakeY = new int[500]; //Y坐标
    //构造器
    String fx;
    boolean isStart = false;
    Timer timer = new Timer(100,this); //定时器
    int score;

    //定义一个食物
    int foodX;
    int foodY;
    Random random = new Random();


    //死亡判断
    boolean isFail = false;

    GamePanel(){
        init();

        //获取键盘的监听事件
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start(); //让时间动起来

    }
    //初始化
    public void init(){
        length = 3;
        fx = "R";
        //头部坐标
        snakeX[0] = 100;  snakeY[0] = 100;
        snakeX[1] = 75;   snakeY[1] = 100;
        snakeX[2] = 50;   snakeY[2] = 100;

        foodX = 25 + 25*random.nextInt(34);
        foodY = 75 + 25*random.nextInt(24);
        score = 0;

    }

    @Override
    protected void paintComponent(Graphics g) {
        //清屏
        super.paintComponent(g);
        //设置背景颜色
        this.setBackground(Color.WHITE );
        //绘制头部的广告栏
        Data.header.paintIcon( this,g,25,11);
        //绘制游戏区域
        g.fillRect(25,75,850,600);


        //画一条静态的小蛇
        if(fx.equals("R")){
            Data.right.paintIcon(this,g,snakeX[0],snakeY[0]);
        }
        else if(fx.equals("L")){
            Data.left.paintIcon(this,g,snakeX[0],snakeY[0]);
        }
        else if(fx.equals("U")){
            Data.up.paintIcon(this,g,snakeX[0],snakeY[0]);
        }
        else if(fx.equals("D")){
            Data.down.paintIcon(this,g,snakeX[0],snakeY[0]);
        }

        for(int i=1;i<length;i++){
            Data.body.paintIcon(this,g,snakeX[i],snakeY[i]); //蛇的身体长度
        }

        // 画食物
        Data.food.paintIcon(this,g,foodX,foodY);

        //画积分
        g.setColor(Color.WHITE); //设置画笔颜色
        g.setFont(new Font("Calibri",Font.BOLD,18)); //设置字体
        g.drawString("Length: "+ length,750,35);
        g.drawString("Score: "+ score,750,50);


        if(isStart==false){
            g.setColor(Color.WHITE); //设置画笔颜色
            g.setFont(new Font("Calibri",Font.BOLD,40)); //设置字体
            g.drawString("Press Space To Begin the Game",150,300);
        }

        //失败提醒　
        if(isFail==true){
            g.setColor(Color.RED); //设置画笔颜色
            g.setFont(new Font("Calibri",Font.BOLD,40)); //设置字体
            g.drawString("Game Over! Press Space To Restart Game",25,300);
        }


    }

    //接收键盘的输入：监听
    @Override
    public void keyPressed(KeyEvent e) {
        //键盘按下，未释放
        int keyCode  = e.getKeyCode();
        if(keyCode==KeyEvent.VK_SPACE){
            if(isFail){ //失败，游戏再来
                isFail = false;
                init(); //重新初始化游戏
            }
            else {
                //暂停游戏
                isStart = !isStart;
            }
            //刷新界面
            repaint();
        }

        //键盘控制走向
        if(keyCode==KeyEvent.VK_LEFT){
            fx = "L";
        }
        else if(keyCode==KeyEvent.VK_RIGHT){
            fx = "R";
        }
        else if(keyCode==KeyEvent.VK_UP){
            fx = "U";
        }
        else if(keyCode==KeyEvent.VK_DOWN){
            fx = "D";
        }


    }

    //时间帧　
    @Override
    public void actionPerformed(ActionEvent e) {
        //如果游戏处于开始状态
        //右移
        if(isStart && isFail==false){

            for(int i=length-1;i>0;i--){
                snakeX[i] = snakeX[i-1];
                snakeY[i] = snakeY[i-1];
            }

            //通过控制方向让头部移动
            if(fx.equals("R")){
                snakeX[0] = snakeX[0] + 25; //头部移动
                if(snakeX[0]>850){  snakeX[0] = 25; } //边界判断
            }
            else if(fx.equals("L")){
                snakeX[0] = snakeX[0] - 25;
                if(snakeX[0]<25){ snakeX[0] = 850;}
            }
            else if(fx.equals("U")){
                snakeY[0] = snakeY[0] - 25;
                if(snakeY[0]<75){snakeY[0] = 650;}
            }
            else if(fx.equals("D")){
                snakeY[0] = snakeY[0] + 25;
                if(snakeY[0]>650){snakeY[0] = 75;}
            }

            //如果小蛇头坐标和食物坐标相等
            if(snakeX[0]==foodX&&snakeY[0]==foodY){
                //长度+1
                length++;
                score+=10;
                //重新生成食物
                foodX = 25 + 25*random.nextInt(34);
                foodY = 75 + 25*random.nextInt(24);
            }

            for(int i=1;i<length;i++){
                if(snakeX[0]==snakeX[i] && snakeY[0] == snakeY[i]){
                    isFail = true;
                }
            }

            //刷新界面
            repaint();
        }


        timer.start();
    }



    @Override
    public void keyTyped(KeyEvent e) {

    }



    @Override
    public void keyReleased(KeyEvent e) {

    }


}
