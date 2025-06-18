package com.example.commitscanner.scheduler;

import com.example.commitscanner.entity.CommitRecord;
import com.example.commitscanner.service.CommitService;
import com.example.commitscanner.service.FakeAiAnalyzer;
import com.example.commitscanner.service.GitService;
import com.example.commitscanner.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RepoCheckScheduler {

    private final GitService gitService;
    private final EmailService emailService;
    private final CommitService commitService;
    private final FakeAiAnalyzer fakeAiAnalyzer;

    private String lastCommitHash = "";

    public RepoCheckScheduler(GitService gitService,
                              EmailService emailService,
                              CommitService commitService,
                              FakeAiAnalyzer fakeAiAnalyzer) {
        this.gitService = gitService;
        this.emailService = emailService;
        this.commitService = commitService;
        this.fakeAiAnalyzer = fakeAiAnalyzer;
    }

    @Scheduled(fixedRate = 60000) // test için 1 dakikada bir
    public void checkForNewCommit() {
        String currentCommitHash = gitService.getLatestCommitHash();

        if (currentCommitHash == null || currentCommitHash.equals(lastCommitHash)) {
            System.out.println("No new commit found.");
            return;
        }

        String author = gitService.getLatestCommitAuthorName();
        String email = "ozcangun2234@gmail.com"; // test için sabit
        String message = gitService.getLatestCommitMessage();

        // 🔍 AI analizi
        boolean hasIssue = fakeAiAnalyzer.isCommitSuspicious(message);
        String feedback = hasIssue ? "AI: Bu commit şüpheli görünüyor." : "AI: Sorun tespit edilmedi.";

        // 📧 Mail gönder
        emailService.sendCommitNotification(author, email, currentCommitHash, message + "\n\n" + feedback);

        // 🗃️ CommitRecord oluştur ve veritabanına kaydet
        CommitRecord commitRecord = new CommitRecord();
        commitRecord.setCommitHash(currentCommitHash);
        commitRecord.setAuthorName(author);
        commitRecord.setAuthorEmail(email);
        commitRecord.setMessage(message);
        commitRecord.setCommitDate(java.time.LocalDateTime.now()); // Git'ten alınan commit tarihi varsa onunla değiştir
        commitRecord.setHasIssue(hasIssue);
        commitRecord.setAiFeedback(feedback);
        commitRecord.setScannedAt(java.time.LocalDateTime.now());

        commitService.saveCommit(commitRecord);

        // ✅ Güncelle
        lastCommitHash = currentCommitHash;

        System.out.println("Commit kaydedildi ve mail gönderildi: " + currentCommitHash);
    }
}