package pl.sda.eventlift.events.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Countries {
    PL("Polska"),
    US("USA");

    String plName;
}
