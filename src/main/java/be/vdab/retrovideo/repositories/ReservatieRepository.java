package be.vdab.retrovideo.repositories;

import be.vdab.retrovideo.domain.Reservatie;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservatieRepository {
    void insertNieuweReservatie(Reservatie reservatie);

}
