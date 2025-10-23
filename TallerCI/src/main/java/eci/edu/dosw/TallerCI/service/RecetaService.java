package eci.edu.dosw.TallerCI.service;
import eci.edu.dosw.TallerCI.model.Receta;
import eci.edu.dosw.TallerCI.model.TipoAutor;
import eci.edu.dosw.TallerCI.model.DTOS.RecetaDTO;
import eci.edu.dosw.TallerCI.repository.RecetaRepository;
import eci.edu.dosw.TallerCI.util.RecetaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;
    @Autowired
    private RecetaMapper recetaMapper;

    public RecetaDTO registrar(RecetaDTO recetaDTO) {
        Receta receta = recetaMapper.toReceta(recetaDTO);
        Receta guardada = recetaRepository.save(receta);
        return recetaMapper.toRecetaDTO(guardada);
    }

    public List<RecetaDTO> obtenerTodas() {
        return recetaRepository.findAll().stream().map(recetaMapper::toRecetaDTO).collect(Collectors.toList());
    }

    public Optional<RecetaDTO> obtenerPorId(String id) {
        return recetaRepository.findById(id).map(recetaMapper::toRecetaDTO);
    }

    public List<RecetaDTO> obtenerPorTipo(TipoAutor tipoAutor) {
        return recetaRepository.findByTipoAutor(tipoAutor).stream().map(recetaMapper::toRecetaDTO).collect(Collectors.toList());
    }

    public List<RecetaDTO> obtenerPorTemporada(Integer temporada) {
        return recetaRepository.findByTemporada(temporada).stream().map(recetaMapper::toRecetaDTO).collect(Collectors.toList());
    }

    public List<RecetaDTO> buscarPorIngrediente(String ingrediente) {
        return recetaRepository.findAll().stream()
                .filter(r -> r.getIngredientes() != null &&
                        r.getIngredientes().stream()
                                .anyMatch(i -> i.toLowerCase().contains(ingrediente.toLowerCase())))
                .map(recetaMapper::toRecetaDTO)
                .collect(Collectors.toList());
    }

    public RecetaDTO actualizar(String id, RecetaDTO datosActualizados) {
        Optional<Receta> recetaExistente = recetaRepository.findById(id);

        if (recetaExistente.isEmpty()) {
            throw new IllegalArgumentException("No se encontró una receta con el ID proporcionado.");
        }

        Receta receta = recetaExistente.get();
        Receta nuevosDatos = recetaMapper.toReceta(datosActualizados);

        receta.setTitulo(nuevosDatos.getTitulo());
        receta.setAutor(nuevosDatos.getAutor());
        receta.setIngredientes(nuevosDatos.getIngredientes());
        receta.setPasos(nuevosDatos.getPasos());
        receta.setTipoAutor(nuevosDatos.getTipoAutor());
        receta.setTemporada(nuevosDatos.getTemporada());

        return recetaMapper.toRecetaDTO(recetaRepository.save(receta));
    }

    public void eliminar(String id) {
        recetaRepository.deleteById(id);
    }
}


