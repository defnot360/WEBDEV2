package com.ern.jumon.coffee;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class CoffeeService {
    private final ArrayList<CoffeeService> coffees;
    private final String FILE_NAME = "data/coffee_database.csv";

    /**
     * Initializes the coffee list and loads data from disk.
     */
    public CoffeeService() {
        coffees = new ArrayList<>();
        readFromDisk();
    }

    /**
     * Returns the list of all coffees.
     */
    public ArrayList<CoffeeService> getCoffees() {
        return coffees;
    }

    /**
     * Deletes a coffee entry by ID.
     */
    public void deleteCoffee(int id) {
        coffees.removeIf(c -> c.getId() == id);
        writeToDisk();
    }

    /**
     * Searches for coffees by a keyword across multiple fields.
     */
    public List<CoffeeService> searchCoffee(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return coffees;
        }

        String lower = keyword.toLowerCase();
        return coffees.stream().filter(c ->
                {
                    assert c.getName() != null;
                    if (c.getName().toLowerCase().contains(lower)) return true;
                    assert c.getType() != null;
                    if (c.getType().toLowerCase().contains(lower) ||
                            c.getSize().toLowerCase().contains(lower)) return true;
                    assert c.getRoastLevel() != null;
                    if (c.getRoastLevel().toLowerCase().contains(lower) ||
                            Objects.requireNonNull(c.getOrigin()).toLowerCase().contains(lower)) return true;
                    assert c.getFlavorNotes() != null;
                    if (c.getFlavorNotes().toString().toLowerCase().contains(lower)) return true;
                    assert c.getBrewMethod() != null;
                    return c.getBrewMethod().toLowerCase().contains(lower) ||
                            (c.isDecaf() && (lower.contains("decaf") || lower.contains("decaffeinated")));
                }
        ).collect(Collectors.toList());
    }

    private boolean isDecaf() {
        return false;
    }

    private String getBrewMethod() {
        return null;
    }

    private Object getFlavorNotes() {
        return null;
    }

    private String getOrigin() {
        return null;
    }

    private String getRoastLevel() {
        return null;
    }

    private String getSize() {
        return null;
    }

    private String getType() {
        return null;
    }

    private String getName() {
        return null;
    }

    /**
     * Retrieves a coffee by its ID.
     */
    public CoffeeService getCoffee(int id) {
        for (CoffeeService c : coffees) {
            if (c.getId() == id)
                return c;
        }
        return null;
    }

    /**
     * Updates an existing coffee entry.
     */
    public void updateCoffee(int id, CoffeeService update) {
        for (int i = 0; i < coffees.size(); i++) {
            if (coffees.get(i).getId() == id) {
                coffees.set(i, update);
                writeToDisk();
                break;
            }
        }
    }

    private int getId() {
        return 0;
    }

    /**
     * Adds a new coffee entry and assigns a new ID.
     */
    public void addCoffee(CoffeeService coffee) {
        coffee.setId(getLastId() + 1);
        coffees.add(coffee);
        writeToDisk();
    }

    private void setId(int i) {
    }

    /**
     * Returns the highest ID currently in the coffee list.
     */
    public int getLastId() {
        if (coffees.isEmpty()) {
            return 0;
        }
        return coffees.getLast().getId();
    }

    /**
     * Saves all coffee data to disk in CSV format.
     */
    public void writeToDisk() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (CoffeeService c : coffees) {
                bw.write(c.getId() + ","
                        + c.getName() + ","
                        + c.getType() + ","
                        + c.getSize() + ","
                        + c.getPrice() + ","
                        + c.getRoastLevel() + ","
                        + c.getOrigin() + ","
                        + c.isDecaf() + ","
                        + c.getStock() + ","
                        + c.getBrewMethod() + ","
                        + c.getCoffeePicture() + ","
                        + String.join((CharSequence) ";", (CharSequence) c.getFlavorNotes()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Uh-oh! Error writing: " + e.getMessage());
        }
    }

    private int getStock() {
        return 0;
    }

    private int getCoffeePicture() {
        return 0;
    }

    private int getPrice() {
        return 0;
    }

    /**
     * Loads coffee data from the CSV file if it exists.
     */
    public void readFromDisk() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No coffee file found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 11) continue;

                CoffeeService c = new CoffeeService();
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
                c.setCoffeePicture(data[10]);

                // Handle FlavorNotes
                if (data.length >= 12 && !data[11].isEmpty()) {
                    c.setFlavorNotes(data[11].trim());
                } else {
                    c.setFlavorNotes(""); // Empty string if no flavor notes
                }

                coffees.add(c);
            }
        } catch (IOException e) {
            System.out.println("Uh-oh! Error reading: " + e.getMessage());
        }
    }

    private void setFlavorNotes(String trim) {
    }

    private void setCoffeePicture(String datum) {
    }

    private void setBrewMethod(String datum) {
    }

    private void setStock(int i) {
    }

    private void setDecaf(boolean b) {
    }

    private void setOrigin(String datum) {
    }

    private void setRoastLevel(String datum) {
    }

    private void setPrice(double v) {
    }

    private void setSize(String datum) {
    }

    private void setType(String datum) {
    }

    private void setName(String datum) {
    }
}
