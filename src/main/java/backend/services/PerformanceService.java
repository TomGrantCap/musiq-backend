package backend.services;

import backend.config.Config;
import backend.entities.Dj;
import backend.entities.Performance;
import backend.errorHandler.PerformanceServiceException;
import backend.repositories.DjRepository;
import backend.repositories.PerformanceRepository;
import backend.dtos.DjDto;
import backend.dtos.DjMapper;
import backend.dtos.PerformanceDto;
import backend.dtos.PerformanceMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PerformanceService {
    Config config;
    final PerformanceRepository performanceRepository;
    final DjRepository djRepository;
    final PerformanceMapper performanceMapper;
    final DjMapper djMapper;

    public PerformanceService(PerformanceRepository performanceRepository, PerformanceMapper performanceMapper, DjMapper djMapper, DjRepository djRepository, Config config) {
        this.performanceRepository = performanceRepository;
        this.performanceMapper = performanceMapper;
        this.djMapper = djMapper;
        this.djRepository = djRepository;
        this.config = config;
    }

    //SAVE
    public PerformanceDto save(PerformanceDto performanceDTO){
        Performance performance = performanceMapper.mapFromDto(performanceDTO);
        performance.setDJ(new HashSet<>(djRepository.findAllById(performanceDTO.getDjIds())));
        return performanceMapper.mapToDto(performanceRepository.save(performance));
    }

    //DELETE BY ID
    public void deleteById(Long performanceId){

        if (findById(performanceId).isPresent()){
            Set<Long> djIdSet = performanceRepository.findById(performanceId).map(performanceMapper::mapToDto).get().getDjIds();

            if (!djIdSet.isEmpty()){
                removeDJPerformances(performanceId, djIdSet);
            }

            config.webClient().delete()
                    .uri("/reviews/deletebyperformanceid/" + performanceId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            performanceRepository.deleteById(performanceId);
        }
        else throw new PerformanceServiceException("No performance found with ID " + performanceId);
    }

    void removeDJPerformances(Long performanceId, Set<Long> djIdSet){
        Performance performance = performanceRepository.findById(performanceId).get();
        for (Long djId : djIdSet){
            Dj dj = djRepository.findById(djId).get();
            Set<Performance> performances = dj.getPerformance();
            performances.remove(performance);
            dj.setPerformance(performances);
            djRepository.save(dj);
            System.out.println("Performance " + performanceId + " deleted for DJ " + djId);
        }
    }

    //FIND ALL
    public Iterable<PerformanceDto> findAll(){
        return performanceRepository.findAll()
                .stream()
                .map(performanceMapper::mapToDto)
                .toList();
    }

    //FIND BY ID
    public Optional<PerformanceDto> findById(Long id){
        return performanceRepository.findById(id)
                .map(performanceMapper::mapToDto);
    }

    //FIND PERFORMANCE'S DJS
    public Iterable<DjDto> findDJs(Long performanceId){
        return djRepository.findAllById(this.findById(performanceId).get().getDjIds())
                .stream()
                .map(djMapper::mapToDto)
                .toList();
    }

    //FIND BY NAME AND/OR GENRE
    public Iterable<PerformanceDto> findByNameAndGenre(String name, String genre){
        return performanceRepository.findByNameContainsIgnoreCaseAndGenreContainsIgnoreCase(name, genre)
                .stream()
                .map(performanceMapper::mapToDto)
                .toList();
    }

    //FIND BY NAME MULTIPLE
    public Iterable<PerformanceDto> findByName(String name){
        return performanceRepository.findPerformancesByNameContainsIgnoreCase(name)
                .stream()
                .map(performanceMapper::mapToDto)
                .toList();
    }

    //FIND BY NAME SINGLE
    public PerformanceDto findSinglePerformanceByName(String name){
        return performanceMapper.mapToDto(performanceRepository.findPerformanceByNameIgnoreCase(name));
    }

    //FIND BY GENRE
    public Iterable<PerformanceDto> findByGenre(String genre){
        return performanceRepository.findPerformancesByGenreContainsIgnoreCase(genre)
                .stream()
                .map(performanceMapper::mapToDto)
                .toList();
    }}

