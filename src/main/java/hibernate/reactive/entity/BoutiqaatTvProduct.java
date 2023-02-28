package hibernate.reactive.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Table(name = "boutiqaattv_products")
@Data
@IdClass(BoutiqaatProductPK.class)
@Entity
public class BoutiqaatTvProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "tv_id")
    private Long tvId;

    @Id
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @ManyToOne
    @JsonIgnore
    @JoinFormula("tv_id")
    @Transient
    private Boutiqaattv boutiqaattv;

    @ManyToOne
    @JoinFormula("product_id")
    private CatalogProductEntity catalogProductEntity;

}

