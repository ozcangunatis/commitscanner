package com.example.commitscanner.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table
public class CommitRecord {
    @Id
    private String commitHash;

    private String authorName;
    private String authorEmail;
    private String message;
    private LocalDateTime commitDate;

    private boolean hasIssue; // AI hatalı buldu mu?
    private String aiFeedback; // AI açıklaması
    private LocalDateTime scannedAt; // Commit ne zaman analiz edildi

    public CommitRecord() {}

    public CommitRecord(String commitHash, String authorName, String authorEmail, String message,
                        LocalDateTime commitDate, boolean hasIssue, String aiFeedback, LocalDateTime scannedAt) {
        this.commitHash = commitHash;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.message = message;
        this.commitDate = commitDate;
        this.hasIssue = hasIssue;
        this.aiFeedback = aiFeedback;
        this.scannedAt = scannedAt;
    }

    public String getCommitHash() {
        return commitHash;
    }

    public void setCommitHash(String commitHash) {
        this.commitHash = commitHash;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(LocalDateTime commitDate) {
        this.commitDate = commitDate;
    }

    public boolean isHasIssue() {
        return hasIssue;
    }

    public void setHasIssue(boolean hasIssue) {
        this.hasIssue = hasIssue;
    }

    public String getAiFeedback() {
        return aiFeedback;
    }

    public void setAiFeedback(String aiFeedback) {
        this.aiFeedback = aiFeedback;
    }

    public LocalDateTime getScannedAt() {
        return scannedAt;
    }

    public void setScannedAt(LocalDateTime scannedAt) {
        this.scannedAt = scannedAt;
    }
}
