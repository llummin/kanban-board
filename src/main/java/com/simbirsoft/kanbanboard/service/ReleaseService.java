package com.simbirsoft.kanbanboard.service;

import com.simbirsoft.kanbanboard.model.Release;
import com.simbirsoft.kanbanboard.repository.ReleaseRepository;
import org.springframework.stereotype.Service;

@Service
public class ReleaseService {

  private final ReleaseRepository releaseRepository;

  public ReleaseService(ReleaseRepository releaseRepository) {
    this.releaseRepository = releaseRepository;
  }

  public void createRelease(Release release) {
    releaseRepository.save(release);
  }
}
