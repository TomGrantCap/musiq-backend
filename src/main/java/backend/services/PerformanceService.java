package backend.services;

import backend.entities.DJ;
import backend.entities.Performance;
import backend.errorHandler.PerformanceException;
import backend.repositories.DJRepository;
import backend.repositories.PerformanceRepository;
import backend.dtos.DjDTO;
import backend.dtos.DjMapper;
import backend.dtos.PerformanceDTO;
import backend.dtos.PerformanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PerformanceService {

    @Autowired
    WebClient webClient;
    final PerformanceRepository performanceRepository;
    final DJRepository djRepository;
    final PerformanceMapper performanceMapper;
    final DjMapper djMapper;

    public PerformanceService(PerformanceRepository performanceRepository, PerformanceMapper performanceMapper, DjMapper djMapper, DJRepository djRepository) {
        this.performanceRepository = performanceRepository;
        this.performanceMapper = performanceMapper;
        this.djMapper = djMapper;
        this.djRepository = djRepository;
    }

    //SAVE
    public PerformanceDTO save(PerformanceDTO performanceDTO){
        Performance performance = performanceMapper.mapFromDto(performanceDTO);
        performance.setDJ(new HashSet<>(djRepository.findAllById(performanceDTO.getDjIDs())));
        return performanceMapper.mapToDto(performanceRepository.save(performance));
    }

    //DELETE BY ID
    public void deleteById(Long performanceId){

        if (findById(performanceId).isPresent()){
            Set<Long> djIdSet = performanceRepository.findById(performanceId).map(performanceMapper::mapToDto).get().getDjIDs();

            if (!djIdSet.isEmpty()){
                RemoveDJPerformances(performanceId, djIdSet);
            }

            webClient.delete()
                    .uri("/reviews/deletebyperformanceid/" + performanceId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            performanceRepository.deleteById(performanceId);
        }


        else throw new PerformanceException("No performance found with ID " + performanceId);


    }

    void RemoveDJPerformances(Long performanceId, Set<Long> djIdSet){
        Performance performance = performanceRepository.findById(performanceId).get();
        for (Long djId : djIdSet){
            DJ dj = djRepository.findById(djId).get();
            Set<Performance> performances = dj.getPerformance();
            performances.remove(performance);
            dj.setPerformance(performances);
            djRepository.save(dj);
            System.out.println("Performance " + performanceId + " deleted for DJ " + djId);
        }
    }

    //FIND ALL
    public Iterable<PerformanceDTO> findAll(){
        return performanceRepository.findAll()
                .stream()
                .map(performanceMapper::mapToDto)
                .collect(Collectors.toList());
    }

    //FIND BY ID
    public Optional<PerformanceDTO> findById(Long id){
        return performanceRepository.findById(id)
                .map(performanceMapper::mapToDto);
    }

    //FIND PERFORMANCE'S DJS
    public Iterable<DjDTO> findDJs(Long performanceId){
        return djRepository.findAllById(this.findById(performanceId).get().getDjIDs())
                .stream()
                .map(djMapper::mapToDto)
                .collect(Collectors.toList());
    }

    //FIND BY NAME AND/OR GENRE
    public Iterable<PerformanceDTO> findByNameAndGenre(String name, String genre){
        return performanceRepository.findByNameContainsIgnoreCaseAndGenreContainsIgnoreCase(name, genre)
                .stream()
                .map(performanceMapper::mapToDto)
                .collect(Collectors.toList());
    }

    //FIND BY NAME MULTIPLE
    public Iterable<PerformanceDTO> findByName(String name){
        return performanceRepository.findPerformancesByNameContainsIgnoreCase(name)
                .stream()
                .map(performanceMapper::mapToDto)
                .collect(Collectors.toList());
    }

    //FIND BY NAME SINGLE
    public PerformanceDTO findSinglePerformanceByName(String name){
        return performanceMapper.mapToDto(performanceRepository.findPerformanceByNameIgnoreCase(name));
    }

    //FIND BY GENRE
    public Iterable<PerformanceDTO> findByGenre(String genre){
        return performanceRepository.findPerformancesByGenreContainsIgnoreCase(genre)
                .stream()
                .map(performanceMapper::mapToDto)
                .collect(Collectors.toList());
    }
}