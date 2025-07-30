package com.example.commitscanner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommitLog {
    private String commitId;
    private Strg authorName;
    private String authorEmail;
    private String commitDate;
    private String message;
}
