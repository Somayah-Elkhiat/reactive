package hibernate.reactive.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "boutiqaattv")
@Data
public class Boutiqaattv implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "arabic_name")
    private String arabicName;

    @Column(name = "brand_id")
    private Integer brandId;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "celebrity_id")
    private Integer celebrityId;

    @Column(name = "created_date")
    //    @JsonSerialize(using = CustomDateSerializer.class)
    private Timestamp createdDate;

    @Column(name = "end_date")
    //    @JsonSerialize(using = CustomDateSerializer.class)
    private LocalDate endDate;

    @Column(name = "start_date")
    //    @JsonSerialize(using = CustomDateSerializer.class)
    private LocalDate startDate;

    @Column(name = "en_name")
    private String enName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "show_in_home")
    private Integer showInHome;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "status")
    private Integer status;

    @Column(name = "store_ids")
    //    @Column(name = "store_ids")
    private String storeIds;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "thumbnail_grid")
    private String thumbnailGrid;

    @Column(name = "thumbnail_list")
    private String thumbnailList;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "views_count")
    private Integer viewsCount;

    @Column(name = "tv_display")
    private String tvDisplay;

    @Column(name = "exclude_country")
    private String excludeCountry;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "boutiqaattv", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<BoutiqaatTvProduct> products;


}