package backend.services;

import backend.entities.Dj;
import backend.entities.Performance;
import backend.repositories.DjRepository;
import backend.repositories.PerformanceRepository;
import backend.dtos.DjDto;
import backend.dtos.DjMapper;
import backend.dtos.PerformanceDto;
import backend.dtos.PerformanceMapper;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class DjService {

    final DjRepository djRepository;
    final DjMapper djMapper;
    final PerformanceMapper performanceMapper;
    final PerformanceRepository performanceRepository;

    //CONSTRUCTOR
    public DjService(DjRepository djRepository, DjMapper djMapper, PerformanceMapper performanceMapper, PerformanceRepository performanceRepository) {
        this.djRepository = djRepository;
        this.djMapper = djMapper;
        this.performanceMapper = performanceMapper;
        this.performanceRepository = performanceRepository;
    }

    //SAVE
    public DjDto save(DjDto djDto) {
        Dj dj = djMapper.mapFromDto(djDto);
        dj.setPerformance(new HashSet<>(performanceRepository.findAllById(djDto.getPerformanceIds())));
        return djMapper.mapToDto(djRepository.save(dj));
    }

    //DELETE BY ID
    public void deleteById(Long djId) {

        Set<Long> performanceIdSet = djRepository.findById(djId).map(djMapper::mapToDto).get().getPerformanceIds();
        if (performanceIdSet.isEmpty()) {
            djRepository.deleteById(djId);
        }
        else {
            deleteDJsFromPerformances(djId, performanceIdSet);
            }

            djRepository.deleteById(djId);
    }

    void deleteDJsFromPerformances(Long djId, Set<Long>performanceIdSet){
        Dj dj = djRepository.findById(djId).get();
        for (Long performanceId : performanceIdSet) {
            Performance performance = performanceRepository.findById(performanceId).get();
            Set<Dj> djs = performance.getDJ();
            djs.remove(dj);
            performance.setDJ(djs);
            performanceRepository.save(performance);
        }
    }

    //FIND ALL
    public Iterable<DjDto> findAll() {
        return djRepository.findAll()
                .stream()
                .map(djMapper::mapToDto)
                .toList();
    }

    //FIND DJ'S PERFORMANCES
    public Iterable<PerformanceDto> findPerformances(Long djId){

        return performanceRepository.findAllById(this.findById(djId).get().getPerformanceIds())
                .stream()
                .map(performanceMapper::mapToDto)
                .toList();
    }

    //FIND BY ID
    public Optional<DjDto> findById(Long id) {
        return djRepository.findById(id).map(djMapper::mapToDto);
    }

    //FIND BY NAME AND/OR GENRE
    public Iterable<DjDto> findByNameAndGenre(String name, String genre) {
        return djRepository.findByNameContainsIgnoreCaseAndGenreContainsIgnoreCase(name, genre)
                .stream()
                .map(djMapper::mapToDto)
                .toList();
    }

    //FIND BY NAME
    public Iterable<DjDto> findByName(String name) {
        return djRepository.findByNameContainsIgnoreCase(name)
                .stream()
                .map(djMapper::mapToDto)
                .toList();
    }

    //FIND BY GENRE
    public Iterable<DjDto> findByGenre(String genre) {
        return djRepository.findByGenreContainsIgnoreCase(genre)
                .stream()
                .map(djMapper::mapToDto)
                .toList();
    }
}


