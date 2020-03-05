// Denys Klimenkov
// MY9
package classwork;
import java.io.*;
import java.util.*;

public class Assigment2 {

    public static void main(String[] args) throws Exception {

        String[] fileNames = {"/Users/klimenkoff9/Desktop/practiceGit/songsplay/regional-global-daily-latest.csv", "/Users/klimenkoff9/Desktop/practiceGit/songsplay/regional-global-daily-2020-03-03.csv"};

        ArrayList<MyQueue> weekchart = new ArrayList<>();

        for (int i = 0; i < fileNames.length; i++) { // read the files
            weekchart.add(new MyQueue());

            weekchart.get(i).convert(fileNames[i]);
            weekchart.get(i).sort();
        }
        for (int i = 0; i < weekchart.size(); i++) { // convert files in a loop
            System.out.println("\nLIST OF WEEK NUMBER " + (i + 1) + "\n");
            System.out.println(weekchart.get(i));
        }

        System.out.println("\n\n\t\tMERGED QUEUE " + "\t\t\n\n"); // creates new queue
        weekchart.add(new MyQueue());
        weekchart.set(2, weekchart.get(2).mergingFunction(weekchart.get(0), weekchart.get(1)));

        System.out.println(weekchart.get(2));


        Playlist playlist1 = new Playlist(); // making a playlist
        SongHistoryList historyList1 = new SongHistoryList();

        playlist1.addSong("song 1");
        playlist1.addSong("song 2");
        playlist1.addSong("song 3");

        System.out.println(playlist1);

        for (int i = 0; i < 3; i++) {

            System.out.println("\nListening to: " + playlist1.getFirst() + "");

            historyList1.addSong(playlist1.listenToSong());

            System.out.println("Last Song Listened: " + historyList1.getFirst());
        }

        System.out.println("\n");
        System.out.println(historyList1);

    }
}
class MyQueue { // queue implementation

    LinkedList queue = new LinkedList();

    MyQueue() {
        queue = new LinkedList();
    }

    void convert(String file) throws Exception {

        BufferedReader br = null;

        int i = 0;
        String[] readData = null;

        String line = "";

        br = new BufferedReader(new FileReader(file));
        int counter = 0;
        br.readLine();
        br.readLine();
        while ((line = br.readLine()) != null) {
            readData = line.split(",");
            readData[1] = readData[1].replaceAll("^\"|\"$", ""); // fixes quotation marks
            enqueue(readData[1]);
        }
    }

    MyQueue mergingFunction(MyQueue q1, MyQueue q2) { // merges two queues into one
        String element = "";
        MyQueue mergedQueue = new MyQueue();
        while( !(q1.isEmpty() && q2.isEmpty()) ) {

            if(!q1.isEmpty()) {
                element = q1.dequeue();
                if(!mergedQueue.findElement(element))
                    mergedQueue.enqueue(element);
            }

            if(!q2.isEmpty()) {
                element = q2.dequeue();
                if(!mergedQueue.findElement(element))
                    mergedQueue.enqueue(element);
            }

        }

        return mergedQueue;

    }

    void sort() {
        queue.sortList();
    }

    private boolean findElement(String e) {
        return queue.findElement(e);
    }

    private String dequeue() {
        return queue.removeFirst();
    }

    private void enqueue(String data) {
        queue.addLast(data);
    }

    private boolean isEmpty() {
        return queue.isEmpty();
    }

    public String toString() {
        return queue.toString();
    }
}
class LinkedList{ // LL class implementation

    private static class Node{ // class for Node

        private String data;
        private Node next;

        Node(String data) { // constructor
            this.data = data;
            this.next = null;
        }

        Node(String data, Node next) { // constructor
            this.data = data;
            this.next = next;
        }

    }

    private Node header, tail;
    private int size;

    LinkedList() { // constructor
        this.header = new Node(null);
        this.tail = header;
        this.size = 0;
    }

    public String first() { // constructor
        if (this.isEmpty()) return null;
        return this.header.next.data;
    }

    public String last() { // constructor
        if (this.isEmpty()) return null;
        return this.tail.data;
    }

    public int size() {
        return this.size;
    }

    boolean isEmpty() {
        return this.header.next == null;
    }

    public void addFirst(String e) {
        Node newNode;
        newNode = new Node(e, this.header.next);
        this.header.next = newNode;
        this.size++;
    }

