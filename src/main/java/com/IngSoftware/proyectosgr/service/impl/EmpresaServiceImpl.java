package com.IngSoftware.proyectosgr.service.impl;

import com.IngSoftware.proyectosgr.config.exception.ResourceNotFoundException;
import com.IngSoftware.proyectosgr.domain.model.Empresa;
import com.IngSoftware.proyectosgr.domain.repository.EmpresaRepository;
import com.IngSoftware.proyectosgr.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    public List<Empresa> getAll() {
        return empresaRepository.findAll();
    }

    @Override
    public Page<Empresa> getAll(Pageable pageable) {
        return empresaRepository.findAll(pageable);
    }

    @Override
    public Empresa getById(Integer id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa", "id = " + id));
    }

    @Override
    public List<Empresa> getByEstado(Boolean estado) {
        return empresaRepository.findByEstado(estado);
    }

    @Override
    public Empresa getByRuc(String ruc) {
        return empresaRepository.findByRuc(ruc)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa", "ruc", ruc));
    }

    @Override
    public Empresa getByIdempresaSap(String idempresaSap) {
        return empresaRepository.findByIdempresaSap(idempresaSap)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa", "idempresaSap", idempresaSap));
    }

    @Override
    public List<Empresa> getByEmpresaPadre(Integer empresaPadre) {
        return empresaRepository.findByEmpresaPadre(empresaPadre);
    }

    @Override
    public List<Empresa> getNombresComerciales() {
        return empresaRepository.findAll();
    }

    @Override
    public Empresa create(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Override
    public Empresa update(Integer id, Empresa empresaRequest) {
        Empresa empresa = getById(id);

        empresa.setIdmoneda(empresaRequest.getIdmoneda());
        empresa.setIdfrente(empresaRequest.getIdfrente());
        empresa.setRazonsocial(empresaRequest.getRazonsocial());
        empresa.setNombrecomercial(empresaRequest.getNombrecomercial());
        empresa.setRuc(empresaRequest.getRuc());
        empresa.setDireccion(empresaRequest.getDireccion());
        empresa.setTelefono(empresaRequest.getTelefono());
        empresa.setEstado(empresaRequest.getEstado());
        empresa.setCodinterno(empresaRequest.getCodinterno());
        empresa.setLogo(empresaRequest.getLogo());
        empresa.setPrefijonombrecomercial(empresaRequest.getPrefijonombrecomercial());
        empresa.setUsuarioSolicitante(empresaRequest.getUsuarioSolicitante());
        empresa.setActivarPais(empresaRequest.getActivarPais());
        empresa.setTiempoPrueba(empresaRequest.getTiempoPrueba());
        empresa.setAplicaDetraccion(empresaRequest.getAplicaDetraccion());
        empresa.setEmpresaPadre(empresaRequest.getEmpresaPadre());
        empresa.setPrioridad(empresaRequest.getPrioridad());
        empresa.setAplicaCierreAutomatico(empresaRequest.getAplicaCierreAutomatico());
        empresa.setIdempresaSap(empresaRequest.getIdempresaSap());
        empresa.setIdBukrs(empresaRequest.getIdBukrs());

        return empresaRepository.save(empresa);
    }

    @Override
    public Empresa delete(Integer id) {
        Empresa empresa = getById(id);
        empresaRepository.delete(empresa);
        return empresa;
    }

    @Override
    public Empresa getByIdWithMoneda(Integer id) {
        return empresaRepository.findByIdWithMoneda(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa", "id = " + id));
    }

    // 🔥 NUEVO: Obtener empresa con países
    @Override
    public Empresa getByIdWithPaises(Integer id) {
        return empresaRepository.findByIdWithPaises(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa", "id = " + id));
    }


}
