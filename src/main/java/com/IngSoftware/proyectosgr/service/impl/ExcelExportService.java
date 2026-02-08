package com.IngSoftware.proyectosgr.service.impl;

import com.IngSoftware.proyectosgr.domain.dto.ActividadPlanRealConsultor.ActividadPlanRealConsultorResource;
import com.IngSoftware.proyectosgr.domain.dto.Asignacion.AsignacionResource;
import com.IngSoftware.proyectosgr.domain.dto.Requerimiento.RequerimientoResource;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ExcelExportService {

    private static final Logger logger = LoggerFactory.getLogger(ExcelExportService.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private static final int MAX_CELL_LENGTH = 32767; // ✅ AGREGAR ESTA CONSTANTE


    // ✅ AGREGAR ESTE MÉTODO
    /**
     * Sanitiza el valor de una celda para no exceder el límite de Excel
     */
    private String sanitizeCellValue(String value) {
        if (value == null || value.isEmpty()) {
            return "-";
        }

        if (value.length() > MAX_CELL_LENGTH) {
            logger.warn("Campo excede límite de Excel. Truncando de {} a {} caracteres",
                    value.length(), MAX_CELL_LENGTH);
            return value.substring(0, MAX_CELL_LENGTH - 50) + "... [TEXTO TRUNCADO]";
        }

        return value;
    }

    /**
     * Genera un archivo Excel con todas las asignaciones y sus actividades
     *
     * @param asignaciones Lista de asignaciones a exportar
     * @return ByteArrayOutputStream con el archivo Excel generado
     * @throws IOException Si hay error al generar el archivo
     */
    public ByteArrayOutputStream exportarAsignacionesAExcel(List<AsignacionResource> asignaciones)
            throws IOException {

        logger.info("Iniciando generación de Excel con {} asignaciones", asignaciones.size());

        // Crear workbook y hoja
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Informe de Actividades");

        // Crear estilos
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle dateStyle = createDateStyle(workbook);
        CellStyle currencyStyle = createCurrencyStyle(workbook);
        CellStyle percentStyle = createPercentStyle(workbook);

        // Crear cabecera
        createHeader(sheet, headerStyle);

        // Llenar datos
        int rowNum = 1;
        int posicion = 1;

        for (AsignacionResource asignacion : asignaciones) {
            RequerimientoResource req = asignacion.getRequerimiento();
            List<ActividadPlanRealConsultorResource> actividades =
                    asignacion.getActividadPlanRealConsultor();

            // Si no tiene actividades, crear una fila con solo datos del requerimiento
            if (actividades == null || actividades.isEmpty()) {
                Row row = sheet.createRow(rowNum++);
                fillRequerimientoData(row, req, null, posicion++, dateStyle, currencyStyle, percentStyle);
            } else {
                // Crear una fila por cada actividad
                for (ActividadPlanRealConsultorResource actividad : actividades) {
                    Row row = sheet.createRow(rowNum++);
                    fillRequerimientoData(row, req, actividad, posicion++, dateStyle, currencyStyle, percentStyle);
                }
            }
        }

        // Ajustar ancho de columnas
        autoSizeColumns(sheet, 30);

        // Escribir a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        logger.info("Excel generado exitosamente con {} filas", rowNum - 1);

        return outputStream;
    }

    /**
     * Crea la fila de encabezados
     */
    private void createHeader(Sheet sheet, CellStyle headerStyle) {
        Row headerRow = sheet.createRow(0);

        String[] headers = {
                "Posición",                      // 0
                "Código Requerimiento",          // 1
                "Título Requerimiento",          // 2
                "Fecha Creación",                // 3
                "Fecha Envío",                   // 4
                "Cliente",                       // 5
                "Empresa",                       // 6
                "RUC Empresa",                   // 7
                "Estado",                        // 8
                "Prioridad",                     // 9
                "Detalle Requerimiento",         // 10
                "Estimación",                    // 11
                "Detalle Asignación",            // 12
                "Orden Compra",                  // 13
                "Tipo Facturación",              // 14
                "País",                          // 15
                "Consultor",                     // 16
                "Email Consultor",               // 17
                "Tipo Actividad",                // 18
                "Descripción Actividad",         // 19
                "Fecha Inicio Actividad",        // 20
                "Fecha Fin Actividad",           // 21
                "Tiempo Regular (hrs)",          // 22
                "Tiempo Extra (hrs)",            // 23
                "Costo",                         // 24
                "Moneda",                        // 25
                "Facturable",                    // 26
                "% Avance",                      // 27
                "Escala Tiempo",                 // 28
                "Categoría Actividad"            // 29
        };

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    /**
     * Llena una fila con datos del requerimiento y actividad
     */
    private void fillRequerimientoData(
            Row row,
            RequerimientoResource req,
            ActividadPlanRealConsultorResource actividad,
            int posicion,
            CellStyle dateStyle,
            CellStyle currencyStyle,
            CellStyle percentStyle) {

        int colNum = 0;

        // Posición
        row.createCell(colNum++).setCellValue(posicion);

        // Código Requerimiento
        row.createCell(colNum++).setCellValue(
                req.getCodRequerimiento() != null ? req.getCodRequerimiento() : "-"
        );

        // Título Requerimiento
        row.createCell(colNum++).setCellValue(
                req.getTitulo() != null ? req.getTitulo() : "-"
        );

        // Fecha Creación
        Cell fechaCreacionCell = row.createCell(colNum++);
        if (req.getFechaRegistro() != null) {
            fechaCreacionCell.setCellValue(dateFormat.format((req.getFechaRegistro())));
        } else {
            fechaCreacionCell.setCellValue("-");
        }

        // Fecha Envío
        Cell fechaEnvioCell = row.createCell(colNum++);
        if (req.getFechaEnvio() != null) {
            fechaEnvioCell.setCellValue(dateFormat.format((req.getFechaEnvio())));
        } else {
            fechaEnvioCell.setCellValue("-");
        }

        // Cliente
        String nombreCliente = "-";
        if (req.getUsuario() != null) {
            nombreCliente = String.format("%s %s %s",
                    req.getUsuario().getNombres() != null ? req.getUsuario().getNombres() : "",
                    req.getUsuario().getApepaterno() != null ? req.getUsuario().getApepaterno() : "",
                    req.getUsuario().getApematerno() != null ? req.getUsuario().getApematerno() : ""
            ).trim();
        }
        row.createCell(colNum++).setCellValue(nombreCliente);

        // Empresa
        row.createCell(colNum++).setCellValue(
                req.getEmpresa() != null && req.getEmpresa().getNombrecomercial() != null
                        ? req.getEmpresa().getNombrecomercial()
                        : "-"
        );

        // RUC Empresa
        row.createCell(colNum++).setCellValue(
                req.getEmpresa() != null && req.getEmpresa().getRuc() != null
                        ? req.getEmpresa().getRuc()
                        : "-"
        );

        // Estado
        row.createCell(colNum++).setCellValue(
                req.getEstadoRequerimiento() != null && req.getEstadoRequerimiento().getDescripcion() != null
                        ? req.getEstadoRequerimiento().getDescripcion()
                        : "-"
        );

        // Prioridad
        row.createCell(colNum++).setCellValue(
                req.getPrioridad() != null && req.getPrioridad().getDescripcion() != null
                        ? req.getPrioridad().getDescripcion()
                        : "-"
        );

        // Detalle Requerimiento
        //row.createCell(colNum++).setCellValue(
                //req.getDetalle() != null ? req.getDetalle() : "-"
        //);

        // ✅ DESPUÉS
        row.createCell(colNum++).setCellValue(
                sanitizeCellValue(req.getDetalle())
        );

        // Estimación
        //row.createCell(colNum++).setCellValue(
                //req.getDescripcionEstimacion() != null ? req.getDescripcionEstimacion() : "-"
        //);

        // ✅ DESPUÉS
        row.createCell(colNum++).setCellValue(
                sanitizeCellValue(req.getDescripcionEstimacion())
        );

        // Detalle Asignación
        //row.createCell(colNum++).setCellValue(
                //req.getDetalleAsignacion() != null ? req.getDetalleAsignacion() : "-"
        //);

        // ✅ DESPUÉS
        row.createCell(colNum++).setCellValue(
                sanitizeCellValue(req.getDetalleAsignacion())
        );

        // Orden Compra
        row.createCell(colNum++).setCellValue(
                req.getOrdenCompra() != null ? req.getOrdenCompra() : "-"
        );

        // Tipo Facturación
        row.createCell(colNum++).setCellValue(
                req.getTipoFacturacion() != null ? req.getTipoFacturacion() : "-"
        );

        // País
        row.createCell(colNum++).setCellValue(
                req.getIdPais() != null ? req.getIdPais().toString() : "-"
        );

        // ============================================================
        // DATOS DE LA ACTIVIDAD (si existe)
        // ============================================================
        if (actividad != null) {
            // Consultor
            String nombreConsultor = "-";
            if (actividad.getUsuario() != null) {
                nombreConsultor = String.format("%s %s %s",
                        actividad.getUsuario().getNombres() != null ? actividad.getUsuario().getNombres() : "",
                        actividad.getUsuario().getApepaterno() != null ? actividad.getUsuario().getApepaterno() : "",
                        actividad.getUsuario().getApematerno() != null ? actividad.getUsuario().getApematerno() : ""
                ).trim();
            }
            row.createCell(colNum++).setCellValue(nombreConsultor);

            // Email Consultor
            row.createCell(colNum++).setCellValue(
                    actividad.getUsuario() != null && actividad.getUsuario().getCorreo() != null
                            ? actividad.getUsuario().getCorreo()
                            : "-"
            );

            // Tipo Actividad
            row.createCell(colNum++).setCellValue(
                    actividad.getTipoActividad() != null && actividad.getTipoActividad().getDescripcion() != null
                            ? actividad.getTipoActividad().getDescripcion()
                            : "-"
            );

            // Descripción Actividad
            //row.createCell(colNum++).setCellValue(
                    //actividad.getDescripcion() != null ? actividad.getDescripcion() : "-"
            //);

            // ✅ DESPUÉS
            // Descripción Actividad
            row.createCell(colNum++).setCellValue(
                    sanitizeCellValue(actividad.getDescripcion())
            );

            // Fecha Inicio Actividad
            Cell fechaInicioActCell = row.createCell(colNum++);
            if (actividad.getFechainicio() != null) {
                fechaInicioActCell.setCellValue(dateFormat.format((actividad.getFechainicio())));
            } else {
                fechaInicioActCell.setCellValue("-");
            }

            // Fecha Fin Actividad
            Cell fechaFinActCell = row.createCell(colNum++);
            if (actividad.getFechafin() != null) {
                fechaFinActCell.setCellValue(dateFormat.format((actividad.getFechafin())));
            } else {
                fechaFinActCell.setCellValue("-");
            }

            // Tiempo Regular
            if (actividad.getTiemporegular() != null) {
                row.createCell(colNum++).setCellValue(actividad.getTiemporegular());
            } else {
                row.createCell(colNum++).setCellValue("-");
            }

            // Tiempo Extra
            if (actividad.getTiempoextra() != null) {
                row.createCell(colNum++).setCellValue(actividad.getTiempoextra());
            } else {
                row.createCell(colNum++).setCellValue("-");
            }

            // Costo
            Cell costoCell = row.createCell(colNum++);
            if (actividad.getCosto() != null) {
                costoCell.setCellValue(actividad.getCosto());
                costoCell.setCellStyle(currencyStyle);
            } else {
                costoCell.setCellValue("-");
            }

            // Moneda
            row.createCell(colNum++).setCellValue(
                    actividad.getMoneda() != null && actividad.getMoneda().getCodigo() != null
                            ? actividad.getMoneda().getCodigo()
                            : "-"
            );

            // Facturable
            row.createCell(colNum++).setCellValue(
                    actividad.getFacturable() != null && actividad.getFacturable() ? "Sí" : "No"
            );

            // % Avance
            Cell avanceCell = row.createCell(colNum++);
            if (actividad.getPorcentajeAvance() != null) {
                avanceCell.setCellValue(actividad.getPorcentajeAvance() / 100.0);
                avanceCell.setCellStyle(percentStyle);
            } else {
                avanceCell.setCellValue("-");
            }

            // Escala Tiempo
            row.createCell(colNum++).setCellValue(
                    actividad.getEscalaTiempo() != null && actividad.getEscalaTiempo().getDescripcion() != null
                            ? actividad.getEscalaTiempo().getDescripcion()
                            : "-"
            );

            // Categoría Actividad
            row.createCell(colNum++).setCellValue(
                    actividad.getCategoriaActividad() != null && actividad.getCategoriaActividad().getNombre() != null
                            ? actividad.getCategoriaActividad().getNombre()
                            : "-"
            );

        } else {
            // Si no hay actividad, llenar con guiones
            for (int i = 0; i < 14; i++) {
                row.createCell(colNum++).setCellValue("-");
            }
        }
    }

    /**
     * Crea el estilo para las cabeceras
     */
    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        // Fondo azul
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Fuente blanca y negrita
        Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);

        // Alineación
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        // Bordes
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    /**
     * Crea el estilo para fechas
     */
    private CellStyle createDateStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("dd/MM/yyyy"));
        return style;
    }

    /**
     * Crea el estilo para moneda
     */
    private CellStyle createCurrencyStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));
        return style;
    }

    /**
     * Crea el estilo para porcentajes
     */
    private CellStyle createPercentStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));
        return style;
    }

    /**
     * Ajusta automáticamente el ancho de las columnas

    private void autoSizeColumns(Sheet sheet, int numberOfColumns) {
        for (int i = 0; i < numberOfColumns; i++) {
            sheet.autoSizeColumn(i);
            // Agregar un poco más de espacio
            int currentWidth = sheet.getColumnWidth(i);
            sheet.setColumnWidth(i, currentWidth + 1000);
        }
    }
     */

    private void autoSizeColumns(Sheet sheet, int numberOfColumns) {
        // Ancho máximo permitido por Excel (en unidades de 1/256 de carácter)
        final int MAX_COLUMN_WIDTH = 255 * 256;  // 255 caracteres
        // Ancho razonable para columnas de texto largo (100 caracteres)
        final int REASONABLE_WIDTH = 100 * 256;

        for (int i = 0; i < numberOfColumns; i++) {
            try {
                sheet.autoSizeColumn(i);

                // Obtener el ancho calculado
                int currentWidth = sheet.getColumnWidth(i);

                // Agregar un poco más de espacio
                int newWidth = currentWidth + 1000;

                // Limitar al ancho máximo permitido
                if (newWidth > MAX_COLUMN_WIDTH) {
                    newWidth = REASONABLE_WIDTH;  // Usar un ancho razonable
                    logger.warn("Columna {} excede ancho máximo. Limitando a {} caracteres", i, 100);
                }

                sheet.setColumnWidth(i, newWidth);

            } catch (IllegalArgumentException e) {
                // Si falla, establecer un ancho por defecto
                logger.warn("Error al ajustar columna {}. Usando ancho por defecto: {}", i, e.getMessage());
                sheet.setColumnWidth(i, REASONABLE_WIDTH);
            }
        }
    }


}
