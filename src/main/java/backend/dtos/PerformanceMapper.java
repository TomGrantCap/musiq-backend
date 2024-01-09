package backend.dtos;

import backend.entities.DJ;
import backend.entities.Performance;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PerformanceMapper {
    public PerformanceDTO mapToDto(Performance performance) {
        PerformanceDTO performanceDto = new PerformanceDTO(performance.getId(), performance.getName(), performance.getGenre());

        performanceDto.setDj(performance.getDJ()
                .stream()
                .map(DJ::getId)
                .collect(Collectors.toSet())); //Sets performanceDTO's DJs, being a set of IDs (longs).

        return performanceDto;
    }

    public Performance mapFromDto(PerformanceDTO performanceDTO) {
        Performance performance = new Performance(performanceDTO.getId(), performanceDTO.getName(), performanceDTO.getGenre());

        return performance;
    }
}