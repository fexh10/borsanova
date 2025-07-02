package borsanova.politicaPrezzo;

import borsanova.Borsa.Azione;

/**
 * Classe che implementa una politica di prezzo
 * invariata.
 * 
 * <p> Una politica di prezzo invariata non modifica il prezzo
 * corrente di un'azione. </p>
 * 
 * <p> Strumenti di supporto utilizzati:
 *  <ul>
 *  <li> GitHub Copilot (GPT 4o) per una scrittura più rapida del codice. </li>
 *  </ul>
 * 
 */
public class PrezzoInvariato implements PoliticaPrezzo {

    /**
     * Costruisce un'istanza di PrezzoInvariato vuota.
     */
    public PrezzoInvariato() {}

    /*-
     * AF:
     * 
     * RI:
     */

    @Override
    public int calcolaPrezzo(final Azione azione, final int quantita, final boolean acquisto) {
        return azione.prezzo();
    }
}
