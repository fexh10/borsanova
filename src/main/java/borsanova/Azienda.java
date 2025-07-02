package borsanova;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Classe che rappresenta un'azienda.
 *  
 * <p>Un'Azienda è caratterizzata da:
 *  <ul>
 *      <li>un nome (unico, e non può essere modificato);</li>
 *      <li>un insieme di borse dove è quotata.</li>
 *  </ul>
 * 
 * <p> Essendo il nome unico, non è possibile creare due aziende con lo stesso nome.
 * 
 * <p>Un'Azienda può decidere di quotarsi in una borsa,
 * specificando il numero totale di azioni e il prezzo unitario di ciascuna azione.
 * Inoltre è possibile ottenere un insieme non modificabile delle borse dove l'azienda è quotata.
 * 
 * <p>Il criterio di confronto e di ordinamento tra le aziende è il nome.
 * 
 * <p> Strumenti di supporto e confronto:
 *  <ul>
 *   <li> Matteo Mascherpa, compagno di corso, per 
 *       il suggerimento di aggiungere il criterio di confronto e ordinamento 
 *       alla documentazione di questa e delle altre classi che implementano {@link Comparable}.</li>
 *  </ul>
 * 
 * @see Comparable
 */
public class Azienda implements Comparable<Azienda> {
    
    /** Registro delle istanze uniche di Azienda.
     *  La chiave è il nome dell'azienda, il valore è l'istanza di Azienda. 
     */
    private static final Map<String, Azienda> ISTANZE = new TreeMap<>();

    /**Il nome di questa Azienda.*/
    public final String nome;

    /**Le borse dove questa Azienda è quotata.*/
    private final Set<Borsa> borse;

    /*-
     * AF:
     *  - nome è il nome dell'azienda.
     *  - borse è l'insieme delle borse dove l'azienda è quotata.
     * 
     * RI: 
     *  - nome non può essere null e non può essere vuoto o contenere solo spazi.
     *  - borse non può essere null e non può contenere null.
     *    Inoltre borse non può contenere duplicati e deve essere ordinata in ordine lessicografico.
     */

    /**
     * Metodo di fabbricazione per ottenere un'istanza di Azienda.
     * 
     * <p>Se non esiste già un'azienda con il nome specificato, ne viene creata una nuova.
     * Altrimenti, viene restituita l'istanza già esistente.</p>
     * 
     * @param nome il nome dell'azienda.
     * @throws NullPointerException se {@code nome} è {@code null}.
     * @throws IllegalArgumentException se {@code nome} è vuoto o contiene solo spazi.
     * @return l'istanza di Azienda.
     */
    public static Azienda of(final String nome) {
        if (Objects.requireNonNull(nome, "Il nome dell'azienda non può essere null").isBlank()) 
            throw new IllegalArgumentException("Il nome dell'azienda non può essere vuoto");
        if (!ISTANZE.containsKey(nome)) ISTANZE.put(nome, new Azienda(nome));
        return ISTANZE.get(nome);
    }

    /**
     * Costruisce un'istanza di Azienda con il nome specificato.
     * 
     * <p>Il costruttore è privato per garantire
     * che le istanze di Azienda siano create solo tramite il metodo di fabbricazione {@link #of(String)}.</p>
     * 
     * @param nome il nome dell'azienda.
     */
    private Azienda(final String nome) {
        this.nome = nome;
        this.borse = new TreeSet<>();
    }

    /**
     * Restituisce un insieme
     * non modificabile delle borse
     * dove questa azienda è quotata.
     * 
     * @return l'insieme delle borse.
     */
    public Iterable<Borsa> borse() {
        return Collections.unmodifiableSet(borse);
    }

    /**
     * Quota questa azienda in una borsa.
     * 
     * <p> Questo metodo modifica this 
     * perché aggiunge la borsa all'insieme
     * delle borse dove l'azienda è quotata.</p>
     * 
     * <p> Questo metodo inoltre modifica lo stato 
     * della borsa specificata, aggiugendo l'azione
     * di questa azienda nell'elenco delle azioni quotate.</p>
     * 
     * @param borsa la borsa in cui quotare l'azienda.
     * @param azioniTotali il numero totale di azioni dell'azienda.
     * @param prezzo il prezzo unitario di ciascuna azione.
     * @throws NullPointerException se borsa è {@code null}.
     * @throws IllegalArgumentException se le azioni totali o il prezzo sono minori o uguali a 0,
     *  oppure se l'azienda è già quotata nella borsa specificata.
     */
    public void quotaInBorsa(final Borsa borsa, final int azioniTotali, final int prezzo) {
        Objects.requireNonNull(borsa, "La borsa non può essere null");
        if (azioniTotali <= 0) throw new IllegalArgumentException("Il numero di azioni totali deve essere positivo");
        if (prezzo <= 0) throw new IllegalArgumentException("Il prezzo unitario deve essere positivo");
        if (!borse.add(borsa))
            throw new IllegalArgumentException("L'azienda "  + nome + " è già quotata nella borsa di " + borsa.nome);
        borsa.quota(this, azioniTotali, prezzo);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nome);
        for (Borsa b: borse) sb.append("\n- ").append(b.nome);
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Azienda other)) return false;
        return nome.equals(other.nome);
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }

    @Override
    public int compareTo(Azienda altra) {
        return nome.compareTo(altra.nome);
    }

}
