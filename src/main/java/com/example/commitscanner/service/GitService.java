package com.example.commitscanner.service;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class GitService {

    private final String repoPath = "C:/git/evam-repo"; // senin test ettiğin klasör

    public String getLatestCommitHash() {
        try (Git git = Git.open(new File(repoPath))) {
            Iterable<RevCommit> logs = git.log().setMaxCount(1).call();
            for (RevCommit commit : logs) {
                return commit.getName();
            }
        } catch (Exception e) {
            System.err.println("Failed to get latest commit hash: " + e.getMessage());
        }
        return null;
    }

    public String getLatestCommitMessage() {
        try (Git git = Git.open(new File(repoPath))) {
            Iterable<RevCommit> logs = git.log().setMaxCount(1).call();
            for (RevCommit commit : logs) {
                return commit.getShortMessage();
            }
        } catch (Exception e) {
            System.err.println("Failed to get commit message: " + e.getMessage());
        }
        return null;
    }

    public String getLatestCommitAuthorEmail() {
        try (Git git = Git.open(new File(repoPath))) {
            Iterable<RevCommit> logs = git.log().setMaxCount(1).call();
            for (RevCommit commit : logs) {
                return commit.getAuthorIdent().getEmailAddress();
            }
        } catch (Exception e) {
            System.err.println("Failed to get commit author email: " + e.getMessage());
        }
        return null;
    }

    public String getLatestCommitAuthorName() {
        try (Git git = Git.open(new File(repoPath))) {
            Iterable<RevCommit> logs = git.log().setMaxCount(1).call();
            for (RevCommit commit : logs) {
                return commit.getAuthorIdent().getName();
            }
        } catch (Exception e) {
            System.err.println("Failed to get commit author name: " + e.getMessage());
        }
        return null;
    }

    public LocalDateTime getLatestCommitDate() {
        try (Git git = Git.open(new File(repoPath))) {
            Iterable<RevCommit> logs = git.log().setMaxCount(1).call();
            for (RevCommit commit : logs) {
                return Instant.ofEpochSecond(commit.getCommitTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
            }
        } catch (Exception e) {
            System.err.println("Failed to get commit date: " + e.getMessage());
        }
        return null;
    }
}

