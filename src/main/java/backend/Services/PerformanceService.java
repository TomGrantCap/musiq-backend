package backend.Services;

import backend.Entities.DJ;
import backend.Entities.Performance;
import backend.Repositories.DJRepository;
import backend.Repositories.PerformanceRepository;
import backend.dtos.PerformanceDTO;
import backend.dtos.PerformanceMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PerformanceService {

    final PerformanceRepository performanceRepository;

    final DJRepository djRepository;
    final PerformanceMapper performanceMapper;
    public PerformanceService(PerformanceRepository performanceRepository, PerformanceMapper performanceMapper, DJRepository djRepository) {
        this.performanceRepository = performanceRepository;
        this.performanceMapper = performanceMapper;
        this.djRepository = djRepository;
    }

    //SAVE
    public PerformanceDTO save(Performance performance){
        return performanceMapper.mapToDto(performanceRepository.save(performance));
    }

    //FIND ALL
    public Iterable<PerformanceDTO> findAll(){
        return performanceRepository.findAll()
                .stream()
                .map(performanceMapper::mapToDto)
                .collect(Collectors.toList());
    }

    //DELETE BY ID
    public void deleteById(Long performanceId){
        Set<Long> djIdSet = performanceRepository.findById(performanceId).map(performanceMapper::mapToDto).get().getDjIDs();
        if (djIdSet.isEmpty()){
            performanceRepository.deleteById(performanceId);
            System.out.println("Performance lacking DJs deleted");
        }
        else{
            //For each DJ playing in performance, removes performance from them prior to deletion.
            Performance performance = performanceRepository.findById(performanceId).get();
            for (Long djId : djIdSet){
                DJ dj = djRepository.findById(djId).get();
                Set<Performance> performances = dj.getPerformance();
                performances.remove(performance);
                dj.setPerformance(performances);
                djRepository.save(dj);
                System.out.println("Performance " + performanceId + " deleted for DJ " + djId);
            }
            performanceRepository.deleteById(performanceId);
        }
    }

    //FIND BY ID
    public Optional<PerformanceDTO> findById(Long id){
        return performanceRepository.findById(id)
                .map(performanceMapper::mapToDto);
    }

    //FIND BY NAME AND/OR GENRE
    public Iterable<PerformanceDTO> findByNameAndGenre(String name, String genre){
        return performanceRepository.findByNameEqualsIgnoreCaseAndGenreEqualsIgnoreCase(name, genre)
                .stream()
                .map(performanceMapper::mapToDto)
                .collect(Collectors.toList());
    }

    //FIND BY NAME
    public Iterable<PerformanceDTO> findByName(String name){
        return performanceRepository.findPerformancesByNameEqualsIgnoreCase(name)
                .stream()
                .map(performanceMapper::mapToDto)
                .collect(Collectors.toList());
    }

    //FIND BY GENRE
    public Iterable<PerformanceDTO> findByGenre(String genre){
        return performanceRepository.findPerformancesByGenreEqualsIgnoreCase(genre)
                .stream()
                .map(performanceMapper::mapToDto)
                .collect(Collectors.toList());
    }
}