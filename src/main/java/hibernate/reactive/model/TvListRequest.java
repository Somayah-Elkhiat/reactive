package hibernate.reactive.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TvListRequest {

    private List<Long> id = new ArrayList<>();
    private String arabicName;
    private Integer brandId;
    private Date createdDateFrom;
    private Date createdDateTo;

    private Date startDateFrom;
    private Date startDateTo;

    private Date endDateFrom;
    private Date endDateTo;

    private String enName;
    private String tvGender;
    private Integer tvStatus;
    private Integer showInHome;
    private String thumbnail;
    private String thumbnailGrid;
    private String thumbnailList;
    private String videoUrl;
    private Integer viewsCount;
    private Map<String, Object> filterOptions;
    private String search;
    private Integer idFrom;
    private Integer idTo;
    private Integer sortOrderFrom;
    private Integer sortOrderTo;

    public Map<String, Object> getFilters() {

        Map<String, Object> filterOptions = new HashMap<>();
        filterOptions.put("id", (Objects.isNull(id) || id.isEmpty() ? null : id));
        filterOptions.put("gender", this.tvGender);
        filterOptions.put("status", this.tvStatus);
        filterOptions.put("arabicName", Strings.isBlank(this.arabicName) ? null : this.arabicName);
        filterOptions.put("enName", Strings.isBlank(this.enName) ? null : this.enName);
        filterOptions.put("showInHome", Objects.isNull(this.showInHome) ? null : this.showInHome);
        filterOptions.put("brandId", Objects.isNull(this.brandId) ? null : this.brandId);
        filterOptions.put("thumbnail", Strings.isBlank(this.thumbnail) ? null : this.thumbnail);
        filterOptions.put("thumbnailGrid", Strings.isBlank(this.thumbnailGrid) ? null : this.thumbnailGrid);
        filterOptions.put("thumbnailList", Strings.isBlank(this.thumbnailList) ? null : this.thumbnailList);
        filterOptions.put("videoUrl", Strings.isBlank(this.videoUrl) ? null : this.videoUrl);
        filterOptions.put("viewsCount", Objects.isNull(this.viewsCount) ? null : this.viewsCount);
        filterOptions.put("idTo", idTo);
        filterOptions.put("idFrom", idFrom);

        filterOptions.entrySet().removeIf(entry -> ObjectUtils.isEmpty(entry.getValue()));
        if (ObjectUtils.isEmpty(filterOptions) && !ObjectUtils.isEmpty(this.getFilterOptions())) {
            filterOptions.putAll(this.getFilterOptions());
        }
        filterOptions.entrySet().removeIf(entry -> ObjectUtils.isEmpty(entry.getValue()));
        return filterOptions;
    }
}
