package hibernate.reactive.repository;

import hibernate.reactive.entity.BoutiqaatTvProduct;
import hibernate.reactive.entity.Boutiqaattv;
import io.smallrye.mutiny.Uni;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BoutiqaattvRepository {

    Uni<Boutiqaattv> getById(Long id);

    Uni<Boutiqaattv> saveTv(Boutiqaattv tv);

    Uni<List<Boutiqaattv>> getTvs();

    Uni<Set<BoutiqaatTvProduct>> getProduct(Long tvId);
}
