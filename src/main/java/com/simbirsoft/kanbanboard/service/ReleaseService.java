package com.simbirsoft.kanbanboard.service;

import com.simbirsoft.kanbanboard.model.Task;
import com.simbirsoft.kanbanboard.model.Release;
import com.simbirsoft.kanbanboard.repository.ReleaseRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

/**
 * Сервис для работы с релизами.
 */
@Service
public class ReleaseService {

  private final ReleaseRepository releaseRepository;

  public ReleaseService(ReleaseRepository releaseRepository) {
    this.releaseRepository = releaseRepository;
  }

  /**
   * Получает список релизов, связанных с указанной задачей.
   *
   * @param task Задача, для получения релизов.
   * @return Список релизов, связанных с указанной задачей.
   */
  public List<Release> getReleasesByTask(Task task) {
    return releaseRepository.findByTask(task);
  }

  /**
   * Получает релиз по его идентификатору.
   *
   * @param id Идентификатор релиза.
   * @return Релиз с заданным идентификатором.
   */
  public Optional<Release> getReleaseById(Long id) {
    return releaseRepository.findById(id);
  }

  /**
   * Обновляет указанный релиз.
   *
   * @param release Релиз, подлежащий обновлению.
   * @throws IllegalArgumentException Если дата начала больше даты окончания.
   */
  public void updateRelease(Release release) {
    LocalDateTime startDate = release.getStartDate();
    LocalDateTime endDate = release.getEndDate();
    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException("Дата создания не может быть позже даты окончания");
    }
    releaseRepository.save(release);
  }

  /**
   * Обновляет указанный релиз, устанавливая версию, дату начала и дату конца в указанные значения.
   *
   * @param id Идентификатор выпуска, который будет обновлен.
   * @param version Новая версия релиза.
   * @param startDate Новая дата начала релиза.
   * @param endDate Новая дата окончания релиза.
   */
  public void updateRelease(Long id, String version, LocalDateTime startDate,
      LocalDateTime endDate) {
    Release release = releaseRepository.getReferenceById(id);
    release.setVersion(version);
    release.setStartDate(startDate);
    release.setEndDate(endDate);
    releaseRepository.save(release);
  }

  /**
   * Удаляет релиз с указанным идентификатором.
   *
   * @param id Идентификатор релиза, который будет удален.
   */
  public void deleteReleaseById(Long id) {
    releaseRepository.deleteById(id);
  }

  /**
   * Проверяет, принадлежит ли указанный релиз задаче с указанным идентификатором задачи.
   *
   * @param release Релиз, подлежащий проверке.
   * @param taskId Идентификатор задачи, к которой должен принадлежать релиз.
   * @throws IllegalArgumentException Если указанный релиз не относится к указанной задаче.
   */
  public void checkReleaseBelongsToTask(Release release, Long taskId) {
    if (!release.getTask().getId().equals(taskId)) {
      throw new IllegalArgumentException("Релиз не принадлежит задаче");
    }
  }

  /**
   * Проверяет, принадлежит ли указанный релиз проекту с указанным идентификатором проекта.
   *
   * @param release Релиз, подлежащий проверке.
   * @param projectId Идентификатор проекта, к которому должен принадлежать релиз.
   * @throws IllegalArgumentException Если указанный релиз не относится к указанной задаче и проекту
   */
  public void checkReleaseBelongsToProject(Release release, Long projectId) {
    if (!release.getTask().getProject().getId().equals(projectId)) {
      throw new IllegalArgumentException("Релиз не принадлежит задаче и проекту");
    }
  }
}
