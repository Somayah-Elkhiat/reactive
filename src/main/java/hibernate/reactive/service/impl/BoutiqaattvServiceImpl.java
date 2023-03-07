package hibernate.reactive.service.impl;

import hibernate.reactive.controller.BoutiqaattvController;
import hibernate.reactive.entity.BoutiqaatProductPK;
import hibernate.reactive.entity.BoutiqaatTvProduct;
import hibernate.reactive.entity.Boutiqaattv;
import hibernate.reactive.entity.CatalogProductEntity;
import hibernate.reactive.exception.BoutiqaatTvNotFoundException;
import hibernate.reactive.model.TvListRequest;
import hibernate.reactive.repository.BoutiqaattvRepository;
import hibernate.reactive.service.BoutiqaattvService;
import io.smallrye.mutiny.converters.uni.UniReactorConverters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoutiqaattvServiceImpl implements BoutiqaattvService {

    private final BoutiqaattvRepository boutiqaattvRepository;

    @Override
    public Mono<Boutiqaattv> getById(Long id) {
        return boutiqaattvRepository.getById(id).convert().with(UniReactorConverters.toMono())
                .switchIfEmpty(Mono.error(new BoutiqaatTvNotFoundException("tv is not found")));
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

    @Override
    public void mapProductToTv(Long tvId, List<Integer> mapProducts) throws ExecutionException, InterruptedException {
        Boutiqaattv tv = getById(tvId).toFuture().get();

        Set<Integer> existIds = getProducts(tvId).flatMapMany(Flux::fromIterable)
                .subscribeOn(Schedulers.boundedElastic())
                .map(boutiqaatTvProduct -> boutiqaatTvProduct.getProductId())
                .collect(Collectors.toSet()).toFuture().get();

        //remove the ids that already mapped
        mapProducts.removeAll(existIds);

        List<CatalogProductEntity> catalogProductEntities = Mono.from(boutiqaattvRepository
                .getCatalogProduct(mapProducts).convert().toPublisher()).toFuture().get();

        for (CatalogProductEntity product:catalogProductEntities){
            BoutiqaatTvProduct boutiqaatTvProduct = new BoutiqaatTvProduct();
            boutiqaatTvProduct.setCatalogProductEntity(product);
            boutiqaatTvProduct.setProductId(product.getRowId());
            boutiqaatTvProduct.setBoutiqaattv(tv);
            boutiqaatTvProduct.setTvId(tvId);
            boutiqaatTvProduct.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
            boutiqaatTvProduct.setSortOrder(0);
            boutiqaattvRepository.saveTvProducts(boutiqaatTvProduct);
        }

    }

    @Override
    public void unmapProductToTv(Long tvId, List<Integer> unMapProducts) throws ExecutionException, InterruptedException {
        Boutiqaattv tv = getById(tvId).toFuture().get();

        Set<BoutiqaatTvProduct> toBeRemovedProducts =
                getProducts(tvId).flatMapMany(Flux::fromIterable)
                .subscribeOn(Schedulers.boundedElastic())
                .collect(Collectors.toSet()).toFuture().get()
                .stream().filter(boutiqaatTvProduct -> unMapProducts.contains(boutiqaatTvProduct.getProductId()))
                .collect(Collectors.toSet());

        for(BoutiqaatTvProduct product : toBeRemovedProducts){
            BoutiqaatProductPK productPK = new BoutiqaatProductPK(product.getTvId(), product.getProductId());
            boutiqaattvRepository.deleteTvProduct(product, productPK);
        }

    }


}
