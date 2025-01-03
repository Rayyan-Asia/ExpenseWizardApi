package Rayyan.Asia.ExpenseWizard.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
public class Project {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Size(min = 3, max = 100, message
            = "Name must be between 10 and 200 characters")
    @NotBlank
    @Column(name = "project_name")
    private String name;

    @Column(name = "project_limit")
    private Float limit;

    @OneToMany(mappedBy = "project" ,cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Expense> expenses = new ArrayList<>();

    @Column(name = "created_at")
    private Timestamp createdAt = Timestamp.from(Instant.now());
}
