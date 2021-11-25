package ru.itmo.wp.lesson8.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.lesson8.domain.Notice;
import ru.itmo.wp.lesson8.repository.NoticeRepository;

import java.util.List;

/**
 * @author dzahbarov
 */
@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public List<Notice> findAll() {
        return noticeRepository.findAllByOrderByCreationTimeDesc();
    }

    public Notice save(Notice notice) {
        return noticeRepository.save(notice);
    }
}
