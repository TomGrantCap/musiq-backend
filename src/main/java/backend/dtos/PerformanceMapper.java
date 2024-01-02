package backend.dtos;

import backend.Entities.DJ;
import backend.Entities.Performance;
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
}