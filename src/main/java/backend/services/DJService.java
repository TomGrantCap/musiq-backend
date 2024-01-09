package backend.services;

import backend.entities.DJ;
import backend.entities.Performance;
import backend.repositories.DJRepository;
import backend.repositories.PerformanceRepository;
import backend.dtos.DjDTO;
import backend.dtos.DjMapper;
import backend.dtos.PerformanceDTO;
import backend.dtos.PerformanceMapper;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DJService {

    final DJRepository djRepository;
    final DjMapper djMapper;
    final PerformanceMapper performanceMapper;
    final PerformanceRepository performanceRepository;

    //CONSTRUCTOR
    public DJService(DJRepository djRepository, DjMapper djMapper, PerformanceMapper performanceMapper, PerformanceRepository performanceRepository) {
        this.djRepository = djRepository;
        this.djMapper = djMapper;
        this.performanceMapper = performanceMapper;
        this.performanceRepository = performanceRepository;
    }

    //SAVE
    public DjDTO save(DjDTO djDto) {
        DJ dj = djMapper.mapFromDto(djDto);
        dj.setPerformance(new HashSet<>(performanceRepository.findAllById(djDto.getPerformanceIDs())));
        return djMapper.mapToDto(djRepository.save(dj));
    }

    //DELETE BY ID
    public void deleteById(Long djId) {
        Set<Long> performanceIdSet = djRepository.findById(djId).map(djMapper::mapToDto).get().getPerformanceIDs();
        if (performanceIdSet.isEmpty()) {
            djRepository.deleteById(djId);
            System.out.println("DJ lacking performances deleted");
        } else {
            //For each performance in DJ's set, removes the DJ from said set prior to deletion.
            DJ dj = djRepository.findById(djId).get();
            for (Long performanceId : performanceIdSet) {
                Performance performance = performanceRepository.findById(performanceId).get();
                Set<DJ> djs = performance.getDJ();
                djs.remove(dj);
                performance.setDJ(djs);
                performanceRepository.save(performance);
                System.out.println("DJ " + djId + " deleted for performance " + performanceId);
            }
            djRepository.deleteById(djId);
        }
    }

    //FIND ALL
    public Iterable<DjDTO> findAll() {
        return djRepository.findAll()
                .stream()
                .map(djMapper::mapToDto)
                .collect(Collectors.toList());
    }

    //FIND DJ'S PERFORMANCES
    public Iterable<PerformanceDTO> findPerformances(Long djId){
        return performanceRepository.findAllById(this.findById(djId).get().getPerformanceIDs())
                .stream()
                .map(performanceMapper::mapToDto)
                .collect(Collectors.toList());
    }

    //FIND BY ID
    public Optional<DjDTO> findById(Long id) {
        return djRepository.findById(id).map(djMapper::mapToDto);
    }

    //FIND BY NAME AND/OR GENRE
    public Iterable<DjDTO> findByNameAndGenre(String name, String genre) {
        return djRepository.findByNameContainsIgnoreCaseAndGenreContainsIgnoreCase(name, genre)
                .stream()
                .map(djMapper::mapToDto)
                .collect(Collectors.toList());
    }

    //FIND BY NAME
    public Iterable<DjDTO> findByName(String name) {
        return djRepository.findByNameContainsIgnoreCase(name)
                .stream()
                .map(djMapper::mapToDto)
                .collect(Collectors.toList());
    }

    //FIND BY GENRE
    public Iterable<DjDTO> findByGenre(String genre) {
        return djRepository.findByGenreContainsIgnoreCase(genre)
                .stream()
                .map(djMapper::mapToDto)
                .collect(Collectors.toList());
    }
}


