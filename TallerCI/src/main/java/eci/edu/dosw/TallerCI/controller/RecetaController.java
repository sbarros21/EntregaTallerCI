package eci.edu.dosw.TallerCI.controller;

import eci.edu.dosw.TallerCI.model.DTOS.RecetaDTO;
import eci.edu.dosw.TallerCI.model.TipoAutor;
import eci.edu.dosw.TallerCI.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")

public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    @PostMapping
    public ResponseEntity<RecetaDTO> registrar(@RequestBody RecetaDTO recetaDTO) {
        RecetaDTO nueva = recetaService.registrar(recetaDTO);
        return ResponseEntity.ok(nueva);
    }

    @GetMapping
    public ResponseEntity<List<RecetaDTO>> obtenerTodas() {
        return ResponseEntity.ok(recetaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable String id) {
        return recetaService.obtenerPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body("Receta no encontrada"));
    }

    @GetMapping("/tipo/{tipoAutor}")
    public ResponseEntity<List<RecetaDTO>> obtenerPorTipo(@PathVariable String tipoAutor) {
        TipoAutor tipo = TipoAutor.valueOf(tipoAutor.toUpperCase());
        return ResponseEntity.ok(recetaService.obtenerPorTipo(tipo));
    }

    @GetMapping("/temporada/{temporada}")
    public ResponseEntity<List<RecetaDTO>> obtenerPorTemporada(@PathVariable Integer temporada) {
        return ResponseEntity.ok(recetaService.obtenerPorTemporada(temporada));
    }

    @GetMapping("/buscar/{ingrediente}")
    public ResponseEntity<List<RecetaDTO>> buscarPorIngrediente(@PathVariable String ingrediente) {
        return ResponseEntity.ok(recetaService.buscarPorIngrediente(ingrediente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecetaDTO> actualizar(@PathVariable String id, @RequestBody RecetaDTO recetaDTO) {
        return ResponseEntity.ok(recetaService.actualizar(id, recetaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable String id) {
        recetaService.eliminar(id);
        return ResponseEntity.ok("Receta eliminada correctamente");
    }
}
