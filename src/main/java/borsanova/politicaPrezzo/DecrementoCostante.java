package borsanova.politicaPrezzo;

import borsanova.Borsa.Azione;

/**
 * Classe che implementa una politica di prezzo {@link PoliticaPrezzo}
 * a decremento costante.
 * 
 * <p> Il decremento costante è un'operazione che
 * diminuisce il prezzo di un'azione di un valore
 * costante se un'azione viene venduta,
 * altrimenti lascia il prezzo invariato. Se con il decremento
 * il prezzo dell'azione diventa minore o uguale a 0,
 * il prezzo dell'azione viene impostato a 1. </p> 
 * 
 * <p> Strumenti di supporto utilizzati:
 *  <ul>
 *  <li> GitHub Copilot (GPT 4o) per una scrittura più rapida del codice. </li>
 *  </ul>
 * 
 */
public class DecrementoCostante implements PoliticaPrezzo {

    /**
     * Il valore che rappresenta la decrescita
     * del prezzo di un'azione.
     */
    private final int valore;

    /*-
     * AF:
     *  - valore rappresenta il valore di decremento del prezzo di un'azione.
     * 
     * RI:
     *  - valore non può essere minore o uguale a 0.
     */

    /**
     * Costruisce un'istanza di DecrementoCostante,
     * con un valore di decremento specificato.
     * 
     * @param valore il valore di decremento.
     * @throws IllegalArgumentException se il valore di decremento è minore o uguale a 0.
     */
    public DecrementoCostante(int valore) {
        if (valore <= 0)
            throw new IllegalArgumentException("Il valore di decremento deve essere maggiore di 0.");
        this.valore = valore;
    }

    @Override
    public int calcolaPrezzo(final Azione azione, final int quantita, final boolean acquisto) {
        if (acquisto)  
            return azione.prezzo();
        if (azione.prezzo() - valore <= 0)
            return 1;
        return azione.prezzo() - valore;
    }

}
