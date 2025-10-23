package eci.edu.dosw.TallerCI.service;

import eci.edu.dosw.TallerCI.model.Receta;
import eci.edu.dosw.TallerCI.model.TipoAutor;
import eci.edu.dosw.TallerCI.model.DTOS.RecetaDTO;
import eci.edu.dosw.TallerCI.repository.RecetaRepository;
import eci.edu.dosw.TallerCI.util.RecetaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecetaServiceTest {

    @Mock
    private RecetaRepository recetaRepository;

    @Mock
    private RecetaMapper recetaMapper; // mock del mapper porque el service lo usa

    @InjectMocks
    private RecetaService recetaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // a. Validar que se pueda registrar una receta
    @Test
    void registrarRecetaDebeGuardarCorrectamente() {
        // DTO de entrada (lo que recibe el servicio)
        RecetaDTO recetaDTO = new RecetaDTO(
                "Ajiaco",
                "Carlos",
                TipoAutor.PARTICIPANTE,
                List.of("Papa", "Pollo"),
                List.of("Cocinar todo"),
                2
        );

        // Entidad que resulta de mapear el DTO
        Receta recetaEntidad = new Receta(
                null,
                "Ajiaco",
                List.of("Papa", "Pollo"),
                List.of("Cocinar todo"),
                "Carlos",
                TipoAutor.PARTICIPANTE,
                2
        );

        // DTO esperado tras guardar (normalmente igual al original, sin id en DTO por diseño actual)
        RecetaDTO recetaDTOGuardada = recetaDTO;

        // Mocks del mapper y repositorio
        when(recetaMapper.toReceta(recetaDTO)).thenReturn(recetaEntidad);
        when(recetaRepository.save(any(Receta.class))).thenReturn(recetaEntidad);
        when(recetaMapper.toRecetaDTO(recetaEntidad)).thenReturn(recetaDTOGuardada);

        // Ejecutar
        RecetaDTO resultado = recetaService.registrar(recetaDTO);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals("Ajiaco", resultado.getTitulo());
        verify(recetaMapper, times(1)).toReceta(recetaDTO);
        verify(recetaRepository, times(1)).save(any(Receta.class));
        verify(recetaMapper, times(1)).toRecetaDTO(recetaEntidad);
    }

    // b. Validar que la búsqueda por ingrediente devuelva resultados correctos
    @Test
    void buscarPorIngredienteDebeDevolverRecetasQueContenganElIngrediente() {
        // Entidades en el repositorio
        Receta r1 = new Receta("1", "Arepa", List.of("Maíz", "Queso"), List.of("Asar"), "Laura", TipoAutor.TELEVIDENTE, null);
        Receta r2 = new Receta("2", "Empanada", List.of("Papa", "Carne"), List.of("Freír"), "Sofía", TipoAutor.TELEVIDENTE, null);

        // DTOs devueltos por el mapper
        RecetaDTO d1 = new RecetaDTO("Arepa", "Laura", TipoAutor.TELEVIDENTE, List.of("Maíz", "Queso"), List.of("Asar"), null);
        RecetaDTO d2 = new RecetaDTO("Empanada", "Sofía", TipoAutor.TELEVIDENTE, List.of("Papa", "Carne"), List.of("Freír"), null);

        // Mocks
        when(recetaRepository.findAll()).thenReturn(List.of(r1, r2));
        when(recetaMapper.toRecetaDTO(r1)).thenReturn(d1);
        when(recetaMapper.toRecetaDTO(r2)).thenReturn(d2);

        // Ejecutar
        List<RecetaDTO> resultado = recetaService.buscarPorIngrediente("queso");

        // Verificaciones
        assertEquals(1, resultado.size());
        assertEquals("Arepa", resultado.get(0).getTitulo());
        verify(recetaRepository, times(1)).findAll();

        // Solo se debería mapear la receta que contenía el ingrediente
        verify(recetaMapper, times(1)).toRecetaDTO(r1);
        verify(recetaMapper, never()).toRecetaDTO(r2);
    }

        // c. Validar que se devuelva error si se consulta una receta inexistente
    @Test
    void obtenerPorIdDebeRetornarVacioSiNoExiste() {
        String id = "999";
        when(recetaRepository.findById(id)).thenReturn(Optional.empty());

        Optional<RecetaDTO> resultado = recetaService.obtenerPorId(id);

        assertTrue(resultado.isEmpty());
        verify(recetaRepository, times(1)).findById(id);
        // no debe invocar el mapper porque no hay entidad
        verifyNoInteractions(recetaMapper);
    }
}


