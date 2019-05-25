package ru.itpark.issue.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Issue {
    Integer id;
    String name;
    String description;
    Date createDate;
    Integer votes;
}

