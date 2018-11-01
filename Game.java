import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Scanner;

public class Game implements Runnable {

    private Display display;
    public int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;
    private PackOfCards deck;
    private Scanner reader;
    private int nbOfCards;

    public Game(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;
    }

    private void init(){
        display = new Display(title, width, height);
        Assets.load("/res/img/pixeldeck3_double.png");
        deck = new PackOfCards();
        deck.initStandardPack();
        deck.shuffle();
        deck.getTop().toggleSelect();
        reader = new Scanner(System.in);
        nbOfCards = 0;
    }

    private void tick() {
        String s = reader.nextLine();

        switch (s) {
            case "":
                deck.shuffle();
                break;
            case "e":
                deck.extract();
                break;
            case "t":
                deck.getTop().toggleShow();
                break;
            case "s":
                deck.getTop().toggleSelect();
                break;
        }
//        if (s.equals("l"))
//            deck.extract();
//        else
//            deck.insertRandomCard();
    }

    private void render(){
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        //Clear Screen
        g.clearRect(0, 0, width, height);
        //Draw Here!

        int tmpX = 0, tmpY = 0;

        for (CardGUI c : deck.getCards()) {
            g.drawImage(Assets.getCardImg(c),
                    20 + 45 *tmpX, 20 + 60 * tmpY, null);
            tmpX++;
//            if (tmpX + tmpY*13 >= nbOfCards)
//                break;

            if (tmpX > 12) {
                tmpX = 0;
                tmpY++;
            }
        }

        //End Drawing!
        bs.show();
        g.dispose();
    }

    public void run(){

        init();

        while(running){
            tick();
            render();
        }

        stop();

    }

    public synchronized void start(){
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

