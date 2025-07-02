package borsanova.politicaPrezzo;

import borsanova.Borsa.Azione;

/**
 * Interfaccia per la definizione di una politica di prezzo.
 * 
 * <p> Una politica di prezzo è un insieme di regole che determinano
 * il prezzo di un'azione in base a determinate condizioni.
 * </p>
 * 
 * <p> L'interfaccia contiene solo il metodo {@link #calcolaPrezzo(Azione, int, boolean)}
 *  che deve essere sovrascritto dalle classi che implementano una politica di prezzo.
 * </p>
 * 
 * <p>Strumenti di supporto utilizzati e confronti:
 *  <ul>
 *   <li> Giacomo Castellana, compagno di corso, per aver discusso
 *       sull'utilizzo dell'interfaccia per la definizione delle politiche di prezzo.</li>
 *   <li> GitHub Copilot (GPT 4o) per una scrittura del codice più fluida.</li>
 *  </ul>
 * 
 */
public interface PoliticaPrezzo {

    /**
     * Calcola il nuovo prezzo di un'Azione 
     * in base alla politica di prezzo.
     * 
     * @param azione l'azione di cui deve essere calcolato il prezzo in base alla politica.
     * @param quantita la quantità di azioni acquistate o vendute.
     * @param acquisto {@code true} se l'operazione è un acquisto, {@code false} se è una vendita.
     * @return il nuovo prezzo dell'azione.
     */
    public int calcolaPrezzo(Azione azione, int quantita, boolean acquisto);
}
