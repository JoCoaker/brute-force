package seb.algo.rithmen;

import java.util.ArrayList;

public class Pfad {

    int x = 0;
    int y = 0;

    ArrayList<Weg> pfade = new ArrayList<>();


    public void wegGehen(Weg weg) {
        x += weg.getX();
        y += weg.getY();

        pfade.add(weg);
    }

}
