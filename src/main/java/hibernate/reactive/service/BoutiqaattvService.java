package hibernate.reactive.service;

import hibernate.reactive.entity.BoutiqaatTvProduct;
import hibernate.reactive.entity.Boutiqaattv;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Service
public interface BoutiqaattvService {

    Mono<Boutiqaattv> getById(Long id);

    Mono<Boutiqaattv> saveTv(Boutiqaattv tv);

    Flux<Boutiqaattv> getTvs();

    Mono<List<BoutiqaatTvProduct>> getProducts(Long tvId);
}
