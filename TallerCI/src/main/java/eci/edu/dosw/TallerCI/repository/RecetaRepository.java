package eci.edu.dosw.TallerCI.repository;

import eci.edu.dosw.TallerCI.model.Receta;
import eci.edu.dosw.TallerCI.model.TipoAutor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecetaRepository extends MongoRepository<Receta, String> {
    List<Receta> findByTipoAutor(TipoAutor tipoAutor);
    List<Receta> findByTemporada(Integer temporada);
}
