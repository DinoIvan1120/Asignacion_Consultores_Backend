package com.IngSoftware.proyectosgr.controller;

import com.IngSoftware.proyectosgr.domain.dto.Asignacion.AsignacionResource;
import com.IngSoftware.proyectosgr.domain.dto.Asignacion.UpdateAsignacionResource;
import com.IngSoftware.proyectosgr.service.AsignacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.IngSoftware.proyectosgr.service.impl.ExcelExportService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/api/v1/asignaciones")
public class AsignacionRest {

    private static final Logger logger = LoggerFactory.getLogger(AsignacionRest.class);


    @Autowired
    private AsignacionService asignacionService;

    @Autowired
    private ExcelExportService excelExportService;


    // ============================================================
    // ENDPOINT DE BÚSQUEDA CON FILTROS
    // ============================================================

    /**
     * ✅ Búsqueda de asignaciones con filtros múltiples
     * Endpoint: GET /api/v1/asignaciones/search
     *
     * Ejemplos de uso:
     * - Buscar por fechas: /search?fechaInicio=2025-01-01&fechaFin=2025-12-31
     * - Buscar por cliente: /search?idUsuario=123
     * - Buscar por consultor: /search?nombreConsultor=Juan
     * - Combinar filtros: /search?fechaInicio=2025-01-01&nombreConsultor=Maria&page=0&size=10
     */
    @Operation(
            summary = "Buscar asignaciones con filtros",
            description = "Permite filtrar asignaciones del coordinador autenticado usando múltiples criterios opcionales. " +
                    "Todos los parámetros de filtro son opcionales y se pueden combinar. " +
                    "Retorna una página con las asignaciones completas (requerimiento + actividades)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asignaciones filtradas obtenidas exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    @GetMapping("/search")
    public ResponseEntity<Page<AsignacionResource>> searchAsignaciones(
            @Parameter(description = "Fecha de inicio del requerimiento (formato: yyyy-MM-dd)", example = "2025-01-01")
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            Date fechaInicio,

            @Parameter(description = "Fecha de fin del requerimiento (formato: yyyy-MM-dd)", example = "2025-12-31")
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            Date fechaFin,

            @Parameter(description = "ID del cliente (usuario del requerimiento)", example = "123")
            @RequestParam(required = false)
            Integer idUsuario,

            @Parameter(description = "Nombre del consultor (busca en el título del requerimiento)", example = "Juan")
            @RequestParam(required = false)
            String nombreConsultor,

            @Parameter(description = "Parámetros de paginación (page, size, sort)", hidden = true)
            Pageable pageable) {

        Page<AsignacionResource> asignaciones = asignacionService.searchAsignacionesByFilters(
                fechaInicio,
                fechaFin,
                idUsuario,
                nombreConsultor,
                pageable
        );

        return new ResponseEntity<>(asignaciones, HttpStatus.OK);
    }

    // ============================================================
    // 🔥 NUEVOS ENDPOINTS DE DESCARGA DE EXCEL
    // ============================================================

    /**
     * ✅ NUEVO: Descargar Excel con TODAS las asignaciones (sin filtros)
     * Ejemplo: GET /api/v1/asignaciones/download/excel
     */
    @Operation(
            summary = "Descargar Excel con todas las asignaciones",
            description = "Descarga un archivo Excel con TODAS las asignaciones del coordinador autenticado. " +
                    "No aplica filtros, incluye todo el reporte completo."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Excel generado exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "500", description = "Error al generar el archivo Excel")
    })
    @GetMapping("/download/excel")
    public ResponseEntity<ByteArrayResource> downloadExcelCompleto() {
        try {
            logger.info("Iniciando descarga de Excel completo (sin filtros)");

            // Obtener todas las asignaciones sin paginación
            List<AsignacionResource> asignaciones =
                    asignacionService.obtenerTodasAsignacionesSinPaginacion();

            // Generar Excel
            ByteArrayOutputStream excelStream =
                    excelExportService.exportarAsignacionesAExcel(asignaciones);

            // Preparar respuesta
            ByteArrayResource resource = new ByteArrayResource(excelStream.toByteArray());

            // Nombre del archivo con timestamp
            String fileName = String.format("Informe_Actividades_Completo_%s.xlsx",
                    new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));

            logger.info("Excel completo generado exitosamente: {}", fileName);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .contentLength(resource.contentLength())
                    .body(resource);

        } catch (IOException e) {
            logger.error("Error al generar Excel completo", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * ✅ NUEVO: Descargar Excel con FILTROS aplicados
     * Ejemplo: GET /api/v1/asignaciones/download/excel/filtered?fechaInicio=2025-01-01&idUsuario=123
     */
    @Operation(
            summary = "Descargar Excel con asignaciones filtradas",
            description = "Descarga un archivo Excel con las asignaciones filtradas según los criterios especificados. " +
                    "Todos los parámetros de filtro son opcionales y se pueden combinar. " +
                    "Si no se envían filtros, descargará todas las asignaciones."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Excel filtrado generado exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Error al generar el archivo Excel")
    })
    @GetMapping("/download/excel/filtered")
    public ResponseEntity<ByteArrayResource> downloadExcelFiltrado(
            @Parameter(description = "Fecha de inicio del requerimiento (formato: yyyy-MM-dd)", example = "2025-01-01")
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            Date fechaInicio,

            @Parameter(description = "Fecha de fin del requerimiento (formato: yyyy-MM-dd)", example = "2025-12-31")
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            Date fechaFin,

            @Parameter(description = "ID del cliente (usuario del requerimiento)", example = "123")
            @RequestParam(required = false)
            Integer idUsuario,

            @Parameter(description = "Nombre del consultor (busca en el título del requerimiento)", example = "Juan")
            @RequestParam(required = false)
            String nombreConsultor) {

        try {
            logger.info("Iniciando descarga de Excel filtrado - fechaInicio: {}, fechaFin: {}, idUsuario: {}, nombreConsultor: {}",
                    fechaInicio, fechaFin, idUsuario, nombreConsultor);

            // Obtener asignaciones filtradas sin paginación
            List<AsignacionResource> asignaciones =
                    asignacionService.searchAsignacionesByFiltersSinPaginacion(
                            fechaInicio,
                            fechaFin,
                            idUsuario,
                            nombreConsultor
                    );

            // Generar Excel
            ByteArrayOutputStream excelStream =
                    excelExportService.exportarAsignacionesAExcel(asignaciones);

            // Preparar respuesta
            ByteArrayResource resource = new ByteArrayResource(excelStream.toByteArray());

            // Nombre del archivo con timestamp y descripción de filtros
            StringBuilder fileNameBuilder = new StringBuilder("Informe_Actividades_Filtrado");

            if (fechaInicio != null || fechaFin != null) {
                fileNameBuilder.append("_Fechas");
            }
            if (idUsuario != null) {
                fileNameBuilder.append("_Cliente");
            }
            if (nombreConsultor != null && !nombreConsultor.isEmpty()) {
                fileNameBuilder.append("_Consultor");
            }

            String fileName = String.format("%s_%s.xlsx",
                    fileNameBuilder.toString(),
                    new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));

            logger.info("Excel filtrado generado exitosamente: {} ({} registros)",
                    fileName, asignaciones.size());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .contentLength(resource.contentLength())
                    .body(resource);

        } catch (IOException e) {
            logger.error("Error al generar Excel filtrado", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /**
     * ✅ NUEVO: GET - Obtener todas las asignaciones con paginación
     * Ejemplo: GET /api/v1/asignaciones/page?page=0&size=10&sort=idRequerimiento,desc
     */
    @Operation(
            summary = "Obtener todas las asignaciones del coordinador con paginación",
            description = "Retorna una página con todas las asignaciones (requerimiento + actividades) " +
                    "del coordinador autenticado. Ideal para listar en tablas con scroll infinito."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de asignaciones obtenida exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @GetMapping("/page")
    public ResponseEntity<Page<AsignacionResource>> obtenerTodasAsignaciones(Pageable pageable) {
        Page<AsignacionResource> asignaciones = asignacionService.obtenerTodasAsignaciones(pageable);
        return new ResponseEntity<>(asignaciones, HttpStatus.OK);
    }

    /**
     * GET - Obtener requerimiento completo con sus actividades
     * Ejemplo: GET /api/v1/asignaciones/50600
     */
    @Operation(
            summary = "Obtener asignación completa",
            description = "Retorna el requerimiento con todas sus actividades asociadas en un solo objeto"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asignación obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "Requerimiento no encontrado")
    })
    @GetMapping("/{idRequerimiento}")
    public ResponseEntity<AsignacionResource> obtenerAsignacionCompleta(
            @PathVariable Integer idRequerimiento) {

        AsignacionResource asignacion =
                asignacionService.obtenerAsignacionCompleta(idRequerimiento);

        return new ResponseEntity<>(asignacion, HttpStatus.OK);
    }

    /**
     * PUT - Actualizar requerimiento y sus actividades
     * Ejemplo: PUT /api/v1/asignaciones/50600
     */
    @Operation(
            summary = "Actualizar asignación completa",
            description = "Actualiza el requerimiento y sus actividades en una sola transacción. " +
                    "Todos los campos son opcionales. Solo se actualizarán los campos que se envíen."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asignación actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Requerimiento o actividad no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos - Revise que los campos cumplan las validaciones")
    })
    @PutMapping("/{idRequerimiento}")
    public ResponseEntity<AsignacionResource> actualizarAsignacionCompleta(
            @PathVariable Integer idRequerimiento,
            @Valid @RequestBody UpdateAsignacionResource actualizacion) {

        AsignacionResource asignacionActualizada =
                asignacionService.actualizarAsignacionCompleta(idRequerimiento, actualizacion);

        return new ResponseEntity<>(asignacionActualizada, HttpStatus.OK);
    }
}
