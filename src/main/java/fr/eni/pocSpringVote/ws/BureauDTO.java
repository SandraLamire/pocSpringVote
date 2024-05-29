package fr.eni.pocSpringVote.ws;


import fr.eni.pocSpringVote.entity.Bureau;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BureauDTO {
    private Integer idBureau;
    private String numero;
    private String adresse;

    public BureauDTO(Bureau bureau) {
        this.idBureau = bureau.getIdBureau();
        this.numero = bureau.getNumero();
        this.adresse = bureau.getAdresse();
    }


    public Bureau toEntity() {
        Bureau bureau = new Bureau();
        bureau.setIdBureau(idBureau);
        bureau.setNumero(numero);
        bureau.setAdresse(adresse);
        return bureau;
    }
}
