package Backend.Services;

import Backend.Entities.DJ;
import Backend.Entities.Performance;
import Backend.Repositories.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PerformanceService {

    @Autowired
    PerformanceRepository performanceRepository;

    public Performance Save(Performance performance){
        return performanceRepository.save(performance);
    }
    public Iterable<Performance> FindAll(){
        return performanceRepository.findAll();
    }
    public void DeleteById(Long id){
        performanceRepository.deleteById(id);
    }

    public Optional<Performance> FindById(Long id){
        return performanceRepository.findById(id);
    }

    public Iterable<Performance> FindByNameAndGenre(String name, String genre){
        return performanceRepository.findByNameEqualsIgnoreCaseAndGenreEqualsIgnoreCase(name, genre);
    }

    public Iterable<Performance> FindByName(String name){
        return performanceRepository.findPerformancesByNameEqualsIgnoreCase(name);
    }

    public Iterable<Performance> FindByGenre(String genre){
        return performanceRepository.findPerformancesByGenreEqualsIgnoreCase(genre);
    }
}