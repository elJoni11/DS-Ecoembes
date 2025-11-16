package Ecoembes.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class AsignacionRequestDTO {
    @NotEmpty
    private String plantaID;
    @NotNull @FutureOrPresent
    private LocalDate fechaPrevista;
    @NotEmpty
    private List<String> listaContenedoresID;

    public AsignacionRequestDTO() {
    }

    // Getters y Setters
    public String getPlantaID() {
        return plantaID;
    }
    public void setPlantaID(String plantaID) {
        this.plantaID = plantaID;
    }
    public LocalDate getFechaPrevista() {
        return fechaPrevista;
    }
    public void setFechaPrevista(LocalDate fechaPrevista) {
        this.fechaPrevista = fechaPrevista;
    }
    public List<String> getListaContenedoresID() {
        return listaContenedoresID;
    }
    public void setListaContenedoresID(List<String> listaContenedoresID) {
        this.listaContenedoresID = listaContenedoresID;
    }
}