    void addLast(String e) {
        Node newNode = new Node(e);
        this.tail.next = newNode;
        this.tail = newNode;
        this.size++;
    }

    String removeFirst() {
        if (this.isEmpty()) return null;
        String val = this.header.next.data;
        this.header.next = this.header.next.next;
        this.size--;
        return val;
    }

    public String toString() {

        StringBuilder sb = new StringBuilder();

        Node current = this.header;

        while(current.next != null) {
            current = current.next;
            sb.append(String.format("%s \n ", current.data));
        }

        return sb.toString();
    }

    boolean findElement(String e) { // if already exists in the file
        Node current = this.header.next;

        boolean flag = false; //does not exist in list

        while(current != null) {

            if(current.data.compareToIgnoreCase(e) == 0){
                flag = true; //exists in list
                break;
            }

            current = current.next;
        }

        return flag;
    }

    void sortList() { // current is gonna point to to next head
        Node current = this.header.next;
        Node index = null;
        String temp;

        if(header == null) {
            return;
        }
        else {
            while(current != null) { // index point to next to current

                index = current.next;

                while(index != null) { // in case the current node's data is bigger than the index's data its gonna swap them
                    String str_current = current.data;
                    String str_index = index.data;
                    if( str_current.compareToIgnoreCase(str_index) > 0) {
                        temp = current.data;
                        current.data = index.data;
                        index.data = temp;
                    }
                    index = index.next;
                }
                current = current.next;
            }
        }
    }
}
class Playlist {

    private Song first, last;
    private int size;

    public void Playlist() {
        this.first = null;
        this.last = first;
        this.size = 0;
    }

    public int getSize() {
        return this.size;
    }

    Song getFirst() {
        return this.first;
    }

    //addFront()
    void addSong(String s) {
        if(this.size == 0) {
            this.first = new Song(s);
            this.last = first;
            this.size++;
        } else {
            Song newNode = new Song(s);
            newNode.setNext(this.first);
            this.last = this.first;

            this.first = newNode;
            this.size++;
        }
    }

    Song listenToSong() {
        if (this.size == 0) {
            System.out.println("Empty playlist");
            return null;
        }

        Song current = this.first;
        this.first = this.first.getNext();

        this.size--;

        return current;
    }

    public String toString() {

        if (this.size == 0) return "[EMPTY PLAYLIST]";

        StringBuilder sb = new StringBuilder();
        sb.append("Playlist:[");

        Song current = this.first;

        while(current.getNext() != null) {
            sb.append(String.format("%s --> ", current.getTrack()));
            current = current.getNext();
        }
        sb.append(String.format("%s --> END]", current.getTrack()));

        return sb.toString();
    }

}
class Song {

    private String track;
    private Song next;
    Song(String track) {
        this.track = track;
        this.next = null;
    }

    public Song(String track, Song next) {
        this.track = track;
        this.next = next;
    }

    String getTrack() {
        return track;
    }

    Song getNext() {
        return next;
    }

    void setNext(Song next) {
        this.next = next;
    }

    public String toString() {
        return "" + this.track;
    }
}
class SongHistoryList  {

    private Song first, last;
    private int size;

    public void SongHistoryList () {
        this.first = null;
        this.last = first;
        this.size = 0;
    }

    Song getFirst() {
        return this.first;
    }

    //addFront()
    void addSong(Song s) {
        if(this.size == 0) {
            this.first = new Song(s.getTrack());
            this.last = first;
            this.size++;
        } else {
            Song newNode = new Song(s.getTrack());
            newNode.setNext(this.first);
            this.last = this.first;

            this.first = newNode;
            this.size++;
        }
    }

    //removeFront()
    public Song lastListened() {
        if (this.size == 0) {
            System.out.println("Empty Song History");
            return null;
        }

        Song current = this.first;

        this.first = this.first.getNext();
        this.size--;

        return current;
    }

    public String toString() {

        if (this.size == 0) return "[EMPTY SONG HISTORY]";

        StringBuilder sb = new StringBuilder();
        sb.append("Song History:[");

        Song current = this.first;

        while(current.getNext() != null) {
            sb.append(String.format("%s --> ", current.getTrack()));
            current = current.getNext();
        }
        sb.append(String.format("%s --> END]", current.getTrack()));

        return sb.toString();
    }

}
