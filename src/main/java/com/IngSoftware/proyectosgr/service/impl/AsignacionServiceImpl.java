package com.IngSoftware.proyectosgr.service.impl;

import com.IngSoftware.proyectosgr.config.exception.ResourceNotFoundException;
import com.IngSoftware.proyectosgr.domain.dto.ActividadPlanRealConsultor.ActividadPlanRealConsultorResource;
import com.IngSoftware.proyectosgr.domain.dto.Asignacion.AsignacionResource;
import com.IngSoftware.proyectosgr.domain.dto.Asignacion.UpdateAsignacionResource;
import com.IngSoftware.proyectosgr.domain.dto.Requerimiento.RequerimientoResource;
import com.IngSoftware.proyectosgr.domain.mapping.ActividadPlanRealConsultorMapper;
import com.IngSoftware.proyectosgr.domain.mapping.AsignacionesMapper;
import com.IngSoftware.proyectosgr.domain.mapping.RequerimientoMapper;
import com.IngSoftware.proyectosgr.domain.model.*;
import com.IngSoftware.proyectosgr.domain.repository.*;
import com.IngSoftware.proyectosgr.service.AsignacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Objects;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsignacionServiceImpl implements AsignacionService {

    private static final Logger logger = LoggerFactory.getLogger(AsignacionServiceImpl.class);

    @Autowired
    private RequerimientoRepository requerimientoRepository;

    @Autowired
    private ActividadPlanRealConsultorRepository actividadRepository;

    @Autowired
    private TipoActividadRepository tipoActividadRepository;

    @Autowired
    private RequerimientoMapper requerimientoMapper;

    @Autowired
    private ActividadPlanRealConsultorMapper actividadMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ✅ NUEVO: Inyectar el mapper
    @Autowired
    private AsignacionesMapper asignacionMapper;

    @Autowired
    private EmpresaRepository  empresaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * ✅ REFACTORIZADO: Buscar asignaciones con filtros usando el mapper
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AsignacionResource> searchAsignacionesByFilters(
            Date fechaInicio,
            Date fechaFin,
            Integer idUsuario,
            String nombreConsultor,
            Integer idEmpresa,
            String codRequerimiento,
            Integer idRequerimiento,
            Integer idEstadoRequerimiento,
            Pageable pageable) {

        Integer idCoordinador = getAuthenticatedCoordinadorId();

        // 🔥 LOGS DETALLADOS PARA DEBUGGING
        logger.info("============================================");
        logger.info("INICIO - Filtrando asignaciones del coordinador: {}", idCoordinador);
        logger.info("Filtros recibidos:");
        logger.info("  - fechaInicio: {}", fechaInicio != null ? fechaInicio.toString() + " (" + fechaInicio.getTime() + ")" : "null");
        logger.info("  - fechaFin: {}", fechaFin != null ? fechaFin.toString() + " (" + fechaFin.getTime() + ")" : "null");
        logger.info("  - idUsuario: {}", idUsuario);
        logger.info("  - nombreConsultor: {}", nombreConsultor);
        logger.info("  - idEmpresa: {}", idEmpresa);
        logger.info("  - codRequerimiento: {}", codRequerimiento);
        logger.info("============================================");


        logger.info("Filtrando asignaciones del coordinador {} con filtros: " +
                        "fechaInicio={}, fechaFin={}, idUsuario={}, nombreConsultor={}",
                idCoordinador, fechaInicio, fechaFin, idUsuario, nombreConsultor);

        // ============================================================
        // 1. Buscar requerimientos con filtros
        // ============================================================
        Page<Requerimiento> requerimientos = requerimientoRepository.findByMultipleFilters(
                idCoordinador,
                idEmpresa,
                codRequerimiento,            // codRequerimiento
                fechaInicio,
                fechaFin,
                idUsuario,
                idRequerimiento,              // idRequerimiento
                idEstadoRequerimiento,              // idEstadoRequerimiento
                nombreConsultor,
                pageable
        );

        logger.info("Se encontraron {} requerimientos filtrados en la página {} de {}",
                requerimientos.getNumberOfElements(),
                requerimientos.getNumber(),
                requerimientos.getTotalPages());

        // 🔥 AGREGAR ESTE BLOQUE NUEVO
        logger.info("============================================");
        logger.info("Fechas de envío de los requerimientos encontrados:");
        requerimientos.getContent().forEach(req -> {
            Date fechaEnvio = req.getFechaEnvio() != null ? (req.getFechaEnvio()) : null;
            logger.info("  - Requerimiento {}: fechaEnvio = {} (timestamp: {})",
                    req.getIdRequerimiento(),
                    fechaEnvio != null ? fechaEnvio.toString() : "null",
                    req.getFechaEnvio());
        });
        logger.info("============================================");

        // ============================================================
        // 2. ✅ USAR EL MAPPER para convertir a AsignacionResource
        // ============================================================
        List<AsignacionResource> asignaciones = asignacionMapper.modelListToList(
                requerimientos.getContent()
        );

        // ============================================================
        // 3. Retornar Page<AsignacionResource>
        // ============================================================
        Page<AsignacionResource> pageAsignaciones = new PageImpl<>(
                asignaciones,
                pageable,
                requerimientos.getTotalElements()
        );

        logger.info("Retornando página de asignaciones filtradas: {} elementos de {} totales",
                pageAsignaciones.getNumberOfElements(),
                pageAsignaciones.getTotalElements());

        return pageAsignaciones;
    }

    /**
     * ✅ NUEVO: Obtener todas las asignaciones SIN paginación (para descarga completa)
     */
    @Override
    @Transactional(readOnly = true)
    public List<AsignacionResource> obtenerTodasAsignacionesSinPaginacion() {
        Integer idCoordinador = getAuthenticatedCoordinadorId();

        logger.info("Obteniendo TODAS las asignaciones del coordinador {} (sin paginación)", idCoordinador);

        // Obtener todos los requerimientos del coordinador
        List<Requerimiento> requerimientos =
                requerimientoRepository.findByIdCoordinadorOrderByIdRequerimientoDesc(idCoordinador);

        logger.info("Se encontraron {} requerimientos totales", requerimientos.size());

        // Convertir a AsignacionResource usando el mapper
        List<AsignacionResource> asignaciones = asignacionMapper.modelListToList(requerimientos);

        logger.info("Retornando {} asignaciones completas", asignaciones.size());

        return asignaciones;
    }

    /**
     * ✅ NUEVO: Buscar asignaciones con filtros SIN paginación (para descarga con filtros)
     */
    @Override
    @Transactional(readOnly = true)
    public List<AsignacionResource> searchAsignacionesByFiltersSinPaginacion(
            Date fechaInicio,
            Date fechaFin,
            Integer idUsuario,
            String nombreConsultor) {

        Integer idCoordinador = getAuthenticatedCoordinadorId();

        logger.info("Filtrando asignaciones del coordinador {} SIN paginación con filtros: " +
                        "fechaInicio={}, fechaFin={}, idUsuario={}, nombreConsultor={}",
                idCoordinador, fechaInicio, fechaFin, idUsuario, nombreConsultor);

        // Buscar todos los requerimientos que cumplan los filtros (sin paginación)
        List<Requerimiento> requerimientos =
                requerimientoRepository.findByMultipleFiltersSinPaginacion(
                        idCoordinador,
                        null,
                        null,
                        fechaInicio,
                        fechaFin,
                        idUsuario,
                        null,
                        null,
                        nombreConsultor
                );

        logger.info("Se encontraron {} requerimientos filtrados", requerimientos.size());

        // Convertir a AsignacionResource usando el mapper
        List<AsignacionResource> asignaciones = asignacionMapper.modelListToList(requerimientos);

        logger.info("Retornando {} asignaciones filtradas", asignaciones.size());

        return asignaciones;
    }

    /**
     * Obtiene el ID del usuario coordinador autenticado
     */
    private Integer getAuthenticatedCoordinadorId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        logger.info("Obteniendo ID del coordinador autenticado: {}", username);

        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "usuario", username));

        return usuario.getIdUsuario();
    }

    /**
     * ✅ NUEVO: Obtener todas las asignaciones del coordinador con paginación
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AsignacionResource> obtenerTodasAsignaciones(Pageable pageable) {
        Integer idCoordinador = getAuthenticatedCoordinadorId();

        logger.info("Obteniendo todas las asignaciones del coordinador {} con paginación", idCoordinador);

        // ============================================================
        // 1. Obtener página de requerimientos del coordinador
        // ============================================================
        Page<Requerimiento> requerimientos =
                requerimientoRepository.findByIdCoordinadorWithRelations(idCoordinador, pageable);

        logger.info("Se encontraron {} requerimientos en la página {} de {}",
                requerimientos.getNumberOfElements(),
                requerimientos.getNumber(),
                requerimientos.getTotalPages());

        // ============================================================
        // 2. Para cada requerimiento, construir su AsignacionResource
        // ============================================================
        List<AsignacionResource> asignaciones = new ArrayList<>();

        for (Requerimiento req : requerimientos.getContent()) {
            // Obtener actividades del requerimiento
            List<ActividadesPlanRealConsultor> actividades =
                    //actividadRepository.findByIdrequerimiento(req.getIdRequerimiento());
                    actividadRepository.findByIdrequerimientoExcludeZero(req.getIdRequerimiento());

            // Mapear requerimiento
            RequerimientoResource requerimientoDTO = requerimientoMapper.toResource(req);

            // Mapear actividades
            List<ActividadPlanRealConsultorResource> actividadesDTO = actividades.stream()
                    .map(actividad -> actividadMapper.toResource(actividad))
                    .collect(Collectors.toList());

            // Crear asignación completa
            AsignacionResource asignacion = new AsignacionResource();
            asignacion.setRequerimiento(requerimientoDTO);
            asignacion.setActividadPlanRealConsultor(actividadesDTO);

            asignaciones.add(asignacion);

            logger.debug("Requerimiento {} con {} actividades agregado",
                    req.getIdRequerimiento(), actividadesDTO.size());
        }

        // ============================================================
        // 3. Crear Page<AsignacionResource> con la información de paginación
        // ============================================================
        Page<AsignacionResource> pageAsignaciones = new PageImpl<>(
                asignaciones,
                pageable,
                requerimientos.getTotalElements()
        );

        logger.info("Retornando página de asignaciones: {} elementos de {} totales",
                pageAsignaciones.getNumberOfElements(),
                pageAsignaciones.getTotalElements());

        return pageAsignaciones;
    }



    @Override
    @Transactional(readOnly = true)
    public AsignacionResource obtenerAsignacionCompleta(Integer idRequerimiento) {
        logger.info("Obteniendo asignación completa para requerimiento: {}", idRequerimiento);

        // ============================================================
        // 1. Obtener el requerimiento
        // ============================================================
        Requerimiento requerimiento = requerimientoRepository.findById(idRequerimiento)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Requerimiento", "id -> " + idRequerimiento));

        // ============================================================
        // 2. Obtener todas las actividades del requerimiento
        // ============================================================
        List<ActividadesPlanRealConsultor> actividades =
                actividadRepository.findByIdrequerimiento(idRequerimiento);

        // ============================================================
        // 3. Convertir a DTOs usando los mappers existentes
        // ============================================================
        RequerimientoResource requerimientoDTO = requerimientoMapper.toResource(requerimiento);

        List<ActividadPlanRealConsultorResource> actividadesDTO = actividades.stream()
                .map(actividad -> actividadMapper.toResource(actividad))
                .collect(Collectors.toList());

        // ============================================================
        // 4. Combinar en el DTO completo
        // ============================================================
        AsignacionResource asignacionCompleta = new AsignacionResource();
        asignacionCompleta.setRequerimiento(requerimientoDTO);
        asignacionCompleta.setActividadPlanRealConsultor(actividadesDTO);

        logger.info("Asignación completa obtenida: Requerimiento {} con {} actividades",
                idRequerimiento, actividadesDTO.size());
        entityManager.flush();  // Escribir cambios a BD
        entityManager.clear();  // Limpiar caché de Hibernate
        return asignacionCompleta;
    }

    @Override
    @Transactional
    public AsignacionResource actualizarAsignacionCompleta(
            Integer idRequerimiento,
            UpdateAsignacionResource actualizacion) {

        logger.info("Actualizando asignación completa para requerimiento: {}", idRequerimiento);

        // ============================================================
        // 1. Validar que el requerimiento existe
        // ============================================================
        Requerimiento requerimiento = requerimientoRepository.findById(idRequerimiento)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Requerimiento", "id -> " + idRequerimiento));

        // ============================================================
        // 🔥 OBTENER COSTO ACTUAL ANTES DE CUALQUIER ACTUALIZACIÓN
        // ============================================================
        Double costoActual = 0.0;
        List<ActividadesPlanRealConsultor> actividadesExistentes =
                actividadRepository.findByIdrequerimiento(idRequerimiento);

        if (!actividadesExistentes.isEmpty()) {
            costoActual = actividadesExistentes.get(0).getCosto();
            logger.info("💰 Costo actual del requerimiento: {}", costoActual);
        }

        // 🔥 VARIABLES PARA RASTREAR CAMBIOS
        boolean cambioEmpresa = false;
        boolean cambioCosto = false;
        Integer nuevaIdMoneda = null;
        Double nuevoCosto = null;

        // ============================================================
        // 2. Actualizar datos del requerimiento (si se enviaron)
        // ============================================================
        if (actualizacion.getRequerimiento() != null) {
            // Guardar idEmpresa anterior para comparar
            Integer idEmpresaAnterior = requerimiento.getIdEmpresa();

            // Actualizar campos del requerimiento
            actualizarRequerimiento(requerimiento, actualizacion.getRequerimiento());

            // 🔥 DETECTAR SI CAMBIÓ LA EMPRESA
            if(actualizacion.getRequerimiento().getIdEmpresa() != null
                    && !actualizacion.getRequerimiento().getIdEmpresa().equals(idEmpresaAnterior)){

                cambioEmpresa = true;
                logger.info("🔄 Cambio de empresa detectado: {} → {}",
                        idEmpresaAnterior, actualizacion.getRequerimiento().getIdEmpresa());

                // Obtener la nueva empresa con sus datos
                Empresa nuevaEmpresa = empresaRepository
                        .findByIdWithPaises(actualizacion.getRequerimiento().getIdEmpresa())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Empresa no encontrada",
                                "id -> " + actualizacion.getRequerimiento().getIdEmpresa()));

                // 🔥 GENERAR NUEVO CÓDIGO PARA LA NUEVA EMPRESA
                String codigoAnterior = requerimiento.getCodRequerimiento();
                String nuevoCodigo = generarCodigoRequerimiento(nuevaEmpresa, idRequerimiento);
                requerimiento.setCodRequerimiento(nuevoCodigo);

                logger.info("📝 Código actualizado: {} → {}", codigoAnterior, nuevoCodigo);

                // Actualizar país según la nueva empresa
                if(nuevaEmpresa.getPaises() != null && !nuevaEmpresa.getPaises().isEmpty()){
                    Integer idPais = nuevaEmpresa.getPaises().get(0).getId();
                    requerimiento.setIdPais(idPais);
                    logger.info("🌍 Nuevo país de requerimiento: {}", idPais);
                } else {
                    requerimiento.setIdPais(173); // País por defecto
                    logger.warn("⚠️ Empresa sin países, usando país por defecto: 173");
                }

                // 🔥 GUARDAR LA NUEVA MONEDA
                nuevaIdMoneda = nuevaEmpresa.getIdmoneda() != null ? nuevaEmpresa.getIdmoneda() : 1;
            }

            requerimientoRepository.save(requerimiento);
            logger.info("✅ Requerimiento {} actualizado exitosamente", idRequerimiento);
        }

        // ============================================================
        // 3. Actualizar actividades Y DETECTAR CAMBIO DE COSTO
        // ============================================================
        if (actualizacion.getActividades() != null && !actualizacion.getActividades().isEmpty()) {

            for (UpdateAsignacionResource.ActividadUpdateDTO actividadUpdate :
                    actualizacion.getActividades()) {

                if (actividadUpdate.getId() != null) {
                    // Buscar la actividad
                    ActividadesPlanRealConsultor actividad = actividadRepository
                            .findById(actividadUpdate.getId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Actividad", "id -> " + actividadUpdate.getId()));

                    // Validar que la actividad pertenece al requerimiento
                    if (!actividad.getIdrequerimiento().equals(idRequerimiento)) {
                        throw new IllegalArgumentException(
                                "La actividad " + actividadUpdate.getId() +
                                        " no pertenece al requerimiento " + idRequerimiento);
                    }

                    // 🔥 DETECTAR SI CAMBIÓ EL COSTO
                    if (actividadUpdate.getCosto() != null
                            && !actividadUpdate.getCosto().equals(actividad.getCosto())) {
                        cambioCosto = true;
                        nuevoCosto = actividadUpdate.getCosto();
                        logger.info("💵 Cambio de costo detectado: {} → {}",
                                actividad.getCosto(), nuevoCosto);
                    }

                    // 🔥 SI CAMBIÓ LA EMPRESA → ACTUALIZAR MONEDA Y CLIENTE EN ACTIVIDAD
                    if (cambioEmpresa && actualizacion.getRequerimiento() != null
                            && actualizacion.getRequerimiento().getIdEmpresa() != null) {

                        Empresa empresa = empresaRepository.findById(
                                        actualizacion.getRequerimiento().getIdEmpresa())
                                .orElse(null);

                        if (empresa != null) {
                            // Actualizar moneda en actividad
                            if (empresa.getIdmoneda() != null) {
                                actividad.setIdmoneda(empresa.getIdmoneda());
                                logger.info("💰 Moneda de actividad actualizada: {}", empresa.getIdmoneda());
                            }

                            // 🔥 ACTUALIZAR CLIENTE CON EL NOMBRE COMERCIAL DE LA EMPRESA
                            actividad.setCliente(empresa.getNombrecomercial());
                            logger.info("🏢 Cliente de actividad actualizado: {}", empresa.getNombrecomercial());
                        }
                    }

                    // Actualizar la actividad
                    actualizarActividad(actividad, actividadUpdate);
                    actividadRepository.save(actividad);

                    logger.info("✅ Actividad {} actualizada exitosamente", actividadUpdate.getId());
                }
            }
        }

        // ============================================================
        // 4. 🔥 ACTUALIZAR DESCRIPCIÓN ESTIMACIÓN (SI ES NECESARIO)
        // ============================================================
        if (cambioEmpresa || cambioCosto) {
            logger.info("🔄 Actualizando descripcionEstimacion - cambioEmpresa: {}, cambioCosto: {}",
                    cambioEmpresa, cambioCosto);

            // 🔥 Determinar el costo a usar
            Double costoFinal = (nuevoCosto != null) ? nuevoCosto : costoActual;

            // Determinar la moneda a usar
            Integer idMonedaFinal;
            if (nuevaIdMoneda != null) {
                // Si cambió la empresa, usar la nueva moneda
                idMonedaFinal = nuevaIdMoneda;
            } else {
                // Si no cambió empresa, obtener moneda de la empresa actual
                Empresa empresaActual = empresaRepository.findById(requerimiento.getIdEmpresa())
                        .orElse(null);
                idMonedaFinal = (empresaActual != null && empresaActual.getIdmoneda() != null)
                        ? empresaActual.getIdmoneda()
                        : 1;
            }

            // 🔥 GENERAR Y ACTUALIZAR DESCRIPCIÓN ESTIMACIÓN
            String descripcionEstimacion = generarDescripcionEstimacion(costoFinal, idMonedaFinal);
            requerimiento.setDescripcionEstimacion(descripcionEstimacion);
            requerimientoRepository.save(requerimiento);

            logger.info("💰 Descripción estimación actualizada: {} (costo: {}, moneda: {})",
                    descripcionEstimacion, costoFinal, idMonedaFinal);
        }

        // ============================================================
        // 5. Limpiar caché y retornar la asignación actualizada
        // ============================================================
        logger.info("✅ Asignación completa actualizada exitosamente");
        entityManager.flush();  // Escribir cambios a BD
        entityManager.clear();  // Limpiar caché de Hibernate

        return obtenerAsignacionCompleta(idRequerimiento);
    }

    // ========== Métodos auxiliares de actualización ==========

    /**
     * 🔥 Genera el código de requerimiento para una empresa
     * Busca el último código de esa empresa y genera el siguiente
     *
     * @param empresa La empresa para la cual generar el código
     * @param idRequerimientoActual El ID del requerimiento que se está actualizando
     * @return El nuevo código generado
     */
    private String generarCodigoRequerimiento(Empresa empresa, Integer idRequerimientoActual) {
        String prefijo = empresa.getPrefijonombrecomercial(); // Ej: "CST", "UPC", "CSC"
        int anio = LocalDate.now().getYear();

        // 🔥 Buscar el ÚLTIMO código de esta empresa en el año actual
        // (SIN excluir ningún requerimiento, queremos el correlativo real)
        List<String> codigos = requerimientoRepository
                .findUltimoCodigoByEmpresaAndYear(empresa.getId(), anio);

        int correlativo = 0; // Empieza en 0 para que el primero sea 0001

        if (!codigos.isEmpty()) {
            // Si hay códigos previos de esta empresa
            // Ejemplo código existente: "CST-2025-0013"
            String ultimoCodigo = codigos.get(0); // El más reciente
            String[] partes = ultimoCodigo.split("-");

            if (partes.length >= 3) {
                try {
                    // Extraer el correlativo (0013) y convertirlo a número
                    correlativo = Integer.parseInt(partes[2]);
                    logger.info("Último código de empresa {} ({}): {} - Correlativo: {}",
                            empresa.getId(), prefijo, ultimoCodigo, correlativo);
                } catch (NumberFormatException e) {
                    logger.error("Error al parsear correlativo de: {}", ultimoCodigo, e);
                    correlativo = 0;
                }
            }
        } else {
            // Si NO hay códigos previos de esta empresa, empieza desde 0
            logger.info("No hay códigos previos para empresa {} ({}) en el año {}, comenzando desde 0001",
                    empresa.getId(), prefijo, anio);
        }

        // 🔥 GENERAR EL SIGUIENTE CÓDIGO ÚNICO
        String codigoGenerado;
        boolean codigoExiste;
        int intentos = 0;
        int maxIntentos = 100; // Evitar loop infinito

        do {
            correlativo++;
            String nroFormato = String.format("%04d", correlativo);
            codigoGenerado = prefijo + "-" + anio + "-" + nroFormato;

            // Verificar si este código ya existe en la base de datos
            codigoExiste = requerimientoRepository.existsByCodRequerimiento(codigoGenerado);

            intentos++;

            if (codigoExiste) {
                logger.warn("⚠️ Código {} ya existe, intentando con siguiente correlativo (intento {}/{})",
                        codigoGenerado, intentos, maxIntentos);
            }

        } while (codigoExiste && intentos < maxIntentos);

        if (intentos >= maxIntentos) {
            throw new RuntimeException(
                    "No se pudo generar un código único para empresa " + prefijo +
                            " después de " + maxIntentos + " intentos");
        }

        logger.info("✅ Código generado para empresa {} ({}): {} en {} intento(s)",
                empresa.getId(), prefijo, codigoGenerado, intentos);

        return codigoGenerado;
    }
    /**
     * Genera la descripción de estimación en formato: "8000.0 Soles." o "8000.0 Dólares."
     */
    private String generarDescripcionEstimacion(Double costo, Integer idMoneda) {
        String nombreMoneda;

        // Mapeo de monedas comunes (ajusta según tu base de datos)
        switch (idMoneda) {
            case 1:
                nombreMoneda = "Soles";
                break;
            case 2:
                nombreMoneda = "Dolares";
                break;
            case 3:
                nombreMoneda = "Euros";
                break;
            case 4:
                nombreMoneda = "Yenes";
                break;
            case 5:
                nombreMoneda = "Guaranies";
                break;
            default:
                nombreMoneda = "Soles"; // Por defecto
        }

        return String.format("%.1f %s.", costo, nombreMoneda);
    }

    /**
     * Actualiza los campos del requerimiento que vienen en el DTO
     */
    private void actualizarRequerimiento(
            Requerimiento req,
            UpdateAsignacionResource.RequerimientoUpdateDTO update) {

        if (update.getTitulo() != null) {
            req.setTitulo(update.getTitulo());
        }
        if (update.getDetalle() != null) {
            req.setDetalle(update.getDetalle());
        }

        if(update.getIdEmpresa() != null){
            req.setIdEmpresa(update.getIdEmpresa());
        }
        if (update.getIdSubfrente() != null) {
            req.setIdSubfrente(update.getIdSubfrente());
        }
        if (update.getIdUsuario() != null) {
            req.setIdUsuario(update.getIdUsuario());
        }
        if (update.getDescripcionEstimacion() != null) {
            req.setDescripcionEstimacion(update.getDescripcionEstimacion());
        }
        if (update.getDetalleAsignacion() != null) {
            req.setDetalleAsignacion(update.getDetalleAsignacion());
        }
        if (update.getOrdenCompra() != null) {
            req.setOrdenCompra(update.getOrdenCompra());
        }
    }

    /**
     * Actualiza los campos de la actividad que vienen en el DTO
     */
    private void actualizarActividad(
            ActividadesPlanRealConsultor act,
            UpdateAsignacionResource.ActividadUpdateDTO update) {

        boolean cambioConsultor = false;
        boolean cambioFechas = false;

        if (update.getIdusuario() != null && !update.getIdusuario().equals(act.getIdusuario())) {
            act.setIdusuario(update.getIdusuario());
            cambioConsultor = true;
            logger.info("📝 Consultor actualizado: {} → {}", act.getIdusuario(), update.getIdusuario());
        }
        if (update.getFechainicio() != null && !Objects.equals(update.getFechainicio(), act.getFechainicio())) {
            act.setFechainicio(update.getFechainicio());
            cambioFechas = true;
            logger.info("📅 Fecha inicio actualizada");
        }
        if (update.getFechafin() != null && !Objects.equals(update.getFechafin(), act.getFechafin())) {
            act.setFechafin(update.getFechafin());
            logger.info("📅 Fecha fin actualizada");
            cambioFechas = true;
        }
        if (update.getIdtipoactividad() != null) {
            act.setIdtipoactividad(update.getIdtipoactividad());

            // Actualizar descripción según el tipo de actividad
            TipoActividad tipo = tipoActividadRepository.findById(update.getIdtipoactividad())
                    .orElse(null);
            if (tipo != null) {
                act.setDescripcion(tipo.getDescripcion());
            }
        }
        if (update.getTiemporegular() != null) {
            act.setTiemporegular(update.getTiemporegular());
        }
        if (update.getCosto() != null) {
            act.setCosto(update.getCosto());
        }
        if (update.getFacturable() != null) {
            act.setFacturable(update.getFacturable());
        }
        if (update.getTiempoextra() != null) {
            act.setTiempoextra(update.getTiempoextra());
        }
        if (update.getPorcentajeAvance() != null) {
            act.setPorcentajeAvance(update.getPorcentajeAvance());
        }
        if (update.getDescripcion() != null) {
            act.setDescripcion(update.getDescripcion());
        }

        // 🔥 SI CAMBIÓ CONSULTOR O FECHAS → ACTUALIZAR TÍTULO DEL REQUERIMIENTO Y REGTITULO
        if (cambioConsultor || cambioFechas) {
            Requerimiento req = requerimientoRepository.findById(act.getIdrequerimiento())
                    .orElse(null);

            if (req != null) {
                String nuevoTitulo = generarTituloRequerimiento(
                        act.getIdusuario(),
                        act.getFechainicio(),
                        act.getFechafin()
                );

                req.setTitulo(nuevoTitulo);
                act.setRegtitulo(nuevoTitulo); // 🔥 También actualizar regtitulo en actividad

                requerimientoRepository.save(req);
                logger.info("📝 Título actualizado: {}", nuevoTitulo);
            }
        }
    }

    private String generarTituloRequerimiento(Integer idUsuario, Date fechaInicio, Date fechaFin) {
        // Obtener nombre del consultor
        Usuario consultor = usuarioRepository.findById(idUsuario).orElse(null);

        String nombreCompleto = "Consultor Desconocido";
        if (consultor != null) {
            nombreCompleto = String.format("%s %s %s",
                    consultor.getNombres() != null ? consultor.getNombres() : "",
                    consultor.getApepaterno() != null ? consultor.getApepaterno() : "",
                    consultor.getApematerno() != null ? consultor.getApematerno() : ""
            ).trim();
        }

        // Formatear fechas a DD/MM/YYYY
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaInicioStr = fechaInicio != null ? sdf.format(fechaInicio) : "??/??/????";
        String fechaFinStr = fechaFin != null ? sdf.format(fechaFin) : "??/??/????";

        return String.format("%s (%s - %s)", nombreCompleto, fechaInicioStr, fechaFinStr);
    }



}
