package ru.itmo.wp.lesson8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.wp.lesson8.domain.Notice;
import ru.itmo.wp.lesson8.domain.User;

import java.util.List;

/**
 * @author dzahbarov
 */

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByOrderByCreationTimeDesc();
}
