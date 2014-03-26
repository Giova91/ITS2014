

package ITS;


public class Risposta {
    public static Risposta OK = new Risposta("ok");
    
    public String messaggio;
    
    public Risposta(String messaggio)
    {
        this.messaggio=messaggio;
    }
}
