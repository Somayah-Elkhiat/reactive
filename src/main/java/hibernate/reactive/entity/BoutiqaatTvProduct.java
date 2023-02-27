package hibernate.reactive.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
//import org.springframework.data.relational.core.mapping.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Table(name = "boutiqaattv_products")
@Data
@IdClass(BoutiqaatProductPK.class)
@Entity
public class BoutiqaatTvProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tv_id")
    @JsonIgnore
    private Boutiqaattv boutiqaattv;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id" )
    @JsonIgnore
    private CatalogProductEntity catalogProductEntity;

}

