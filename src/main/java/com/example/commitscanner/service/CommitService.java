package com.example.commitscanner.service;

import com.example.commitscanner.entity.CommitRecord;
import com.example.commitscanner.repository.CommitRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class CommitService {
    private final CommitRecordRepository commitRecordRepository;
    public CommitService(CommitRecordRepository commitRecordRepository) {
        this.commitRecordRepository = commitRecordRepository;
    }

    public void saveCommit(CommitRecord commitRecord) {
        commitRecordRepository.save(commitRecord);
    }

}
