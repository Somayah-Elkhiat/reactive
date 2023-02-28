package hibernate.reactive.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Set;

@Table(name = "catalog_product_entity")
@Data
@NoArgsConstructor
@Entity
public class CatalogProductEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "row_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rowId;

    @Column(name = "attribute_set_id")
    private Integer attributeSetId;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "created_in")
    private BigInteger createdIn;

    @Column(name = "has_options")
    private short hasOptions;

    @Column(name = "required_options")
    private Integer requiredOptions;

    private String sku;

    @Column(name = "type_id")
    private String typeId;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "updated_in")
    private BigInteger updatedIn;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "catalogProductEntity", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<BoutiqaatTvProduct> tvs;

}
