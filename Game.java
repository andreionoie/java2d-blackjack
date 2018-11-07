import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Scanner;

// credit: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
public class Game implements Runnable {

    private Display display;
    public int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;
    private PackOfCards deck, playerHand, dealerHand;
    private Scanner reader;
    private int nbOfCards;
    private CardsGame cardsGame;

    public Game(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;
    }

    private void init(){
        display = new Display(title, width, height);
        Assets.load("/res/img/pixeldeck3_double.png");
//        deck = new PackOfCards();
//        deck.initStandardPack();
//        deck.shuffle();
//        deck.getTop().toggleSelect();
        reader = new Scanner(System.in);
        cardsGame = new CardsGame(1, reader);

        nbOfCards = 0;
    }

    private void tick() {
        cardsGame.tickGameLoop();

        playerHand = cardsGame.getPlayerDeck();
        dealerHand = cardsGame.getDealerDeck();

//        switch (s) {
//            case "":
//                deck.shuffle();
//                break;
//            case "e":
//                deck.extract();
//                break;
//            case "t":
//                deck.getTop().toggleShow();
//                break;
//            case "s":
//                deck.getTop().toggleSelect();
//                break;
//        }

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

        Assets.drawPack(20, 20, dealerHand, g);
        Assets.drawPack(20, height - 120, playerHand, g);

        //End Drawing!
        bs.show();
        g.dispose();
    }

    public void run(){

        init();

        while(running){
            render();
            tick();
            render();
        }

        stop();

    }

    public synchronized void start(){
        if (running)
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

