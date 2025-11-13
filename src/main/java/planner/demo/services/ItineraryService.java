package planner.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import planner.demo.models.Itinerary;
import planner.demo.repositories.ItineraryRepository;

import java.util.List;

@Service
public class ItineraryService {

    @Autowired
    ItineraryRepository itineraryRepo;

    public Itinerary createItinerary(Itinerary itinerary) {
        itineraryRepo.save(itinerary);
        return itinerary;
    }

    public List<Itinerary> getAllItineraries() {
        return itineraryRepo.findAll();
    }

    public Itinerary getItineraryById(Long id) {
        return itineraryRepo.findById(id).orElse(null);
    }

    public Itinerary updateItinerary(Long id, Itinerary updatedItinerary) {
        return itineraryRepo.findById(id).map(itinerary -> {
            itinerary.setActivities(updatedItinerary.getActivities());
            itinerary.setStartTime(updatedItinerary.getStartTime());
            itinerary.setEndTime(updatedItinerary.getEndTime());
            itinerary.setLocation(updatedItinerary.getLocation());
            itinerary.setTotalCost(updatedItinerary.getTotalCost());
            return itineraryRepo.save(itinerary);
        }).orElse(null);
    }

    public void deleteItinerary(Long id) {
        itineraryRepo.deleteById(id);
    }

}
