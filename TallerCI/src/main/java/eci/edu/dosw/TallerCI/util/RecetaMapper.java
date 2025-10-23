package eci.edu.dosw.TallerCI.util;

import eci.edu.dosw.TallerCI.model.Receta;
import eci.edu.dosw.TallerCI.model.DTOS.RecetaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecetaMapper {

    RecetaDTO toRecetaDTO(Receta receta);

    Receta toReceta(RecetaDTO recetaDTO);
}
