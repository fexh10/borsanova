package borsanova.politicaPrezzo;

import borsanova.Borsa.Azione;

/**
 * Classe che implementa una politica {@link PoliticaPrezzo}
 * di variazione costante del prezzo.
 * 
 * <p> Una politica a variazione costante del prezzo prevede,
 * in caso di acquisto di un'azione, una politica ad Incremento Costante ({@link IncrementoCostante}),
 * mentre in caso di vendita di un'azione, una politica a Decremento Costante ({@link DecrementoCostante}).
 * 
 * <p> Strumenti di supporto utilizzati:
 *  <ul>
 *  <li> GitHub Copilot (GPT 4o) per una scrittura più fluida del codice. </li>
 *  </ul>
 * 
 */
public class VariazioneCostante implements PoliticaPrezzo{

    /** Il valore dell'incremento costante del prezzo. */
    private final IncrementoCostante incremento;

    /** Il valore del decremento costante del prezzo. */
    private final DecrementoCostante decremento;

    /*-
     * AF:
     *  - incremento è il valore di incremento costante del prezzo .
     *  - decremento è il valore di decremento costante del prezzo.
     * 
     * RI: 
     *  - incremento non deve essere minore o uguale a 0.
     *  - decremento non deve essere minore o uguale a 0.
     */

    /**
     * Costruisce una politica di variazione costante del prezzo.
     * 
     * @param incremento l'incremento costante del prezzo.
     * @param decremento il decremento costante del prezzo.
     * @throws IllegalArgumentException se incremento o decremento sono minori o uguali a 0.
     */
    public VariazioneCostante(int incremento, int decremento) {
        if (incremento <= 0 || decremento <= 0)
            throw new IllegalArgumentException("Incremento e decremento devono essere maggiori di 0.");
        this.incremento = new IncrementoCostante(incremento);
        this.decremento = new DecrementoCostante(decremento);
    }

    @Override
    public int calcolaPrezzo(final Azione azione, final int quantita, final boolean acquisto) {
        if (acquisto)
            return incremento.calcolaPrezzo(azione, quantita, acquisto);
        return decremento.calcolaPrezzo(azione, quantita, acquisto);
    }

}
