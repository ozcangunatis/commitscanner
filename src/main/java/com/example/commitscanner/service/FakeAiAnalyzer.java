package com.example.commitscanner.service;

import org.springframework.stereotype.Component;

@Component
public class FakeAiAnalyzer {

    public boolean isCommitSuspicious(String commitMessage) {
        String msg = commitMessage.toLowerCase();
        return msg.contains("fix") || msg.contains("bug") || msg.contains("urgent") || msg.contains("temp");
    }
}
