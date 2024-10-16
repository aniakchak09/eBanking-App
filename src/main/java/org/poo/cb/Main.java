package org.poo.cb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args == null) {
            System.out.println("Running Main");

            return;
        }

        String commandsPath = "build/resources/main/" + args[2];
        String stocksPath = "src/main/resources/" + args[1];

        CentralApp app = CentralApp.getInstance();  //using the little ditty Singleton thingy
        app.loadStocks(stocksPath);

        try (BufferedReader br = new BufferedReader(new FileReader(commandsPath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                String command = data[0] + " " + data[1];

                if (app.getCommands().containsKey(command)) {
                    app.getCommands().get(command).execute(data);
                } else {
                    System.out.println("Command not found");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        app.reset();
    }
}