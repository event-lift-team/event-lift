package pl.sda.eventlift.events.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Countries {
    PL("Polska"),
    DE("Niemcy"),
    CZ("Czechy"),
    FR("Francja"),
    AT("Austria"),
    BE("Belgia"),
    CH("Szwajcaria");

    String plName;
}
