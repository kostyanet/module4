package entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * дата проведения,
 * общее кол-во лошадей,
 * место каждой из них и
 * номер лошади на которую ставил пользователь.
 */
@ToString
@Entity
@Getter
@Setter
@Table(name = "race_report")
public class RaceReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "created")
    private LocalDateTime created;

    @NonNull
    @Column(name = "horses_total")
    private int horsesTotal;

    @NonNull
    @Column(name = "horses_ordered")
    private String horsesOrdered;

    @NonNull
    @Column(name = "bet_horse_result")
    private int betHorseResult;

    public RaceReport() {
    }

    public RaceReport(
            @NonNull LocalDateTime created,
            @NonNull int horsesTotal,
            @NonNull String horsesOrdered,
            @NonNull int betHorseResult
    ) {
        this.created = created;
        this.horsesTotal = horsesTotal;
        this.horsesOrdered = horsesOrdered;
        this.betHorseResult = betHorseResult;
    }
}
