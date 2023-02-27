package hibernate.reactive.controller;

import hibernate.reactive.entity.BoutiqaatTvProduct;
import hibernate.reactive.entity.Boutiqaattv;
import hibernate.reactive.service.BoutiqaattvService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/tv")
@RequiredArgsConstructor
public class BoutiqaattvController {

    private final BoutiqaattvService boutiqaattvService;

    @GetMapping("/{id}")
    public Mono<Boutiqaattv> getById(@PathVariable Long id){
        return boutiqaattvService.getById(id);

    }

    @PostMapping
    public Mono<Boutiqaattv> getById(@RequestBody Boutiqaattv tv){
        return boutiqaattvService.saveTv(tv);

    }

    @GetMapping()
    public Flux<Boutiqaattv> getTvs(){
        return boutiqaattvService.getTvs();

    }

//    @GetMapping("/products/{id}")
//    public Mono<List<BoutiqaatTvProduct>> getProducts(@PathVariable Long id){
//        return boutiqaattvService.getProducts(id);
//
//    }

    @GetMapping("/products/{id}")
    public Flux<BoutiqaatTvProduct> getProducts(@PathVariable Long id){
        return boutiqaattvService.getProducts(id)
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(Schedulers.boundedElastic());
    }
}
