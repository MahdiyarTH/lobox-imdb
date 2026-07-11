package org.lobox.imdb.service.imp;

import org.lobox.common.data.base.BaseServiceImp;
import org.lobox.imdb.entity.ProductEntity;
import org.lobox.imdb.repository.ProductRepository;
import org.lobox.imdb.service.PersonService;
import org.lobox.imdb.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImp
        extends BaseServiceImp<ProductEntity, String, ProductRepository>
        implements ProductService {

    private final PersonService personService;

    public ProductServiceImp(ProductRepository repository, PersonService personService) {
        super(repository);
        this.personService = personService;
    }

    @Override
    public Page<String> findAllSameWriteAndDirectorAlive(int page, int size) {
        return getRepository()
                .findAllSameWriteAndDirectorAlive(
                        PageRequest.of(page, size)
                );
    }

    @Override
    public Page<String> findAllTitlesWithTwoActors(Long firstActorId, Long secondActorId, int page, int size) {
        return getRepository()
                .findAllTitlesWithTwoActors(
                        firstActorId,
                        secondActorId,
                        PageRequest.of(page, size)
                );
    }
}
