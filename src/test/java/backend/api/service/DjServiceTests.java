package backend.api.service;

import backend.dtos.DjDto;
import backend.dtos.DjMapper;
import backend.entities.Dj;
import backend.entities.Performance;
import backend.repositories.DjRepository;
import backend.repositories.PerformanceRepository;
import backend.services.DjService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DjServiceTests {

    @Mock
    DjMapper djMapper;

    @Mock
    DjRepository djRepository;

    @Mock
    PerformanceRepository performanceRepository;

    @InjectMocks
    private DjService djService;

    @Test
    void DjService_FindDjDto_ReturnDjDto(){

        Dj dj = new Dj(1L, "TestDjDtoName", "TestDjDtoGenre");

        DjDto djDto = new DjDto(1L, "TestDjDtoName", "TestDjDtoGenre");
        djDto.setPerformanceIds(Set.of(1L, 2L, 3L));

        when(djRepository.findById(djDto.getId())).thenReturn(Optional.of(dj));

        when(djMapper.mapToDto(dj)).thenReturn(djDto);

        DjDto savedDjDto = djService.findById(djDto.getId()).get();

        Assertions.assertThat(savedDjDto).isEqualTo(djDto);
    }

    @Test
    void DjService_SaveDjDto_ReturnDjDto(){

        Dj dj = new Dj(1L, "TestDjDtoName", "TestDjDtoGenre");
        DjDto djDto = new DjDto(1L, "TestDjDtoName", "TestDjDtoGenre");

        Performance performance = new Performance(1L, "TestPerformance", "TestPerformanceGenre");
        dj.setPerformance(Set.of(performance));
        djDto.setPerformanceIds(Set.of(1L));

        when(djMapper.mapFromDto(djDto)).thenReturn(dj);
        when(djMapper.mapToDto(dj)).thenReturn(djDto);
        when(djRepository.save(dj)).thenReturn(dj);

        DjDto savedDto = djService.save(djDto);

        Assertions.assertThat(savedDto).isNotNull();
    }

    @Test
    void DjService_DeleteDjDto_ReturnVoid(){

        Dj dj = new Dj(1L, "TestDjDtoName", "TestDjDtoGenre");

        DjDto djDto = new DjDto(1L, "TestDjDtoName", "TestDjDtoGenre");
        djDto.setPerformanceIds(Set.of(1L, 2L, 3L));

        when(djRepository.findById(djDto.getId())).thenReturn(Optional.of(dj));

        when(djMapper.mapToDto(dj)).thenReturn(djDto);

        DjDto savedDjDto = djService.findById(djDto.getId()).get();

        Assertions.assertThat(savedDjDto).isEqualTo(djDto);
    }
}