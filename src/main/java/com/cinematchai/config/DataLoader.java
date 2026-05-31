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
        if (destinationRepository.count() > 0) return;

        List<DestinationEntity> seeds = List.of(
                buildDestination("dest-001", "Araku Valley", "Alluri Sitharama Raju", "Araku Valley, Andhra Pradesh", "Hill Station",
                        "A lush hill station framed by coffee estates, waterfalls, and misty valleys.",
                        "https://images.unsplash.com/photo-1500534314209-a25ddb2bd429?auto=format&fit=crop&w=1200&q=80",
                        18.3274, 82.8806, 3, "Medium", "Winter", 3500, 85000, 4.8, 95,
                        "Coffee plantation views, tribal craft markets, and scenic train rides."),
                buildDestination("dest-002", "Borra Caves", "Alluri Sitharama Raju", "Borra Caves Road, Andhra Pradesh", "Nature Tourism",
                        "One of Asia's deepest caves with glittering stalactites and ancient limestone formations.",
                        "https://images.unsplash.com/photo-1511739001486-6bfe10ce785f?auto=format&fit=crop&w=1200&q=80",
                        18.3520, 82.7871, 1, "Medium", "Winter", 2800, 76000, 4.7, 88,
                        "Guided cave tours, underground rivers, and dramatic lighting effects."),
                buildDestination("dest-003", "Lambasingi", "Alluri Sitharama Raju", "Lambasingi, Andhra Pradesh", "Adventure Tourism",
                        "A cold-climate village known as the \"Kashmir of Andhra Pradesh\" and misty pine forests.",
                        "https://images.unsplash.com/photo-1519817650390-64a93db5118a?auto=format&fit=crop&w=1200&q=80",
                        18.3500, 82.5766, 2, "Budget", "Winter", 2200, 53000, 4.5, 78,
                        "Foggy mornings, trekking, and camping under silver oak woodlands."),
                buildDestination("dest-004", "RK Beach", "Visakhapatnam", "Ramakrishna Beach Road, Visakhapatnam", "Beach Tourism",
                        "A wide urban beach with sunrise views, a marine aquarium, and active promenades.",
                        "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=1200&q=80",
                        17.7239, 83.3270, 1, "Budget", "Summer", 2400, 98000, 4.4, 92,
                        "Seaside cafes, thousand-year-old lighthouse, and evening strolls by the Bay of Bengal."),
                buildDestination("dest-005", "Simhachalam Temple", "Visakhapatnam", "Simhachalam, Visakhapatnam", "Temple Tourism",
                        "Ancient hill shrine dedicated to Lord Narasimha with dramatic Dravidian architecture.",
                        "https://images.unsplash.com/photo-1529960536864-0d4ea1d7fbdd?auto=format&fit=crop&w=1200&q=80",
                        17.8044, 83.3416, 1, "Medium", "All Seasons", 2600, 72000, 4.6, 81,
                        "Sacred rituals, stone carvings, and a serene hilltop atmosphere."),
                buildDestination("dest-006", "Tirumala Temple", "Tirupati", "Tirumala, Tirupati", "Temple Tourism",
                        "World-famous Vaishnavite shrine attracting millions of pilgrims each year.",
                        "https://images.unsplash.com/photo-1513635269975-59663e0ac1ad?auto=format&fit=crop&w=1200&q=80",
                        13.6831, 79.3470, 1, "Medium", "All Seasons", 3000, 120000, 4.9, 100,
                        "Spiritual processions, sacred hills, and legendary temple cuisine."),
                buildDestination("dest-007", "Srikalahasti Temple", "Tirupati", "Srikalahasti, Andhra Pradesh", "Temple Tourism",
                        "Historic temple famed for Vayu stalam and sculpted gopurams by the Swarnamukhi river.",
                        "https://images.unsplash.com/photo-1512453979798-5ea266f8880c?auto=format&fit=crop&w=1200&q=80",
                        13.7500, 79.7000, 1, "Medium", "All Seasons", 2800, 64000, 4.7, 79,
                        "Stone mythology, evening aarti, and spiritual heritage."),
                buildDestination("dest-008", "Horsley Hills", "Tirupati", "Horsley Hills, Andhra Pradesh", "Hill Station",
                        "Rolling hills and dense forests offering a cool retreat and easy hikes.",
                        "https://images.unsplash.com/photo-1519817650390-64a93db5118a?auto=format&fit=crop&w=1200&q=80",
                        13.2690, 78.5084, 2, "Medium", "Winter", 3200, 47000, 4.5, 76,
                        "Panoramic viewpoints, apple orchards, and winding hill roads."),
                buildDestination("dest-009", "Belum Caves", "Nandyal", "Belum Caves, Andhra Pradesh", "Nature Tourism",
                        "Vast limestone caves with dramatic stalactites, underground lakes, and rare formations.",
                        "https://images.unsplash.com/photo-1500534314209-a25ddb2bd429?auto=format&fit=crop&w=1200&q=80",
                        15.8285, 78.4410, 1, "Budget", "Winter", 2100, 38000, 4.6, 72,
                        "Cavern tours, silent chambers, and a hidden natural wonder."),
                buildDestination("dest-010", "Gandikota", "Kadapa", "Gandikota, Andhra Pradesh", "Historical Sites",
                        "A dramatic gorge along the Penna river often called the 'Grand Canyon of India'.",
                        "https://images.unsplash.com/photo-1545060894-4f79a9d559db?auto=format&fit=crop&w=1200&q=80",
                        14.6907, 78.3203, 1, "Budget", "Winter", 2000, 55000, 4.5, 84,
                        "Ruin exploration, fort walls, and sunset views over the gorge."),
                buildDestination("dest-011", "Lepakshi Temple", "Anantapur", "Lepakshi, Andhra Pradesh", "Temple Tourism",
                        "A historic Veerabhadra temple with carved pillars, monolithic Nandi, and frescoes.",
                        "https://images.unsplash.com/photo-1521434469754-780fffd37d05?auto=format&fit=crop&w=1200&q=80",
                        13.7500, 77.7138, 1, "Budget", "Winter", 1800, 67000, 4.8, 70,
                        "Intricate stone carving, dancing pillars, and cultural legend."),
                buildDestination("dest-012", "Ahobilam", "Nandyal", "Ahobilam, Andhra Pradesh", "Temple Tourism",
                        "A mystical temple complex nestled in the Nallamala Hills with ancient Narasimha shrines.",
                        "https://images.unsplash.com/photo-1493559879380-2d5f3f44f9fb?auto=format&fit=crop&w=1200&q=80",
                        15.2500, 78.9000, 2, "Medium", "All Seasons", 2400, 45000, 4.7, 68,
                        "Hill temples, river views, and serene pilgrimage trails."),
                buildDestination("dest-013", "Amaravati", "Guntur", "Amaravati, Andhra Pradesh", "Historical Sites",
                        "The ancient Buddhist capital known for its stupa ruins and museum collections.",
                        "https://images.unsplash.com/photo-1504384308090-c894fdcc538d?auto=format&fit=crop&w=1200&q=80",
                        16.5417, 80.5150, 1, "Medium", "All Seasons", 2600, 72000, 4.4, 73,
                        "Buddhist heritage, archaeological sites, and riverside gardens."),
                buildDestination("dest-014", "Kondapalli Fort", "Krishna", "Kondapalli, Andhra Pradesh", "Historical Sites",
                        "A hill fortress with sweeping views, ancient gateways, and toy-making craftsmanship.",
                        "https://images.unsplash.com/photo-1526772662000-3f88f10405ff?auto=format&fit=crop&w=1200&q=80",
                        16.5394, 80.8727, 1, "Budget", "Winter", 2100, 33000, 4.3, 62,
                        "Fort walls, traditional toys, and local village markets."),
                buildDestination("dest-015", "Bhavani Island", "Kakinada", "Bhavani Island, Andhra Pradesh", "Nature Tourism",
                        "A tranquil island in the Godavari River offering river safaris and green landscapes.",
                        "https://images.unsplash.com/photo-1500534314209-a25ddb2bd429?auto=format&fit=crop&w=1200&q=80",
                        16.9961, 82.2560, 1, "Medium", "Winter", 2500, 62000, 4.6, 66,
                        "Boat rides, mangrove views, and weekend picnics."),
                buildDestination("dest-016", "Mypadu Beach", "Nellore", "Mypadu Beach, Andhra Pradesh", "Beach Tourism",
                        "A white sand beach with calm waters popular for weekend escapes and seafood.",
                        "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=1200&q=80",
                        14.3375, 80.0017, 2, "Budget", "Summer", 2300, 47000, 4.4, 64,
                        "Water sports, beachside restaurants, and long sunset walks."),
                buildDestination("dest-017", "Pulicat Lake", "Nellore", "Pulicat Lake, Andhra Pradesh", "Nature Tourism",
                        "A scenic coastal lagoon famous for migratory birds, fishing villages, and backwater cruises.",
                        "https://images.unsplash.com/photo-1500534314209-a25ddb2bd429?auto=format&fit=crop&w=1200&q=80",
                        13.5850, 80.0500, 1, "Medium", "Winter", 2600, 54000, 4.5, 61,
                        "Bird watching, lagoon cruises, and salt marsh landscapes."),
                buildDestination("dest-018", "Coringa Wildlife Sanctuary", "Kakinada", "Coringa Wildlife Sanctuary, Andhra Pradesh", "Nature Tourism",
                        "A mangrove sanctuary home to rare birds, estuarine ecosystems, and conservation trails.",
                        "https://images.unsplash.com/photo-1493559879380-2d5f3f44f9fb?auto=format&fit=crop&w=1200&q=80",
                        16.9037, 82.1870, 1, "Medium", "Winter", 2700, 39000, 4.5, 60,
                        "Mangrove boardwalks, bird hides, and river delta ecology."),
                buildDestination("dest-019", "Kakinada Beach", "Kakinada", "Kakinada Beach Road, Andhra Pradesh", "Beach Tourism",
                        "A popular coastal stretch with iconic colonial pier views and sunrise ambience.",
                        "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=1200&q=80",
                        16.9580, 82.2383, 1, "Budget", "Summer", 2200, 59000, 4.4, 67,
                        "Beach walks, local seafood stalls, and sunset photography."),
                buildDestination("dest-020", "Rollapadu Wildlife Sanctuary", "Nandyal", "Rollapadu, Andhra Pradesh", "Nature Tourism",
                        "A dry deciduous reserve known for blackbuck, migratory birds, and open plains.",
                        "https://images.unsplash.com/photo-1493559879380-2d5f3f44f9fb?auto=format&fit=crop&w=1200&q=80",
                        15.7139, 78.1203, 2, "Budget", "Winter", 2100, 30000, 4.3, 58,
                        "Wildlife safaris, birding trails, and rural landscape photography.")
        );

        destinationRepository.saveAll(seeds);
        System.out.println("Seeded Andhra Pradesh destinations: " + destinationRepository.count());
    }

    private DestinationEntity buildDestination(String destinationId, String name, String district, String address, String category,
                                               String description, String imageUrl, double latitude, double longitude,
                                               int tripDuration, String budgetLevel, String bestSeason, int avgCostPerDay,
                                               int monthlyVisitors, double rating, double popularity, String highlights) {
        DestinationEntity entity = new DestinationEntity();
        entity.setDestinationId(destinationId);
        entity.setName(name);
        entity.setRegion("Andhra Pradesh");
        entity.setCountry("India");
        entity.setDistrict(district);
        entity.setAddress(address);
        entity.setCategory(category);
        entity.setDescription(description);
        entity.setImageUrl(imageUrl);
        entity.setBackdropUrl(imageUrl);
        entity.setLatitude(latitude);
        entity.setLongitude(longitude);
        entity.setTripDuration(tripDuration);
        entity.setBudgetLevel(budgetLevel);
        entity.setBestSeason(bestSeason);
        entity.setAvgCostPerDay(avgCostPerDay);
        entity.setMonthlyVisitors(monthlyVisitors);
        entity.setRating(rating);
        entity.setPopularity(popularity);
        entity.setHighlights(highlights);
        entity.setTravelStyle(category);
        entity.setBestTimeToVisit(bestSeason);
        entity.setBudgetRange(budgetLevel);
        entity.setRecommendedDuration(tripDuration);
        entity.setTags(String.join(", ", category, district, "Andhra Pradesh"));
        return entity;
    }
}
