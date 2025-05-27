package ernesto.Services;

import ernesto.Model.AppUser;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final List<AppUser> appUsers = new ArrayList<>();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostConstruct
    public void init() {
        // Load users.csv from classpath (e.g., src/main/resources/demo/data/users.csv)
        ClassPathResource resource = new ClassPathResource("demo/data/users.csv");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line = reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 2) continue; // skip malformed lines

                AppUser appUser = new AppUser();
                appUser.setUsername(parts[0].trim());

                // Assume CSV stores plain text passwords; encode them here
                String rawPassword = parts[1].trim();
                String encodedPassword = passwordEncoder.encode(rawPassword);
                appUser.setPassword(encodedPassword);

                appUsers.add(appUser);
            }

        } catch (IOException e) {
            // Log error and continue with empty user list
            System.err.println("Failed to load users.csv: " + e.getMessage());
        }
    }

    public AppUser findByUsername(String username) {
        return appUsers.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void save(AppUser appUser) {
        // Optional: encode password before saving
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        synchronized (appUsers) {
            appUsers.add(appUser);
        }
    }
}
