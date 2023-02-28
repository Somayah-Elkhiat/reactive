package hibernate.reactive.repository.impl;

import hibernate.reactive.entity.BoutiqaatTvProduct;
import hibernate.reactive.entity.Boutiqaattv;
import hibernate.reactive.model.TvListRequest;
import hibernate.reactive.repository.BoutiqaattvRepository;
import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public Uni<List<Boutiqaattv>> getByFilter(TvListRequest tvListRequest, Integer pageIndex, Integer pageSize) {
        return sessionFactory.withSession(session -> session.createQuery(getFilterQuery(tvListRequest))
                .setMaxResults(pageSize).setFirstResult(pageIndex).getResultList());
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
    public Uni<Set<BoutiqaatTvProduct>> getProduct(Long tvId){
        return sessionFactory.withSession(session -> session.find(Boutiqaattv.class, tvId)
                .chain(boutiqaattv -> Mutiny.fetch(boutiqaattv.getProducts())));
    }

    private CriteriaQuery<Boutiqaattv> getFilterQuery(TvListRequest requestOptions) {
        CriteriaQuery<Boutiqaattv> query = sessionFactory.getCriteriaBuilder().createQuery(Boutiqaattv.class);
        Root<Boutiqaattv> root = query.from(Boutiqaattv.class);
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();

        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, Object> filterOptions : requestOptions.getFilters().entrySet()) {
            switch (filterOptions.getKey()) {
                case "enName", "videoUrl", "arabicName", "gender":  // for handing gender
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(
                            root.get(filterOptions.getKey()).as(String.class), "%" + filterOptions.getValue().toString() + "%")));
                    break;
                case "categoryId":
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(
                            root.get(filterOptions.getKey()).as(String.class), filterOptions.getValue().toString())));
                    break;
                case "idTo":
                    predicates.add(criteriaBuilder.and(
                            criteriaBuilder.lessThanOrEqualTo(root.get("id"), requestOptions.getIdTo())));
                    break;
                case "idFrom": // IdFrom
                    predicates.add(criteriaBuilder.and(
                            criteriaBuilder.greaterThanOrEqualTo(root.get("id"), requestOptions.getIdFrom())));
                    break;
                case "status":  // for handing status
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(
                            root.get(filterOptions.getKey()), filterOptions.getValue())));
                    break;
                case "id":
                    predicates.add(criteriaBuilder.and(root.get(filterOptions.getKey())
                            .in(requestOptions.getId())));
                    break;
                default:
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(
                            root.get(filterOptions.getKey()), filterOptions.getValue())));
                    break;
            }
        }
        return query.select(root).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
    }
}
