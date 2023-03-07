package hibernate.reactive.model;

import com.sun.istack.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MapUnMapProductRequest {

    private List<Integer> mapProducts;
    private List<Integer> unMapProducts;

}
