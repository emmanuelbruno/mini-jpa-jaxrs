package org.example;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter

@NamedQuery(name = "Maison.findAll", query = "select m from Maison m")
@NamedQuery(name = "Maison.findByVetuste", query = "select m from Maison m where m.vetuste = :vetuste")
@NamedQuery(name = "Maison.findGrandes", query = "select m from Maison m where m.surface >=150")

@XmlRootElement
public class Maison {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private Vetuste vetuste;
    private int surface;
    private Date dateDeConstruction;
    @Basic(fetch = FetchType.EAGER)
    private Byte[] big;

    public enum Vetuste {INSALUBRE, ANCIEN, BON_ETAT, NEUF}

}
