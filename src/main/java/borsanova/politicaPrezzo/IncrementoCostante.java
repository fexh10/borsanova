package borsanova.politicaPrezzo;

import borsanova.Borsa.Azione;

/**
 * Classe che implementa una politica di prezzo {@link PoliticaPrezzo}
 * ad incremento costante.
 * 
 * <p> L'incremento costante è un'operazione che
 * aumenta il prezzo di un'azione di un valore
 * costante se un'azione viene acquistata,
 * altrimenti lascia il prezzo invariato. </p>
 * 
 * <p> Strumenti di supporto utilizzati:
 *  <ul>
 *   <li> GitHub Copilot (GPT 4o) per una scrittura più rapida del codice. </li>
 *  </ul>
 * 
 */
public class IncrementoCostante implements PoliticaPrezzo {

    /** Il valore che rappresenta la crescita
     * del prezzo di un'azione.
     */
    private final int valore;

    /*-
     * AF:
     *  - valore rappresenta il valore di incremento del prezzo di un'azione.
     * 
     * RI:
     *  - valore non può essere minore o uguale a 0.
     */

    /**
     * Costruisce un'istanza di IncrementoCostante, 
     * con un valore di incremento specificato.
     * 
     * @param valore il valore di incremento.
     * @throws IllegalArgumentException se il valore di incremento è minore o uguale a 0.
     */
    public IncrementoCostante(int valore) {
        if (valore <= 0) 
            throw new IllegalArgumentException("Il valore di incremento deve essere positivo.");
        this.valore = valore;
    }

    @Override
    public int calcolaPrezzo(final Azione azione, final int quantita, final boolean acquisto) {
        if (acquisto) 
            return azione.prezzo() + valore;
        return azione.prezzo();
    }
}
