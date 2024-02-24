package Rayyan.Asia.ExpenseWizard.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table
@Data
public class Expense {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @NotNull
    private Float cost;

    private Timestamp timestamp = Timestamp.from(Instant.now());

    @NotNull
    @Column(name = "category")
    private ExpenseCategory expenseCategory;
    private String description;
}
