package entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@ToString
@Entity
@Getter
@Setter
public class RaceStat {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "bet_horse_result")
    private int betHorseResult;

    @Column(name = "horses_total")
    private int horsesTotal;

    public RaceStat() {
    }
}
