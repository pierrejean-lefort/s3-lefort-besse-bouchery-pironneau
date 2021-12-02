package fr.umontpellier.lpbr.s3.SystemTournoi;

import fr.umontpellier.lpbr.s3.Partie;

import java.util.List;

public abstract class SystemTournoi {
    public abstract List<Partie> firstRound();
    public abstract List<Partie> newRound(int round) throws Exception;
}
