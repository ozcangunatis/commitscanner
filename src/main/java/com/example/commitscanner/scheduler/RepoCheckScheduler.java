package com.example.commitscanner.scheduler;

import com.example.commitscanner.client.FlaskAiClient;
import com.example.commitscanner.entity.CommitRecord;
import com.example.commitscanner.service.CommitService;
import com.example.commitscanner.service.FakeAiAnalyzer;
import com.example.commitscanner.service.GitService;
import com.example.commitscanner.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class RepoCheckScheduler {

    private final GitService gitService;
    private final EmailService emailService;
    private final CommitService commitService;
    private final FakeAiAnalyzer fakeAiAnalyzer;
    private final FlaskAiClient flaskAiClient;
    private String lastCommitHash = "";

    public RepoCheckScheduler(GitService gitService,
                              EmailService emailService,
                              CommitService commitService,
                              FakeAiAnalyzer fakeAiAnalyzer,
                              FlaskAiClient flaskAiClient) {
        this.gitService = gitService;
        this.emailService = emailService;
        this.commitService = commitService;
        this.fakeAiAnalyzer = fakeAiAnalyzer;
        this.flaskAiClient = flaskAiClient;
    }


    @Scheduled(fixedRate = 60000)
    public void checkForNewCommit() {
        String currentCommitHash = gitService.getLatestCommitHash();

        if (currentCommitHash == null || currentCommitHash.equals(lastCommitHash)) {
            System.out.println("No new commit found.");
            return;
        }

        String author = gitService.getLatestCommitAuthorName();
        String email = "ozcangun2234@gmail.com";
        String message = gitService.getLatestCommitMessage();
        String diff = gitService.getLastCommitDiff();


        Map<String, Object> analysisResult = flaskAiClient.analyzeCommit(message, diff);
        boolean hasIssue = (Boolean) analysisResult.get("hasIssue");
        String feedback = (String) analysisResult.get("feedback");

        String openingLine = hasIssue
                ? "A potentially suspicious commit has been detected by the CommitScanner system."
                : "The following commit has been reviewed and no issues were found.";

        String formattedTimestamp = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String subject = hasIssue
                ? "Suspicious Commit Detected"
                : "Commit Passed AI Review";

        String body = """
Hello %s,

%s

Commit Details:
- Commit ID   : %s
- Author      : %s
- Message     : %s
- Timestamp   : %s

AI Analysis Result:
%s

Changed Code (First 30 lines):
%s

Recommended Action:
Please review the above commit to ensure it meets your project's quality and security standards.

Best regards,
CommitScanner Bot
""".formatted(
                author,
                openingLine,
                currentCommitHash,
                author,
                message,
                formattedTimestamp,
                feedback,
                diff.lines().limit(30).collect(java.util.stream.Collectors.joining("\n"))
        );

        emailService.sendCommitNotification(email, subject, body);

        CommitRecord commitRecord = new CommitRecord();
        commitRecord.setCommitHash(currentCommitHash);
        commitRecord.setAuthorName(author);
        commitRecord.setAuthorEmail(email);
        commitRecord.setMessage(message);
        commitRecord.setCommitDate(LocalDateTime.now());
        commitRecord.setHasIssue(hasIssue);
        commitRecord.setAiFeedback(feedback);
        commitRecord.setScannedAt(LocalDateTime.now());

        commitService.saveCommit(commitRecord);
        lastCommitHash = currentCommitHash;

        System.out.println("Commit saved and email sent: " + currentCommitHash);
    }

}

