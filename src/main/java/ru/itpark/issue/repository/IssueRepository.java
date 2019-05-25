package ru.itpark.issue.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itpark.issue.domain.Issue;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class IssueRepository {
    private final NamedParameterJdbcTemplate template;
    private final RowMapper<Issue> rowMapper = (rs, i) -> new Issue(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getDate("createDate"),
            rs.getInt("votes")
    );

    public IssueRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @PostConstruct
    public void init() {
        this.template.getJdbcTemplate().execute( // <- jdbcTemplate
                "create table if not exists issues ( " +
                        "    id                 serial, " +
                        "    name               varchar, " +
                        "    description        varchar , " +
                        "    createDate         date , " +
                        "    votes              integer ) "
        );
    }

    public List<Issue> getAll() {
        return template.query(
                "select id, name, description, createDate, votes from issues", rowMapper);
    }

    public void save(Issue issue) {
        if (issue.getId() == null) {
            template.update(
                    "INSERT INTO issues(name, description, createdate, votes ) VALUES (:name, :description, current_date, 0)",
                    Map.of("name", issue.getName(), "description", issue.getDescription()) // nullability <-
            );
        } else { // foreign key -> null
            template.update(
                    "UPDATE issues SET name = :name, description = :description WHERE id = :id",
                    Map.of("id", issue.getId(), "name", issue.getName(), "description", issue.getDescription())
            );
        }
    }

    public void removeById(long id) {
        template.update(
                "DELETE FROM burgers WHERE id = :id",
                Map.of("id", id)
        );
    }

    public Issue getById(long id) {
        return template.queryForObject(
                "select id, name, description, createDate, votes from issues WHERE id = :id",
                Map.of("id", id), rowMapper);
    }

    public List<Issue> getByName(String name) {
        return template.query(
                "select id, name, description, createDate, votes from issues WHERE name ilike '%'|| :name || '%'",
                Map.of("name", name), rowMapper);
    }


}
