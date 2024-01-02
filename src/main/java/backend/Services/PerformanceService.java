package backend.Services;

import backend.Entities.Performance;
import backend.Repositories.DJRepository;
import backend.Repositories.PerformanceRepository;
import backend.dtos.DjDTO;
import backend.dtos.DjMapper;
import backend.dtos.PerformanceDTO;
import backend.dtos.PerformanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PerformanceService {

    final PerformanceRepository performanceRepository;

    final PerformanceMapper performanceMapper;
    public PerformanceService(PerformanceRepository performanceRepository, PerformanceMapper performanceMapper) {
        this.performanceRepository = performanceRepository;
        this.performanceMapper = performanceMapper;
    }

    public PerformanceDTO save(Performance performance){
        return performanceMapper.mapToDto(performanceRepository.save(performance));
    }
    public Iterable<PerformanceDTO> findAll(){
        return performanceRepository.findAll().stream().map(performanceMapper::mapToDto).collect(Collectors.toList());
    }
    public void deleteById(Long id){
        performanceRepository.deleteById(id);
    }

    public Optional<PerformanceDTO> findById(Long id){
        return performanceRepository.findById(id)
                .map(performanceMapper::mapToDto);
    }

    public Iterable<PerformanceDTO> findByNameAndGenre(String name, String genre){
        return performanceRepository.findByNameEqualsIgnoreCaseAndGenreEqualsIgnoreCase(name, genre)
                .stream()
                .map(performanceMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public Iterable<PerformanceDTO> findByName(String name){
        return performanceRepository.findPerformancesByNameEqualsIgnoreCase(name)
                .stream()
                .map(performanceMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public Iterable<PerformanceDTO> findByGenre(String genre){
        return performanceRepository.findPerformancesByGenreEqualsIgnoreCase(genre)
                .stream()
                .map(performanceMapper::mapToDto)
                .collect(Collectors.toList());
    }
}