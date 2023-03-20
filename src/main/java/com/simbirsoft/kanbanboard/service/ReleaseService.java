package com.simbirsoft.kanbanboard.service;

import com.simbirsoft.kanbanboard.model.Project;
import com.simbirsoft.kanbanboard.model.Release;
import com.simbirsoft.kanbanboard.model.Task;
import com.simbirsoft.kanbanboard.repository.ReleaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReleaseService {

  private final ReleaseRepository releaseRepository;

  public ReleaseService(ReleaseRepository releaseRepository) {
    this.releaseRepository = releaseRepository;
  }

  public List<Release> getAllReleases() {
    return releaseRepository.findAll();
  }

  public Optional<Release> getReleaseById(Long id) {
    return releaseRepository.findById(id);
  }

  public Release saveRelease(Release release) {
    return releaseRepository.save(release);
  }

  public void deleteReleaseById(Long id) {
    releaseRepository.deleteById(id);
  }

  public List<Release> getReleasesByTask(Task task) {
    return releaseRepository.findByTask(task);
  }
}
