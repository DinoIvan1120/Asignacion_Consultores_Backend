package com.IngSoftware.proyectosgr.service.impl;

import com.IngSoftware.proyectosgr.config.exception.ResourceNotFoundException;
import com.IngSoftware.proyectosgr.domain.model.Empresa;
import com.IngSoftware.proyectosgr.domain.model.Requerimiento;
import com.IngSoftware.proyectosgr.domain.model.Usuario;
import com.IngSoftware.proyectosgr.domain.repository.EmpresaRepository;
import com.IngSoftware.proyectosgr.domain.repository.RequerimientoRepository;
import com.IngSoftware.proyectosgr.domain.repository.UsuarioRepository;
import com.IngSoftware.proyectosgr.service.RequerimientoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class RequerimientoServiceImpl implements RequerimientoService {

    private static final Logger logger = LoggerFactory.getLogger(RequerimientoServiceImpl.class);

    @Autowired
    private RequerimientoRepository requerimientoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;


    /**
     * ✅ NUEVO: Filtrado dinámico con múltiples parámetros opcionales
     */
    @Override
    public Page<Requerimiento> searchByFilters(
            Integer idEmpresa,
            String codRequerimiento,
            Date fechaInicio,
            Date fechaFin,
            Integer idUsuario,
            Integer idRequerimiento,
            Integer idEstadoRequerimiento,
            String nombreConsultor,
            Pageable pageable) {

        Integer idCoordinador = getAuthenticatedCoordinadorId();

        logger.info("Filtrando requerimientos del coordinador {} con filtros: " +
                        "idEmpresa={}, codRequerimiento={}, fechaInicio={}, fechaFin={}, " +
                        "idUsuario={}, idRequerimiento={}, idEstadoRequerimiento={}",
                idCoordinador, idEmpresa, codRequerimiento, fechaInicio, fechaFin,
                idUsuario, idRequerimiento, idEstadoRequerimiento);

        return requerimientoRepository.findByMultipleFilters(
                idCoordinador,
                idEmpresa,
                codRequerimiento,
                fechaInicio,
                fechaFin,
                idUsuario,
                idRequerimiento,
                idEstadoRequerimiento,
                nombreConsultor,
                pageable
        );
    }

    @Override
    public Page<Requerimiento> searchByFiltersIdCodigoRequerimiento(Pageable pageable) {
        Integer idCoordinador = getAuthenticatedCoordinadorId();
        logger.info("Listando SOLO id de requerimientos del coordinador {}", idCoordinador);
        return requerimientoRepository.findCodigosByIdCoordinador(idCoordinador, pageable);
    }

    @Override
    public Page<Requerimiento> getCodigosByCoordinador(Pageable pageable) {
        Integer idCoordinador = getAuthenticatedCoordinadorId();
        logger.info("Listando SOLO códigos de requerimientos del coordinador {}", idCoordinador);

        return requerimientoRepository.findCodigosByIdCoordinador(idCoordinador, pageable);
    }



    /**
     * Obtiene el ID del usuario coordinador autenticado
     */
    private Integer getAuthenticatedCoordinadorId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        logger.info("Obteniendo ID del coordinador autenticado: {}", username);

        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "usuario ", username));

        logger.info("ID del coordinador obtenido: {}", usuario.toString());
        return usuario.getIdUsuario();
    }

    @Override
    public List<Requerimiento> getAllByCoordinador() {
        Integer idCoordinador = getAuthenticatedCoordinadorId();
        logger.info("Listando todos los requerimientos del coordinador: {}", idCoordinador);
        return requerimientoRepository.findByIdCoordinador(idCoordinador);
    }


    /**
     * ✅ MÉTODO ACTUALIZADO - Usa JOIN FETCH para traer las relaciones
     */
    @Override
    public Page<Requerimiento> getAllByCoordinador(Pageable pageable) {
        Integer idCoordinador = getAuthenticatedCoordinadorId();
        logger.info("Listando requerimientos con paginación del coordinador: {} (CON JOIN FETCH)", idCoordinador);

        // ✅ Usar el método con JOIN FETCH para traer las relaciones
        return requerimientoRepository.findByIdCoordinadorWithRelations(idCoordinador, pageable);
    }

    /*
    @Override
    public Page<Requerimiento> getAllByCoordinador(Pageable pageable) {
        Integer idCoordinador = getAuthenticatedCoordinadorId();
        logger.info("Listando requerimientos con paginación del coordinador: {}", idCoordinador);
        return requerimientoRepository.findByIdCoordinador(idCoordinador, pageable);
    }
     */

    @Override
    public Requerimiento getById(Integer id) {
        Integer idCoordinador = getAuthenticatedCoordinadorId();
        logger.info("Buscando requerimiento {} del coordinador: {}", id, idCoordinador);

        return requerimientoRepository.findByIdRequerimientoAndIdCoordinador(id, idCoordinador)
                .orElseThrow(() -> new ResourceNotFoundException("Requerimiento", "id " + id));
    }

    @Override
    public Requerimiento getByCodRequerimiento(String codRequerimiento) {
        Integer idCoordinador = getAuthenticatedCoordinadorId();
        logger.info("Buscando requerimiento con código {} del coordinador: {}", codRequerimiento, idCoordinador);

        return requerimientoRepository.findByCodRequerimientoAndIdCoordinador(codRequerimiento, idCoordinador)
                .orElseThrow(() -> new ResourceNotFoundException("Requerimiento", "codRequerimiento", codRequerimiento));
    }

    @Override
    public List<Requerimiento> getByEstadoRequerimiento(Integer idEstadoRequerimiento) {
        Integer idCoordinador = getAuthenticatedCoordinadorId();
        logger.info("Listando requerimientos con estado {} del coordinador: {}", idEstadoRequerimiento, idCoordinador);
        return requerimientoRepository.findByIdEstadoRequerimientoAndIdCoordinador(idEstadoRequerimiento, idCoordinador);
    }

    @Override
    public List<Requerimiento> getByEmpresa(Integer idEmpresa) {
        Integer idCoordinador = getAuthenticatedCoordinadorId();
        logger.info("Listando requerimientos de empresa {} del coordinador: {}", idEmpresa, idCoordinador);
        return requerimientoRepository.findByIdEmpresaAndIdCoordinador(idEmpresa, idCoordinador);
    }

    @Override
    public List<Requerimiento> getByPrioridad(Integer idPrioridad) {
        Integer idCoordinador = getAuthenticatedCoordinadorId();
        logger.info("Listando requerimientos con prioridad {} del coordinador: {}", idPrioridad, idCoordinador);
        return requerimientoRepository.findByIdPrioridadAndIdCoordinador(idPrioridad, idCoordinador);
    }

    @Override
    public List<Requerimiento> getByUrgente(Boolean urgente) {
        Integer idCoordinador = getAuthenticatedCoordinadorId();
        logger.info("Listando requerimientos urgentes={} del coordinador: {}", urgente, idCoordinador);
        return requerimientoRepository.findByUrgenteAndIdCoordinador(urgente, idCoordinador);
    }

    @Override
    public List<Requerimiento> getBySubfrente(Integer idSubfrente) {
        Integer idCoordinador = getAuthenticatedCoordinadorId();
        logger.info("Listando requerimientos de subfrente {} del coordinador: {}", idSubfrente, idCoordinador);
        return requerimientoRepository.findByIdSubfrenteAndIdCoordinador(idSubfrente, idCoordinador);
    }

    @Override
    @Transactional
    public Requerimiento create(Requerimiento requerimiento) {
        Integer idCoordinador = getAuthenticatedCoordinadorId();

        // Asignar automáticamente el coordinador y usuario
        requerimiento.setIdCoordinador(idCoordinador);

        // 2. Valores que la app antigua generaba automáticamente
        requerimiento.setIdEstadoRequerimiento(7);     // Estado inicial
        requerimiento.setIdPrioridad(2);               // Prioridad por defecto
        requerimiento.setUrgente(false);               // No urgente
        requerimiento.setTipoFacturacion("S");         // Tipo facturación default
        requerimiento.setCostoRecalculado("");         // Vacío
        requerimiento.setCostoRequerimiento(0.0);      // Costo inicial         // País Perú según tus datos
        requerimiento.setAvanceAutomatico(true);       // Default antiguo
        requerimiento.setNotificandoCierre(false);     // Default
        requerimiento.setSemaforo("2");                // Semáforo default
        requerimiento.setAplicaCierreAutomatico(false);
        requerimiento.setSwFactory(false);
        requerimiento.setActivarExtra(false);

        requerimiento.setIdTipoRequerimiento(0);
        requerimiento.setIdTipoServicio(0);
        requerimiento.setIdArea(0);
        requerimiento.setIdUnidadOrganizativa(0);
        requerimiento.setIdEstadoIncidencia(0);
        requerimiento.setIdProcesoIncidencia(0);
        requerimiento.setIdTipoIncidencia(0);
        requerimiento.setIdUsuarioXEmpXSubfrente(0);
        requerimiento.setIdComercial(0);
        requerimiento.setIdOportunidad(0);
        requerimiento.setIdTipoRequerimiento(0);

        // 3. Fechas automáticas como la app vieja
        if (requerimiento.getFechaEnvio() == null) {
            requerimiento.setFechaEnvio(new Date());
        }

        if (requerimiento.getFechaRequerimiento() == null) {
            requerimiento.setFechaRequerimiento(new Date());
        }

        // ============================================================
        // 1. Obtener empresa UNA SOLA VEZ (trae TODO: países + prefijo + demás datos)
        // ============================================================
        Empresa empresa = empresaRepository.findByIdWithPaises(requerimiento.getIdEmpresa())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        // ============================================================
        // 2. Obtener el país asociado automáticamente
        // ============================================================
        if (empresa.getPaises() != null && !empresa.getPaises().isEmpty()) {
            Integer idPais = empresa.getPaises().get(0).getId();
            requerimiento.setIdPais(idPais);
            logger.info("País asignado automáticamente: {}", idPais);
        } else {
            // Fallback: si no tiene país asociado, usar Perú por defecto
            requerimiento.setIdPais(173);
            logger.warn("Empresa sin país asociado, usando Perú (173) por defecto");
        }

        String prefijo = empresa.getPrefijonombrecomercial(); // Ej: "UPC", "OPI", "SYPE"

        // 2. Año actual
        int anio = LocalDate.now().getYear();

        // 3. Obtener últimos códigos
        List<String> codigos = requerimientoRepository
                .findCodigosByEmpresaAndYear(empresa.getId(), anio);

        // 4. Determinar correlativo
        int correlativo = 1;
        if (!codigos.isEmpty()) {
            // Ejemplo código: "UPC-2025-0034"
            String ultimo = codigos.get(0);
            String[] partes = ultimo.split("-");
            correlativo = Integer.parseInt(partes[2]) + 1;
        }

        // 5. Formatear número
        String nroFormato = String.format("%04d", correlativo);

        // Código final
        String codigoGenerado = prefijo + "-" + anio + "-" + nroFormato;

        requerimiento.setCodRequerimiento(codigoGenerado);

        logger.info("Código generado: {}", codigoGenerado);

        // 6. Guardar
        Requerimiento nuevoRequerimiento = requerimientoRepository.save(requerimiento);

        return nuevoRequerimiento;
    }

    @Override
    public Requerimiento update(Integer id, Requerimiento requerimiento) {
        Integer idCoordinador = getAuthenticatedCoordinadorId();

        // Validar que el requerimiento existe y pertenece al coordinador
        Requerimiento existingRequerimiento = requerimientoRepository
                .findByIdRequerimientoAndIdCoordinador(id, idCoordinador)
                .orElseThrow(() -> new ResourceNotFoundException("Requerimiento", "id  " + id));

        logger.info("Actualizando requerimiento {} del coordinador: {}", id, idCoordinador);

        // Mantener el coordinador original
        requerimiento.setIdRequerimiento(id);
        requerimiento.setIdCoordinador(existingRequerimiento.getIdCoordinador());
        requerimiento.setFechaRegistro(existingRequerimiento.getFechaRegistro());

        Requerimiento updatedRequerimiento = requerimientoRepository.save(requerimiento);
        logger.info("Requerimiento {} actualizado exitosamente", id);

        return updatedRequerimiento;
    }

    @Override
    public Requerimiento delete(Integer id) {
        Integer idCoordinador = getAuthenticatedCoordinadorId();

        // Validar que el requerimiento existe y pertenece al coordinador
        Requerimiento requerimiento = requerimientoRepository
                .findByIdRequerimientoAndIdCoordinador(id, idCoordinador)
                .orElseThrow(() -> new ResourceNotFoundException("Requerimiento", "id " + id));

        logger.info("Eliminando requerimiento {} del coordinador: {}", id, idCoordinador);
        requerimientoRepository.delete(requerimiento);
        logger.info("Requerimiento {} eliminado exitosamente", id);

        return requerimiento;
    }
}
