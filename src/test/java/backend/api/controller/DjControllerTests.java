package backend.api.controller;

import backend.controllers.DjController;
import backend.dtos.DjDto;
import backend.dtos.DjMapper;
import backend.entities.Dj;
import backend.entities.Performance;
import backend.repositories.DjRepository;
import backend.services.DjService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DjControllerTests {

    @Mock
    private DjService djService;

    @InjectMocks
    DjController djController;

    @Test
    void DjController_SaveDjDto_ReturnDjDto(){

        DjDto djDto = new DjDto(1L, "TestDjDtoName", "TestDjDtoGenre");

        djDto.setPerformanceIds(Set.of(1L));

        when(djService.save(djDto)).thenReturn(djDto);

        DjDto savedDto = djController.create(djDto);

        Assertions.assertThat(savedDto).isNotNull().isEqualTo(djDto);
    }
}
