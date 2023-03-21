package ssvv.example.repository;

import ssvv.example.domain.*;
import ssvv.example.validation.*;

public class NotaRepository extends AbstractCRUDRepository<Pair<String, String>, Nota> {
    public NotaRepository(Validator<Nota> validator) {
        super(validator);
    }
}
