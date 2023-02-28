package hibernate.reactive.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class BoutiqaatProductPK implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long tvId;

    private Integer productId;
}

