package game;

import java.util.Random;

public class Rand {
    Random rand;

    public Rand(){
        rand = new Random();
    }

    public int lengthRandom(int v){
        return rand.nextInt(v);
    }
}
