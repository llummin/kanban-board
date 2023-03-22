package com.simbirsoft.kanbanboard.service;

import com.simbirsoft.kanbanboard.model.Release;
import com.simbirsoft.kanbanboard.model.Task;
import com.simbirsoft.kanbanboard.repository.ReleaseRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReleaseService {

  private final ReleaseRepository releaseRepository;

  public ReleaseService(ReleaseRepository releaseRepository) {
    this.releaseRepository = releaseRepository;
  }

  public List<Release> getReleasesByTask(Task task) {
    return releaseRepository.findByTask(task);
  }

  public void createRelease(Release release) {
    LocalDateTime startDate = release.getStartDate();
    LocalDateTime endDate = release.getEndDate();
    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException("Дата создания не может быть позже даты конца");
    }
    releaseRepository.save(release);
  }

  public void updateRelease(Long taskById, String version, LocalDateTime startDate,
      LocalDateTime endDate) {
    Release release = releaseRepository.getReferenceById(taskById);
    release.setVersion(version);
    release.setStartDate(startDate);
    release.setEndDate(endDate);
  }
}
