package hibernate.reactive.repository;

import hibernate.reactive.entity.BoutiqaatProductPK;
import hibernate.reactive.entity.BoutiqaatTvProduct;
import hibernate.reactive.entity.Boutiqaattv;
import hibernate.reactive.entity.CatalogProductEntity;
import hibernate.reactive.model.TvListRequest;
import io.smallrye.mutiny.Uni;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BoutiqaattvRepository {

    Uni<Boutiqaattv> getById(Long id);

    Uni<List<Boutiqaattv>> getByFilter(TvListRequest tvListRequest, Integer pageIndex, Integer pageSize);

    Uni<Boutiqaattv> saveTv(Boutiqaattv tv);

    Uni<List<Boutiqaattv>> getTvs();

    Uni<Set<BoutiqaatTvProduct>> getProduct(Long tvId);

    Uni<List<CatalogProductEntity>> getCatalogProduct(List<Integer> id);

    Void saveTvProducts(BoutiqaatTvProduct boutiqaatTvProductList);

    Object deleteTvProduct(BoutiqaatTvProduct boutiqaatTvProduct, BoutiqaatProductPK productPK);
}
