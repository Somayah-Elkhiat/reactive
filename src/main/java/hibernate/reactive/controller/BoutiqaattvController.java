package hibernate.reactive.controller;

import hibernate.reactive.entity.BoutiqaatTvProduct;
import hibernate.reactive.entity.Boutiqaattv;
import hibernate.reactive.model.MapUnMapProductRequest;
import hibernate.reactive.model.TvListRequest;
import hibernate.reactive.service.BoutiqaattvService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/v1/tv")
@RequiredArgsConstructor
public class BoutiqaattvController {

    private final BoutiqaattvService boutiqaattvService;

    @GetMapping("/{id}")
    public Mono<Boutiqaattv> getById(@PathVariable Long id){
        return boutiqaattvService.getById(id);

    }

    @PostMapping("/save")
    public Mono<Boutiqaattv> saveTv(@RequestBody Boutiqaattv tv){
        return boutiqaattvService.saveTv(tv);

    }


    @GetMapping("/products/{id}")
    public Flux<BoutiqaatTvProduct> getProducts(@PathVariable Long id){
        return boutiqaattvService.getProducts(id)
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping("/list")
    public Flux<Boutiqaattv> getTvs(@RequestBody TvListRequest request,
                                    @RequestParam("page") int pageIndex,
                                    @RequestParam("size") int pageSize){
        return boutiqaattvService.getTvs(request, pageIndex, pageSize)
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping("/mapUnMapProductToTv/{tvId}")
    public void mapUnMapProductToTv(@NonNull @PathVariable Long tvId,
                                    @RequestBody MapUnMapProductRequest request)
            throws ExecutionException, InterruptedException {
        if (!request.getMapProducts().isEmpty() ) {
            boutiqaattvService.mapProductToTv(tvId, request.getMapProducts());
        }
        if(!request.getUnMapProducts().isEmpty()) {
            boutiqaattvService.unmapProductToTv(tvId, request.getUnMapProducts());
        }
    }

}
