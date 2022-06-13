import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Airlines {
    private HashMap<Integer, String> airportsIDs;
    private HashMap<String, Integer> airportsNames;
    private HashMap<Integer, HashSet<Integer>> routesIds;

    public Airlines(String airports,String routes){
        airportsIDs = new HashMap<Integer, String>();
        airportsNames = new HashMap<String, Integer>();
        routesIds = new HashMap<Integer, HashSet<Integer> >();
        loadAirports(airports);
        loadRoutes(routes);
    }

    public HashMap<Integer, String> getAirportsByIDs() { return airportsIDs; }

    public HashMap<Integer, HashSet<Integer>> getRoutesIds() { return routesIds; }

    public HashMap<String, Integer> getAirportsByNames() { return airportsNames; }

    public Integer searchAirport(String Name){
        Integer id=airportsNames.get(Name);
        if(id==null)
            return null;
        return id;
    }

    public LinkedList<Integer> searchPath(Integer srcId,Integer destId){
        HashMap<Integer,Integer> parents=new HashMap<Integer,Integer>();
        LinkedList<Integer> queue=new LinkedList<Integer>();
        LinkedList<Integer> path=new LinkedList<Integer>();
        queue.add(srcId);
        parents.put(srcId,null);
        while(!queue.isEmpty()) {
            Integer current = queue.removeFirst();
            Integer parent = parents.get(current);
            if (current == destId)
                break;
            for (Integer next : routesIds.get(current)) {
                if (!parents.containsKey(next)) {
                    queue.addLast(next);
                    parents.put(next, current);
                }
            }
        }
        if(!parents.containsKey(destId))
            return null;

        Integer current=destId;
        path.addFirst(current);
        while((current=parents.get(current))!=null){
            path.addFirst(current);
        }
        return path;
    }

    private void loadAirports(String fileName) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String airport = null;
            while ((airport = reader.readLine()) != null) {
                try {
                    String[] data = airport.split(",");
                    if (data.length == 14) {
                        Integer airportId = Integer.parseInt(data[0]);
                        String pom = data[1];
                        String airportName=pom.replaceAll("\"","");
                        airportsIDs.put(airportId,airportName);
                        airportsNames.put(airportName, airportId);
                        routesIds.put(airportId, new HashSet<Integer>());
                    }
                }catch (Exception exc){
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadRoutes(String fileName) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String route = null;
            while ((route = reader.readLine()) != null) {
                try {
                    String[] data = route.split(",");
                    if (data.length == 9) {
                        Integer sourceId = Integer.parseInt(data[3]);
                        Integer destinationId = Integer.parseInt(data[5]);
                        if (airportsIDs.containsKey(sourceId) && airportsIDs.containsKey(destinationId)) {
                            routesIds.get(sourceId).add(destinationId);
                        }
                    }
                }catch (Exception exc){
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
