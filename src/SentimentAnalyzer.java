import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class WordStack {
    private static class Node {
        String data;
        Node next;

        Node(String data) {
            this.data = data;
        }
    }

    private Node top;

    public void push(String data) {
        Node newNode = new Node(data);
        newNode.next = top;
        top = newNode;
    }

    public boolean contains(String data) {
        Node current = top;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}

public class SentimentAnalyzer {
    WordStack positive = new WordStack();
    WordStack negative = new WordStack();
    WordStack neutral = new WordStack();

    public void readData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("positive.txt"))) {
            String positiveWord;
            while ((positiveWord = reader.readLine()) != null) {
                positive.push(positiveWord.toLowerCase());
            }
        } catch (IOException e) {
            System.out.println("Error reading positive.txt: " + e.getMessage());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("negative.txt"))) {
            String negativeWord;
            while ((negativeWord = reader.readLine()) != null) {
                negative.push(negativeWord.toLowerCase());
            }
        } catch (IOException e) {
            System.out.println("Error reading negative.txt: " + e.getMessage());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("neutral.txt"))) {
            String neutralWord;
            while ((neutralWord = reader.readLine()) != null) {
                neutral.push(neutralWord.toLowerCase());
            }
        } catch (IOException e) {
            System.out.println("Error reading neutral.txt: " + e.getMessage());
        }
    }

    public void analyzer() {
        Scanner sentenceInput = new Scanner(System.in);
        System.out.print("Enter the sentence: ");
        String sentence = sentenceInput.nextLine().toLowerCase().trim();

        if (sentence.isEmpty()) {
            System.out.println("Empty input. Please enter a sentence.");
            return;
        }
        String[] words = sentence.split("\\s+");

        int positiveWordCount = 0;
        int negativeWordCount = 0;
        int neutralWordCount = 0;

        for (String word : words) {
            if (positive.contains(word)) {
                positiveWordCount++;
            } else if (negative.contains(word)) {
                negativeWordCount++;
            } else {
                neutralWordCount++;
            }
        }

        if (negativeWordCount >= 1 && positiveWordCount == 0) {
            System.out.println("This sentence is overall negative!");
        } else if (neutralWordCount > 0) {
            if (positiveWordCount > negativeWordCount) {
                System.out.println("This sentence is overall positive!");
            } else if (negativeWordCount >= 2) {
                System.out.println("This sentence is overall negative!");
            } else {
                System.out.println("This sentence is neutral.");
            }
        } else {
            if (positiveWordCount - neutralWordCount > negativeWordCount) {
                System.out.println("This sentence is overall positive!");
            } else if (negativeWordCount >= 2) {
                System.out.println("This sentence is overall negative!");
            } else {
                System.out.println("This sentence is neutral.");
            }
        }

    }

    public static void main(String[] args){
        SentimentAnalyzer analyzer = new SentimentAnalyzer();
        analyzer.readData();
        analyzer.analyzer();
    }
}

