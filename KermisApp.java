import java.util.Scanner;

/* Opdracht Kermis
Omzet van Kermis wordt bijgehouden.
Kermis heeft zes attracties, dit zijn subklasses van hoofdklasse attractie.
1 voor botsauto's, 2 voor spin, 3 voor spiegelpaleis, 4 voor spookhuis, 5 voor hawaii, 
6 voor ladderklimmen. */
public class KermisApp {
    public static void main(String[] args) {
        KermisManager kermisManager = new KermisManager();
        kermisManager.startUp();
    }
}

class KermisManager {
    
    /* FD: Wanneer de gebruiker een getal invult moet de juiste attractie doorgegeven worden.
    TS: De input wordt vergeleken met de collectie/array.
    TS: Vervolgens wordt een methode in die class van de array aangeroepen.
    TS: Voor testing purposes, maak de scanner een aantal keer loopend.*/
    Attractie[] attracties = new Attractie[6];
    boolean isEnded = false;
    int attractieIngaan = 6;

    String[] attractieNamen = {"botsauto", "spin", "spiegelpaleis", "spookhuis", "hawaii", "ladderklimmen"};

    /* FD: De gebruiker kan een getal ingeven/intypen.
    TS: Een Scanner moet geimport worden.
    TS: De Scanner moet geinitialiseerd worden.
    TS: Zorg ervoor dat de gebruiker zijn input kan geven. */

    /* FD: Wanneer de gebruiker op q/o/k klikt, dan stopt het spel/ wordt omzet getoond/ wordt aantal kaartjes laten zien.
    TS: De Scanner moet herkennen wat voor soort input gegeven wordt.
    TS: Maak if statements met wanneer de q als input wordt gegeven dan system.exit(0);
    TS: Maak if statements met wanneer de o wordt gegeven, dan tel alle omzet van elke attractie bij elkaar en geef dat weer.
    TS: Maak if statements met wanneer de k wordt gegeven, dan tel alle kaartjes van elke attractie bij elkaar en geef dat weer. */
    void startUp()
    {
        setAttracties();

        Scanner sc = new Scanner(System.in);
        while(!isEnded)
        {
            String input = sc.nextLine();
            if(input.length() != 1) {
                System.out.println("Dit snap ik niet!");
            } else {
                if(input.matches("^[1-6]*$")) 
                {
                    int inputNr = Integer.parseInt(input);
                    attracties[inputNr - 1].draaien();
                } 
                else 
                {
                    if(input.equals("q")) {
                        System.out.println("De kermis is gesloten.");
                        System.exit(0);
                        isEnded = true;
                    } 
                    else if(input.equals("o")) {
                        System.out.println("De totale omzet is: " + Kassa.totaleOmzet);
                        for(Attractie at : attracties)
                        {
                            if(at.omzet > 0)
                                System.out.println(at.naam + ": " + at.omzet);
                        }
                    } 
                    else if(input.equals("k")) 
                    {
                        System.out.println("Het totaal aantal gekochte kaartjes is: " + Kassa.totaalAantalKaartjes);
                        for(Attractie at : attracties)
                        {
                            if(at.kaartjesGekocht > 0)
                                System.out.println(at.naam + ": " + at.kaartjesGekocht);
                        }
                    } 
                    else {
                        System.out.println("Dit snap ik niet!");
                    }
                }
            }
        }
        if(sc != null)
            sc.close();
    }

    /* FD: De applicatie moet een selectie met attracties hebben, met hun eigen prijs, naam en oppervlakte.
    TS: De attracties moeten een voor een worden geinstantieerd.
    TS: De attracties moeten een naam en prijs worden gegeven.
    TS: Deze aangemaakte attracties moeten teruggekoppeld worden aan de attracties array. */
    void setAttracties()
    {
        Attractie botsAuto = new BotsAuto();
        botsAuto.naam = attractieNamen[0];
        botsAuto.prijs = 2.50f;
        attracties[0] = botsAuto;
        
        RisicoRijkeAttracties spin = new Spin();
        spin.naam = attractieNamen[1];
        spin.prijs = 2.25f;
        spin.draaiLimiet = 5;
        attracties[1] = spin;

        Attractie spiegelPaleis = new SpiegelPaleis();
        spiegelPaleis.naam = attractieNamen[2];
        spiegelPaleis.prijs = 2.75f;
        attracties[2] = spiegelPaleis;

        Attractie spookhuis = new Spookhuis();
        spookhuis.naam = attractieNamen[3];
        spookhuis.prijs = 3.20f;
        attracties[3] = spookhuis;

        RisicoRijkeAttracties hawaii = new Hawaii();
        hawaii.naam = attractieNamen[4];
        hawaii.prijs = 2.90f;
        hawaii.draaiLimiet = 10;
        attracties[4] = hawaii;

        Attractie ladderKlimmen = new LadderKlimmen();
        ladderKlimmen.naam = attractieNamen[5];
        ladderKlimmen.prijs = 5.00f;
        attracties[5] = ladderKlimmen;
    }
}

/* FD: De gebruiker moet de totale omzet kunnen zien.
TS: De totale omzet moet worden bijgehouden in de kassa.
TS: We maken de totaleOmzet en totaalAantalKaartjes static, hierdoor kunnen we hem blijven updaten via elke class */
class Kassa {
    static float totaleOmzet = 0;
    static int totaalAantalKaartjes = 0;
}

abstract class Attractie {
    String naam;
    float prijs;
    int oppervlakte;
    float omzet;
    int kaartjesGekocht;
    
    void draaien()
    {
        System.out.println("draaien in " + naam);
        omzet += prijs;
        Kassa.totaleOmzet += prijs;
        kaartjesGekocht += 1;
        Kassa.totaalAantalKaartjes += 1;
        
        if(this instanceof GokAttractie)
        {
            //Roep kansSpelBelastingBetalen(); aan.
            //GokAttractie aftrekken = (x) -> x - ((x / 100) * 30);
        }
    }
}

abstract class RisicoRijkeAttracties extends Attractie {
    //Aantal keer dat deze attracties mogen draaien voordat onderhoud plaatsvind.
    int draaiLimiet;
    int onderhoudAantal;

    void draaien() {
        
        int limiet = draaiLimiet * (onderhoudAantal + 1);
        
        if(kaartjesGekocht >= limiet)
        {
            onderhoudAantal++;
            opstellingsKeuring();
            return;
        }
        else
            super.draaien();
    }

    abstract void opstellingsKeuring();

    abstract void onderhoudsBeurt();
}

class BotsAuto extends Attractie { }

class Spin extends RisicoRijkeAttracties {

    void opstellingsKeuring()
    {
        System.out.println("Er moet een keuring worden ondergaan in Spin!");
        onderhoudsBeurt();
    }

    void onderhoudsBeurt()
    {
        System.out.println("Onderhoudsbeurt in spin");
    }
}

class SpiegelPaleis extends Attractie { }

class Spookhuis extends Attractie { }

class Hawaii extends RisicoRijkeAttracties {

    void opstellingsKeuring()
    {
        System.out.println("Er moet een keuring worden ondergaan in Hawaii!");
        onderhoudsBeurt();
    }

    void onderhoudsBeurt()
    {
        System.out.println("Onderhoudsbeurt in hawaii");
    }
}

class LadderKlimmen extends Attractie implements GokAttractie {

    public void kansSpelBelastingBetalen()
    {
        System.out.println("Belasting betalen in ladderklimmen");
        //kansSpelBelasting = 30% van omzet. Dit wordt opzij gezet, en voor nu niet opgeslagen.
    }
}

interface GokAttractie {

    public abstract void kansSpelBelastingBetalen();
}