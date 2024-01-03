package Rayyan.Asia.ExpenseWizard.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

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
    private UserEntity userEntity;

    @Size(min = 3, max = 100, message
            = "Name must be between 10 and 200 characters")
    @NotBlank
    private String name;

    private Float limit;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Expense> expenses = new ArrayList<>();
}
