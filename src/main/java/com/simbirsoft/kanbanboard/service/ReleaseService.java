package com.simbirsoft.kanbanboard.service;

import com.simbirsoft.kanbanboard.model.Task;
import com.simbirsoft.kanbanboard.model.Release;
import com.simbirsoft.kanbanboard.repository.ReleaseRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
public class ReleaseService {

  private final ReleaseRepository releaseRepository;

  public ReleaseService(ReleaseRepository releaseRepository) {
    this.releaseRepository = releaseRepository;
  }

  public List<Release> getReleasesByTask(Task task) {
    return releaseRepository.findByTask(task);
  }

  public Optional<Release> getReleaseById(Long id) {
    return releaseRepository.findById(id);
  }

  public void updateRelease(Release release) {
    LocalDateTime startDate = release.getStartDate();
    LocalDateTime endDate = release.getEndDate();
    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException("Дата создания не может быть позже даты окончания");
    }
    releaseRepository.save(release);
  }

  public void updateRelease(Long id, String version, LocalDateTime startDate,
      LocalDateTime endDate) {
    Release release = releaseRepository.getReferenceById(id);
    release.setVersion(version);
    release.setStartDate(startDate);
    release.setEndDate(endDate);
    releaseRepository.save(release);
  }

  public void deleteReleaseById(Long id) {
    releaseRepository.deleteById(id);
  }

  public void checkReleaseBelongsToTask(Release release, Long taskId) {
    if (!release.getTask().getId().equals(taskId)) {
      throw new IllegalArgumentException("Релиз не принадлежит задаче");
    }
  }

  public void checkReleaseBelongsToProject(Release release, Long projectId) {
    if (!release.getTask().getProject().getId().equals(projectId)) {
      throw new IllegalArgumentException("Релиз не принадлежит задаче и проекту");
    }
  }
}
