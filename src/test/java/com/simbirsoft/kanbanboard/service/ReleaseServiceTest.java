package com.simbirsoft.kanbanboard.service;

import com.simbirsoft.kanbanboard.model.Task;
import com.simbirsoft.kanbanboard.model.Release;
import com.simbirsoft.kanbanboard.repository.ReleaseRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class ReleaseServiceTest {

  @Mock
  private ReleaseRepository releaseRepository;

  @InjectMocks
  private ReleaseService releaseService;

  private Release release1;
  private Release release2;
  private Task task;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    releaseService = new ReleaseService(releaseRepository);

    task = new Task();
    task.setId(1L);

    release1 = new Release();
    release1.setId(1L);
    release1.setVersion("1.0");
    release1.setStartDate(LocalDateTime.now());
    release1.setEndDate(LocalDateTime.now().plusDays(7));
    release1.setTask(task);

    release2 = new Release();
    release2.setId(2L);
    release2.setVersion("2.0");
    release2.setStartDate(LocalDateTime.now().plusDays(7));
    release2.setEndDate(LocalDateTime.now().plusDays(14));
    release2.setTask(task);
  }

  @Test
  public void testGetReleasesByTask() {
    List<Release> releases = new ArrayList<>();
    releases.add(release1);
    releases.add(release2);

    when(releaseRepository.findByTask(task)).thenReturn(releases);

    List<Release> result = releaseService.getReleasesByTask(task);

    verify(releaseRepository, times(1)).findByTask(task);
    assertEquals(releases.size(), result.size());
    assertTrue(result.contains(release1));
    assertTrue(result.contains(release2));
  }

  @Test
  public void testGetReleaseById() {
    when(releaseRepository.findById(release1.getId())).thenReturn(Optional.of(release1));

    Optional<Release> result = releaseService.getReleaseById(release1.getId());

    verify(releaseRepository, times(1)).findById(release1.getId());
    assertTrue(result.isPresent());
    assertEquals(release1, result.get());
  }

  @Test
  public void testUpdateRelease() {
    release1.setVersion("2.0");

    assertDoesNotThrow(() -> releaseService.updateRelease(release1));

    verify(releaseRepository, times(1)).save(release1);
    assertEquals("2.0", release1.getVersion());
  }

  @Test
  public void updateReleaseWithValidRelease() {
    Release release = new Release();
    release.setVersion("1.0");
    release.setStartDate(LocalDateTime.of(2023, 3, 1, 0, 0));
    release.setEndDate(LocalDateTime.of(2023, 3, 31, 0, 0));
    release.setId(1L);
    when(releaseRepository.getReferenceById(1L)).thenReturn(release);

    assertDoesNotThrow(
        () -> releaseService.updateRelease(1L, "2.0", LocalDateTime.of(2023, 4, 1, 0, 0),
            LocalDateTime.of(2023, 4, 30, 0, 0)));

    verify(releaseRepository, times(1)).getReferenceById(1L);
    verify(releaseRepository, times(1)).save(any());
  }

  @Test
  public void deleteReleaseByIdWithValidId() {
    Long id = 1L;
    doNothing().when(releaseRepository).deleteById(id);

    assertDoesNotThrow(() -> releaseService.deleteReleaseById(id));

    verify(releaseRepository, times(1)).deleteById(id);
  }
}
