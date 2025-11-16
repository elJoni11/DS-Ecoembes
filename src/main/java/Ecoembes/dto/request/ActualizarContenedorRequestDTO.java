package Ecoembes.dto.request;

import Ecoembes.entity.NivelLlenado;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ActualizarContenedorRequestDTO {
    @NotNull @Min(0)
    private Integer envasesEstimados;
    @NotNull
    private NivelLlenado nivelLlenado;

    public ActualizarContenedorRequestDTO() {
    }

    // Getters y Setters
    public Integer getEnvasesEstimados() {
        return envasesEstimados;
    }
    public void setEnvasesEstimados(Integer envasesEstimados) {
        this.envasesEstimados = envasesEstimados;
    }
    public NivelLlenado getNivelLlenado() {
        return nivelLlenado;
    }
    public void setNivelLlenado(NivelLlenado nivelLlenado) {
        this.nivelLlenado = nivelLlenado;
    }
}
