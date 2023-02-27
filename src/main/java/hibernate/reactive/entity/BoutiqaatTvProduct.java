package hibernate.reactive.entity;

import lombok.Data;
//import org.springframework.data.relational.core.mapping.Table;
import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "boutiqaattv_products")
@Data
@IdClass(BoutiqaatProductPK.class)
@Entity
public class BoutiqaatTvProduct {

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Id
    @ManyToOne
    @JoinColumn(name = "tv_id")
    private Boutiqaattv boutiqaattv;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private CatalogProductEntity catalogProductEntity;

}

