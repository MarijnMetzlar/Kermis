import java.util.Scanner;

/* Opdracht Kermis
Omzet van Kermis wordt bijgehouden.
Kermis heeft zes attracties, dit zijn subklasses van hoofdklasse attractie.
1 voor botsauto's, 2 voor spin, 3 voor spiegelpaleis, 4 voor spookhuis, 5 voor hawaii, 
6 voor ladderklimmen. */
public class KermisApp {
    public static void main(String[] args) {
        OmzetManager omzetKermis = new OmzetManager();
        omzetKermis.startUp();
    }
}

class OmzetManager {
    
    /* FD: Wanneer de gebruiker een getal invult moet de juiste attractie doorgegeven worden.
    TS: De input wordt vergeleken met de collectie/array.
    TS: Vervolgens wordt een methode in die class van de array aangeroepen.
    TS: Voor testing purposes, maak de scanner een aantal keer loopend.*/
    Attractie[] attracties = new Attractie[6];
    int attractieIngaan = 5;
    
    /* FD: De gebruiker kan een getal ingeven/intypen.
    TS: Een Scanner moet geimport worden.
    TS: De Scanner moet geinitialiseerd worden.
    TS: Zorg ervoor dat de gebruiker zijn input kan geven. */
    void startUp()
    {
        setAttracties();
        Scanner sc = new Scanner(System.in);
        for(int i = 0; i < attractieIngaan; i++)
        {
            int input = sc.nextInt();
            pickAttractie(input);
        }
    }

    void setAttracties()
    {
        attracties[0] = new BotsAuto();
        attracties[1] = new Spin();
    }

    void pickAttractie(int i)
    {
        if(attracties[i] != null)
            attracties[i].draaien();
        else
            System.out.println(attracties[i]);
    }
}

abstract class Attractie {
    String naam;
    float prijs;
    int oppervlakte;
    
    abstract void draaien();
}

class BotsAuto extends Attractie {

    void draaien()
    {
        System.out.println("Draaien in botsauto");
    }
}

class Spin extends Attractie {

    void draaien()
    {
        System.out.println("Draaien in spin");
    }
}