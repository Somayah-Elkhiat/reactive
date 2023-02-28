package hibernate.reactive.service.impl;

import hibernate.reactive.entity.BoutiqaatTvProduct;
import hibernate.reactive.entity.Boutiqaattv;
import hibernate.reactive.model.TvListRequest;
import hibernate.reactive.repository.BoutiqaattvRepository;
import hibernate.reactive.service.BoutiqaattvService;
import io.smallrye.mutiny.converters.uni.UniReactorConverters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BoutiqaattvServiceImpl implements BoutiqaattvService {

    private final BoutiqaattvRepository boutiqaattvRepository;

    @Override
    public Mono<Boutiqaattv> getById(Long id) {
        return boutiqaattvRepository.getById(id).convert().with(UniReactorConverters.toMono());
    }

    @Override
    public Mono<Boutiqaattv> saveTv(Boutiqaattv tv) {
        return boutiqaattvRepository.saveTv(tv).convert().with(UniReactorConverters.toMono());
    }

    @Override
    public Flux<Boutiqaattv> getTvs() {
        return boutiqaattvRepository.getTvs().convert().with(UniReactorConverters.toMono())
                .flatMapMany(Flux::fromIterable);
    }

    @Override
    public Mono<Set<BoutiqaatTvProduct>> getProducts(Long tvId) {
        return Mono.from(boutiqaattvRepository.getProduct(tvId).convert().toPublisher());
    }

    @Override
    public Mono<List<Boutiqaattv>> getTvs(TvListRequest request, Integer pageIndex, Integer pageSize) {
        return Mono.from(boutiqaattvRepository.getByFilter(request, pageIndex, pageSize).convert().toPublisher());
    }
}
