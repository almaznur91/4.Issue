package ru.itpark.issue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itpark.issue.domain.Issue;
import ru.itpark.issue.repository.IssueRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueService {
    private final IssueRepository issueRepository;

    public List<Issue> getAll() {
        return issueRepository.getAll();
    }

    public Issue getById(Integer id) {
        return issueRepository.getById(id);
    }

    public List<Issue> getByName(String name){
        return issueRepository.getByName(name);
    }

    public void removeById(long id) {
        issueRepository.removeById(id);
    }

    public void save(Issue issue) {
        issueRepository.save(issue);
    }

    public Issue getIssue(Integer id) {
        return issueRepository.getById(id);
    }
}
