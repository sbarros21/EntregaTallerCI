package eci.edu.dosw.TallerCI.model.DTOS;

import eci.edu.dosw.TallerCI.model.TipoAutor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecetaDTO {
    private String titulo;
    private String autor;
    private TipoAutor tipoAutor;
    private List<String> ingredientes;
    private List<String> pasos;
    private Integer temporada;
}
