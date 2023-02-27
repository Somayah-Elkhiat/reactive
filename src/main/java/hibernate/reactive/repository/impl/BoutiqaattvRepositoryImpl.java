package hibernate.reactive.repository.impl;

import hibernate.reactive.entity.BoutiqaatTvProduct;
import hibernate.reactive.entity.Boutiqaattv;
import hibernate.reactive.repository.BoutiqaattvRepository;
import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Component
@Transactional
public class BoutiqaattvRepositoryImpl implements BoutiqaattvRepository {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("tvManage");

    Mutiny.SessionFactory sessionFactory = emf.unwrap(Mutiny.SessionFactory.class);

    @Override
    public Uni<Boutiqaattv> getById(Long id) {
        return sessionFactory.withSession(session -> session.find(Boutiqaattv.class, id));
    }

    @Override
    public Uni<Boutiqaattv> saveTv(Boutiqaattv tv) {
        return sessionFactory.withSession(session -> session.persist(tv)
                .chain(session::flush).replaceWith(() -> tv));
    }

    @Override
    public Uni<List<Boutiqaattv>> getTvs() {
        return sessionFactory.withSession(session -> session.find(Boutiqaattv.class));
    }

    @Override
    public Uni<List<BoutiqaatTvProduct>> getProduct(Long tvId){
//        return sessionFactory.withSession(session -> session.find(Boutiqaattv.class, tvId)
//                .chain(boutiqaattv -> Mutiny.fetch(boutiqaattv.getProducts())));

//                .log();
        CriteriaQuery<BoutiqaatTvProduct> query = sessionFactory.getCriteriaBuilder().createQuery(BoutiqaatTvProduct.class);
        Root<Boutiqaattv> a = query.from(Boutiqaattv.class);
        Join<Boutiqaattv,BoutiqaatTvProduct> b = a.join("products");
        query.select(b).where(sessionFactory.getCriteriaBuilder().equal(a.get("id"), tvId));
        return sessionFactory.withSession(session ->
                session.createQuery(query)
                        .getResultList()).log();
    }
}
