import java.util.*;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {

        Airlines airlines=new Airlines("data/airports.dat","data/routes.dat");

        Scanner sc=new Scanner(System.in);
        System.out.println("Imena unosite bez navodinka ");
        System.out.println("Unesite ime polaznog aerodroma");
        String srcName = sc.nextLine();
        System.out.println("Unesite ime krajnjeg aerodroma");
        String destName = sc.nextLine();

        Integer srcId = airlines.searchAirport(srcName);
        Integer destId = airlines.searchAirport(destName);

        if (srcId == null)
            System.out.println("Ne postoji polazni aerodrom");
        else if (destId == null)
            System.out.println("Ne postoji krajnji aerodrom");
        else {
            LinkedList<Integer> path;
            path = airlines.searchPath(srcId, destId);
            if (path == null)
                System.out.println("Ne postoji veza");
            else {
                int countFlights = -1;
                for (Integer id : path) {
                    countFlights++;
                    String name = airlines.getAirportsByIDs().get(id);
                    System.out.println(name);
                }
                System.out.println("Broj letova: " + countFlights);
            }
        }
        sc.close();
    }
}
