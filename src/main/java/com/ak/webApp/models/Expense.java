package com.ak.webApp.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Date;


@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Expense {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int expenseId;

    @Column
    @NonNull
    private Date date;

    @Column
    @NonNull
    @Size(max = 30)
    private String description;

    @Column
    @NonNull
    @Size(max = 30)
    private String category;

    @Column
    @NonNull
  //  @Positive
    private BigDecimal amount;

    @Column
    @NonNull
    @Size(max = 20)
    private String type;

    @ManyToOne
    private User user;

}
