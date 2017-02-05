package demo.snake;

import net.role4j.Compartment;
import net.role4j.Registry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by nguonly on 10/30/15.
 */
public class SnakeGame extends JFrame implements ActionListener{
    final static Integer ROW_COUNT = 30;
    final static Integer COL_COUNT = 30;
    final static Integer DOT_SIZE = 15;
    final Integer DELAY = 200;
    final Integer DELAY_STEP = 10;

    static final Font MEDIUM_FONT = new Font("Tahoma", Font.BOLD, 16);

    /**
     * The small font to draw with.
     */
    static final Font SMALL_FONT = new Font("Tahoma", Font.BOLD, 12);

    int speed = 0;

    BoardPanel boardPanel;
    StatusPanel statusPanel;
    StatisticsPanel statisticsPanel;

    Timer timer;

    Board board;
    Snake snake;
    Router router;

    public SnakeGame(Board board, Snake snake, Router router) throws Throwable{
        this.board = board;
        this.snake = snake;
        this.router = router;
        boardPanel = new BoardPanel(this);
//        boardPanel = RegistryManager.getInstance().newPlayer(BoardPanel.class, new Class[]{SnakeGame.class}, new Object[]{this});
//        boardPanel = Registry.getRegistry().newCore(BoardPanel.class, this);
        //boardPanel.init(this);
        statusPanel = new StatusPanel();
        statisticsPanel = new StatisticsPanel(this);

//        Component bp = (Component) boardPanel.getReal();
        add(boardPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
        add(statisticsPanel, BorderLayout.NORTH);

        setResizable(false);
        pack();

        setTitle("EzSnake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        start();
    }

    public void start(){
        router.setDirection(Router.DIRECTION_NONE);
        board.generateFood();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void reset(){
        router.reset();
        timer.setDelay(DELAY);
        speed = 0;
        timer.start();
    }

    public void pause(){
        router.setDirection(Router.DIRECTION_NONE);
    }

    public boolean isPause(){
        return router.getDirection() == Router.DIRECTION_NONE;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        router.update();
        if(router.gameOver) timer.stop();
        boardPanel.repaint();
        statisticsPanel.repaint();
    }

    public int getFoodEaten() {
        //return foodEaten;
        return snake.getFoodEaten();
    }

    public int getSpeed() {
        return speed+1;
        //return timer.getDelay();
    }

    public void increaseSpeed(){
        if(speed<9) {
            speed++;
            timer.setDelay(timer.getDelay() - DELAY_STEP);
        }
    }

    public void decreaseSpeed(){
        if(speed>0) {
            speed--;
            timer.setDelay(timer.getDelay() + DELAY_STEP);
        }
    }

    public static void main(String[] args) throws Throwable{
        Registry reg = Registry.getRegistry();

        //Board board = Player.initialize(Board.class, new Class[]{int.class, int.class}, new Object[]{SnakeGame.ROW_COUNT, SnakeGame.COL_COUNT});
//        Board board = RegistryManager.getInstance().newPlayer(Board.class, new Class[]{int.class, int.class}, new Object[]{SnakeGame.ROW_COUNT, SnakeGame.COL_COUNT});
//        Board board = reg.newCore(Board.class, SnakeGame.ROW_COUNT, SnakeGame.COL_COUNT);
        Board board = new Board(SnakeGame.ROW_COUNT, SnakeGame.COL_COUNT);

//        Snake snake = new Snake(new Cell(5, 6));
        Snake snake = new Snake(board, 5, 6);
        Router router = new Router(snake, board);
//        Router router = reg.newCore(Router.class, snake, board);

        JFrame ex = new SnakeGame(board, snake, router);
        ex.setVisible(true);

//        EventQueue.invokeLater(() -> {
//            JFrame ex = new SnakeGame(board, snake, router);
//            ex.setVisible(true);
//        });

        //Test binding and dump
        Compartment comp = reg.newCompartment(Compartment.class);
        comp.activate();

//        Thread watchService = new WatchService();
//        watchService.start();
//
//        do{
//            System.out.println(":::: Console command :::: ");
//            Scanner keyboard = new Scanner(System.in);
//            String key = keyboard.nextLine();
//            if(key.equalsIgnoreCase("dumpRelation")){
//                DumpHelper.dumpRelation();
//            }else if(key.equalsIgnoreCase("dumpCore")){
//                DumpHelper.dumpCoreObjects();
//            }else if(key.equalsIgnoreCase("dumpCompartment")) {
//                DumpHelper.dumpCompartments();
//            }else if(key.equalsIgnoreCase("dumpRole")){
//                DumpHelper.dumpRoles();
//            }else {
//                System.out.println("Invalid command. Try: dumpCore, dumpCompartment, dumpRelation");
//            }
//        }while(true);
    }

//    static class WatchService extends Thread{
//        public void run(){
//            try {
//                String dir = System.getProperty("user.dir");
//                Path p = Paths.get(dir + "/src/test/java/net/runtime/role/snake");
//                FileWatcher fileWatcher = FileWatcher.getInstance();
//                fileWatcher.register(p);
//                fileWatcher.monitor("evolution.xml");
//                fileWatcher.processEvents();
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//    }
}
