package zdorovo.tochka.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Ingridient> ingridients;

    private Integer calories;

    private String photo; //#todo maybe list

    @Column(columnDefinition="TEXT")
    private String preparation;//#todo remove
    //#todo add entity Step, which describe dish preparation
    //#todo add entity KitchenTools: pan, mixer, grill and etc

}
