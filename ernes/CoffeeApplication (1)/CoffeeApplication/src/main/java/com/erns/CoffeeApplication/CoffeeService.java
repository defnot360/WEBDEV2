package com.erns.CoffeeApplication;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoffeeService {
    private List<Coffee> coffeeList;
    private final String FILE_NAME = "database.csv";

    public CoffeeService() {
        coffeeList = new ArrayList<>();
        readFromDisk();
    }

    public List<Coffee> getCoffeeList() {
        return coffeeList;
    }

    public void deleteCoffee(int id) {
        coffeeList.removeIf(coffee -> coffee.getId() == id);
        writeToDisk();
    }

    public List<Coffee> searchCoffee(String keyword){
        if(keyword.trim().isEmpty()){
            return new ArrayList<>(coffeeList);
        }

        return coffeeList.stream().filter(s ->
                s.getName() != null && s.getName().toLowerCase().contains(keyword.toLowerCase())
                        || s.getType() != null && s.getType().toLowerCase().contains(keyword.toLowerCase())
                        || s.getSize() != null && s.getSize().toLowerCase().contains(keyword.toLowerCase())
                        || s.getBrewMethod() != null && s.getBrewMethod().toLowerCase().contains(keyword.toLowerCase())
                        || s.getFlavorNotes() != null && s.getFlavorNotes().contains(keyword.toLowerCase())
                        || s.getRoastLevel() != null && s.getRoastLevel().toLowerCase().contains(keyword.toLowerCase())
                        || s.getOrigin() != null && s.getOrigin().toLowerCase().contains(keyword.toLowerCase())
        ).collect(Collectors.toList());
    }

    public Coffee getCoffee(int id){
        for(Coffee s: coffeeList){
            if(s.getId() == id)
                return s;
        }
        return null;
    }

    public void updateCoffee(int id, Coffee update){
        for(int i = 0; i < coffeeList.size(); i++){
            if(coffeeList.get(i).getId() == id){
                coffeeList.set(i, update);
                writeToDisk();
                break;
            }
        }
    }

    public void addCoffee(Coffee coffee){
        coffeeList.add(coffee);
        writeToDisk();
    }

    public int getId(){
        if(coffeeList.isEmpty()){
            return 0;
        }
        return coffeeList.get(coffeeList.size() - 1).getId();
    }

    public void writeToDisk(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))){
            //write the content of the arraylist into csv
            System.out.println("Writing to file");
            for(Coffee s : coffeeList){
                String line = s.getId() + ","
                        + s.getName() + ","
                        + s.getType() + ","
                        + s.getSize() + ","
                        + s.getPrice() + ","
                        + s.getRoastLevel() + ","
                        + s.getOrigin() + ","
                        + s.isDecaf() + ","
                        + s.getStock() + ","
                        +  s.getBrewMethod() + ","
                        + String.join((CharSequence) ";", (CharSequence) (s.getFlavorNotes() != null ? Collections.singleton(s.getFlavorNotes()) : Collections.singleton(new ArrayList<>())));
                bw.write(line);
                bw.newLine();
            }
            System.out.println("Done writing to file");
        }catch(IOException e){
            System.out.println("Woah! Error: " + e.getMessage());
        }
    }


    public void readFromDisk(){
        File file = new File(FILE_NAME);
        if(!file.exists()){
            System.out.println("file not found");
            return;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while((line = br.readLine()) != null){
                String[] data = line.split(",");

                Coffee c = new Coffee();
                c.setId(Integer.parseInt(data[0]));
                c.setName(data[1]);
                c.setType(data[2]);
                c.setSize(data[3]);
                c.setPrice(Double.parseDouble(data[4]));
                c.setRoastLevel(data[5]);
                c.setOrigin(data[6]);
                c.setDecaf(Boolean.parseBoolean(data[7]));
                c.setStock(Integer.parseInt(data[8]));
                c.setBrewMethod(data[9]);
                c.setFlavorNotes(data[10].isEmpty() ? new ArrayList<>() : new ArrayList<>(Arrays.asList(data[10].split(","))));
                //add coffee to the list
                coffeeList.add(c);
            }
            System.out.println("Done reading from file");
        }catch(IOException e){
            System.out.println("Wow! Error: " + e.getMessage());
        }
    }
}