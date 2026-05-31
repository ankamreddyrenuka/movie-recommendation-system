package com.cinematchai.config;

import com.cinematchai.model.DestinationEntity;
import com.cinematchai.repository.DestinationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final DestinationRepository destinationRepository;

    public DataLoader(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (destinationRepository.count() > 0) return; // don't reseed

        // Core named destinations required by the task
        String[][] core = new String[][]{
                {"Goa","India","Beach","Tropical beaches, nightlife, and seafood.","https://images.unsplash.com/photo-1507525428034-b723cf961d3e","15.2993","74.1240"},
                {"Kerala","India","Backwaters","Lush backwaters, Ayurvedic retreats, and spice tours.","https://images.unsplash.com/photo-1549880338-65ddcdfd017b","9.9312","76.2673"},
                {"Manali","India","Hills","Mountain valleys, adventure sports, and scenic walks.","https://images.unsplash.com/photo-1501785888041-af3ef285b470","32.2432","77.1892"},
                {"Ooty","India","Hills","Tea gardens, cool climate, and Nilgiri landscapes.","https://images.unsplash.com/photo-1500534314209-a25ddb2bd429","11.4064","76.6950"},
                {"Vizag","India","Coast","Beaches, seafood, and marine aquarium.","https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05","17.6868","83.2185"},
                {"Jaipur","India","Heritage","Palaces, forts, and vibrant markets.","https://images.unsplash.com/photo-1549880338-65ddcdfd017b","26.9124","75.7873"},
                {"Ladakh","India","Mountain","High-altitude deserts, monasteries, and rugged scenery.","https://images.unsplash.com/photo-1500530855697-b586d89ba3ee","34.1526","77.5770"},
                {"Bali","Indonesia","Beach","Tropical islands, rice terraces, and cultural traditions.","https://images.unsplash.com/photo-1507525428034-b723cf961d3e","-8.3405","115.0920"},
                {"Tokyo","Japan","City","High-tech metropolis, food, and temples.","https://images.unsplash.com/photo-1549692520-acc6669e2f0c","35.6895","139.6917"},
                {"Dubai","UAE","Desert","Luxury shopping, modern architecture, and desert safaris.","https://images.unsplash.com/photo-1493558103817-58b2924bce98","25.2048","55.2708"},
                {"Paris","France","City","Iconic landmarks, art museums, and cuisine.","https://images.unsplash.com/photo-1493558103817-58b2924bce98","48.8566","2.3522"},
                {"London","UK","City","Historic sites, theatre, and urban parks.","https://images.unsplash.com/photo-1501785888041-af3ef285b470","51.5074","-0.1278"}
        };

        var seeds = new java.util.ArrayList<DestinationEntity>();
        int idCounter = 1;

        // Add core destinations first
        for (String[] c : core) {
            var d = new DestinationEntity();
            d.setDestinationId(String.format("dest-%03d", idCounter++));
            d.setName(c[0]);
            d.setCountry(c[1]);
            d.setRegion(c[2]);
            d.setDescription(c[3]);
            d.setImageUrl(c[4]);
            try { d.setLatitude(Double.parseDouble(c[5])); } catch (Exception ignored) {}
            try { d.setLongitude(Double.parseDouble(c[6])); } catch (Exception ignored) {}
            d.setBudgetRange("Moderate");
            d.setPopularity(70.0 + (idCounter % 30));
            seeds.add(d);
        }

        // Programmatically generate additional destinations until we have 50
        String[] sampleCountries = new String[]{"Spain","Italy","USA","Canada","Brazil","Australia","New Zealand","Mexico","Thailand","Vietnam","Portugal","Turkey","Germany","Norway","Iceland","South Africa","Morocco","Egypt","Greece","Netherlands","Sweden","Switzerland","Austria","Czech Republic","Hungary","Argentina","Chile","Peru","Colombia","Kenya","Uganda","Sri Lanka","Bhutan","Nepal","Malaysia","Philippines","Singapore","South Korea"};
        java.util.Random rnd = new java.util.Random(42);
        while (seeds.size() < 50) {
            var d = new DestinationEntity();
            d.setDestinationId(String.format("dest-%03d", idCounter++));
            String name = "Destination " + d.getDestinationId();
            String country = sampleCountries[rnd.nextInt(sampleCountries.length)];
            d.setName(name);
            d.setCountry(country);
            d.setRegion(country);
            d.setDescription("A wonderful place to explore: " + name + " in " + country + ".");
            d.setImageUrl("https://via.placeholder.com/800x600?text=" + java.net.URLEncoder.encode(name, java.nio.charset.StandardCharsets.UTF_8));
            d.setBudgetRange(new String[]{"Budget-Friendly","Moderate","Premium"}[rnd.nextInt(3)]);
            d.setPopularity(50 + rnd.nextDouble() * 50);
            d.setRecommendedDuration(2 + rnd.nextInt(12));
            seeds.add(d);
        }

        destinationRepository.saveAll(seeds);
        System.out.println("Seeded sample destinations: " + destinationRepository.count());
    }
}
