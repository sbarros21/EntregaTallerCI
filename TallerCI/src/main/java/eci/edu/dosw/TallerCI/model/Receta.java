package eci.edu.dosw.TallerCI.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("recetas") // nombre de la colección en MongoDB
public class Receta {



    @Id
    private String id;
    private String titulo;
    private List<String> ingredientes;
    private List<String> pasos;
    private String autor;
    private TipoAutor tipoAutor;
    private Integer temporada;

}