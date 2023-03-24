package com.simbirsoft.kanbanboard.service;

import com.simbirsoft.kanbanboard.model.Task;
import com.simbirsoft.kanbanboard.model.Release;
import com.simbirsoft.kanbanboard.repository.ReleaseRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private static final Logger logger = LoggerFactory.getLogger(ReleaseService.class);

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
    logger.debug("Вызван метод getReleasesByTask с параметром task: {}", task);
    return releaseRepository.findByTask(task);
  }

  /**
   * Получает релиз по его идентификатору.
   *
   * @param id Идентификатор релиза.
   * @return Релиз с заданным идентификатором.
   */
  public Optional<Release> getReleaseById(Long id) {
    logger.debug("Вызван метод getReleaseById с параметром id: {}", id);
    Optional<Release> release = releaseRepository.findById(id);
    if (release.isPresent()) {
      logger.debug("Найден релиз с id {}", id);
    } else {
      logger.debug("Релиз с id {} не найден", id);
    }
    return release;
  }

  /**
   * Обновляет указанный релиз.
   *
   * @param release Релиз, подлежащий обновлению.
   * @throws IllegalArgumentException Если дата начала больше даты окончания.
   */
  public void updateRelease(Release release) {
    logger.debug("Вызван метод updateRelease с параметром release: {}", release);
    LocalDateTime startDate = release.getStartDate();
    LocalDateTime endDate = release.getEndDate();
    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException("Дата создания не может быть позже даты окончания");
    }
    releaseRepository.save(release);
    logger.debug("Релиз успешно обновлен: {}", release);
  }

  /**
   * Обновляет указанный релиз, устанавливая версию, дату начала и дату конца в указанные значения.
   *
   * @param id        Идентификатор выпуска, который будет обновлен.
   * @param version   Новая версия релиза.
   * @param startDate Новая дата начала релиза.
   * @param endDate   Новая дата окончания релиза.
   */
  public void updateRelease(Long id, String version, LocalDateTime startDate,
      LocalDateTime endDate) {
    logger.debug(
        "Вызван метод updateRelease с параметрами id: {}, version: {}, startDate: {}, endDate: {}",
        id, version, startDate, endDate);
    Release release = releaseRepository.getReferenceById(id);
    release.setVersion(version);
    release.setStartDate(startDate);
    release.setEndDate(endDate);
    releaseRepository.save(release);
    logger.debug("Релиз с id {} успешно обновлен: {}", id, release);
  }

  /**
   * Удаляет релиз с указанным идентификатором.
   *
   * @param id Идентификатор релиза, который будет удален.
   */
  public void deleteReleaseById(Long id) {
    logger.debug("Вызван метод deleteReleaseById с параметром id: {}", id);
    releaseRepository.deleteById(id);
    logger.debug("Релиз с id {} удален", id);
  }

  /**
   * Проверяет, принадлежит ли указанный релиз задаче с указанным идентификатором задачи.
   *
   * @param release Релиз, подлежащий проверке.
   * @param taskId  Идентификатор задачи, к которой должен принадлежать релиз.
   * @throws IllegalArgumentException Если указанный релиз не относится к указанной задаче.
   */
  public void checkReleaseBelongsToTask(Release release, Long taskId) {
    logger.debug("Вызван метод checkReleaseBelongsToTask с параметрами release: {}, taskId: {}",
        release, taskId);
    if (!release.getTask().getId().equals(taskId)) {
      throw new IllegalArgumentException("Релиз не принадлежит задаче");
    }
    logger.debug("Релиз {} принадлежит задаче {}", release, taskId);
  }
}
