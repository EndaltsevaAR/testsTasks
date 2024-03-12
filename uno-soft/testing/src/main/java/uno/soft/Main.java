package uno.soft;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("There is no filename");
            System.exit(-1);
        }
        FileProcessor processor = new FileProcessor(args[0]);
        try {
            processor.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
