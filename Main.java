package homework;
import java.io.*;
import java.util.*;
public class Main {

    public static void main(String args[]) throws Exception {
        Song.read();
        Song.sorting();
    }
}
class Song {

    private int Position; // Just in case we going to need this value later
    private String TrackName; // Just in case we going to need this value later
    private String ArtistName; // Just in case we going to need this value later
    private int Streams; // Just in case we going to need this value later
    private String URL; // Just in case we going to need this value later
    private static ArrayList<String> artists = new ArrayList<>(); // Array for Artists
    private static ArrayList<String> badData = new ArrayList<>(); // Array for bad values with bunch of commas


    public Song(int Position, String TrackName, String ArtistName, int Streams, String URL) { //constructor
        this.Position = Position;
        this.TrackName = TrackName;
        this.ArtistName = ArtistName;
        this.Streams = Streams;
        this.URL = URL;
        artists.add(ArtistName.toUpperCase().replace("\"", "")); // getting rid of quotation marks
    }

    public static void read() throws Exception { // reading data from the file
        Scanner sc = new Scanner(new BufferedReader(new FileReader("/Users/klimenkoff9/Desktop/Job:documents/charts.txt")));
        int TempPosition;
        String TempTrackName;
        String TempArtistName;
        int TempStreams;
        String TempURL;
        String data;
        boolean dataEx = false;

        while (sc.hasNextLine()) {
            data = sc.nextLine(); // making sure there is nor less or more commas in the line
            int commas = 0;
            for (int i = 0; i < data.length(); i++) {
                if (data.charAt(i) == ',') {
                    commas++;
                }
            }
            if (commas != 4) { // if more or less making it is bad data
                badData.add(data);
                dataEx = true;
                continue;
            } else {
                Scanner scanner = new Scanner(data).useDelimiter(",");
                TempPosition = Integer.valueOf(scanner.next());
                TempTrackName = scanner.next();
                TempArtistName = scanner.next();
                TempStreams = Integer.valueOf(scanner.next());
                TempURL = scanner.next();
                new Song(TempPosition, TempTrackName, TempArtistName, TempStreams, TempURL); // scanning all the values just in case, pick the ones we need
            }
        }
        if (dataEx) {
            System.out.println("NOTE: Some data cannot be proceed due to Bad Value Format:\n"); // giving a user info about bad data lines
            for (int i = 0; i < badData.size(); i++) {
                System.out.println(badData.get(i));
            }
        }
    }

    public static void sorting() { // count the duplicates and sorting artists in the alphabetical order

        Map<String, Integer> duplicates = new HashMap<>();

        for (String str : artists) {  // first we found how many duplicates each artist has
            if (duplicates.containsKey(str)) {
                duplicates.put(str, duplicates.get(str) + 1);
            } else {
                duplicates.put(str, 1);
            }
        }

        TreeMap<String, Integer> sorted = new TreeMap<>();

        sorted.putAll(duplicates);

        System.out.println("");
        for (Map.Entry<String, Integer> entry : sorted.entrySet()) { // then we sort them alphabetically
            System.out.println("Artist " + entry.getKey() +
                    " shows up in the chart " + entry.getValue() + " time(s)");
            System.out.println("----------------------------------------------------------------------------");
        }
    }
}