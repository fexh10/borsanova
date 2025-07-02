package borsanova;

import borsanova.politicaPrezzo.*;
import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


/**
 * Classe che rappresenta una borsa.
 * 
 * <p> Una borsa è rappresentata da:
 *  <ul>
 *   <li> Un nome (unico, e non può essere modificato); </li>
 *   <li> Un elenco di azioni quotate in questa borsa; </li>
 *   <li> Una politica di variazione del prezzo delle azioni quotate in questa borsa. </li>
 *  </ul>
 * 
 * <p> Dato che il nome di una borsa è unico, non è possibile creare due borse con lo stesso nome.
 * 
 * <p> Una borsa può:
 *  <ul>
 *   <li> impostare una politica di prezzo; </li>
 *   <li> restituire l'elenco delle azioni quotate in questa borsa; </li>
 *   <li> restituire un'azione data un'azienda; </li>
 *  </ul>
 * 
 * 
 * <p>Il criterio di confronto e ordinamento tra borse è il nome.
 * 
 * <p> Strumenti di supporto utilizzati:
 *  <ul>
 *  <li> Matteo Mascherpa, compagno di corso, per per la discussione sulla parte del comprare e vendere le azioni.</li>
 *  <li> GitHub Copilot (GPT 4o) per il refactoring del metodo {@link Azione#compareTo(Azione)}
 *    e per una scrittura più fluida del codice. </li>
 *  </ul>
 * 
 * 
 * @see Comparable
 * 
 */
public class Borsa implements Comparable<Borsa> {

    /** Registro contenente le borse istanziate */
    private static final Map<String, Borsa> ISTANZE = new TreeMap<>();

    /** Il nome della borsa. */
    public final String nome;

    /** L'elenco delle azioni quotate in questa borsa. */
    private final Set<Azione> azioni;

    /** La politica di variazione del prezzo delle azioni quotate in questa borsa.*/
    private PoliticaPrezzo politica;

    /*-
     * AF: 
     *  - nome è il nome della borsa.
     *  - azioni è l'insieme delle azioni quotate in questa borsa.
     *  - politica è la politica di variazione del prezzo delle azioni quotate in questa borsa.
     * 
     * RI:
     *  - nome non può essere null e non può essere vuoto o contenere solo spazi.
     *  - azioni non può essere null e non può contenere null. 
     *    Inoltre non può contenere duplicati e deve essere ordinata in ordine lessicografico.
     *  - politica non può essere null.
     */

    /**
     * Metodo di fabbricazione per ottenere un'istanza di Borsa.
     * 
     * <p>
     *  Se non esiste già una borsa con il nome specificato, ne viene creata una
     *  nuova.
     *  Altrimenti, viene restituita l'istanza già esistente.
     * </p>
     * 
     * @param nome il nome della borsa.
     * @throws NullPointerException se {@code nome} è {@code null}.
     * @throws IllegalArgumentException se {@code nome} è vuoto o contiene solo spazi.
     * 
     * @return l'istanza di Borsa.
     */
    public static Borsa of(final String nome) {
        if (Objects.requireNonNull(nome).isBlank())
            throw new IllegalArgumentException("Il nome della borsa non può essere vuoto");
        if (!ISTANZE.containsKey(nome))
            ISTANZE.put(nome, new Borsa(nome));
        return ISTANZE.get(nome);
    }

    /**
     * Costruisce un'istanza di Borsa.
     * 
     * <p>
     * Il costruttore è privato per garantire che le istanze di Borsa siano create
     * solo tramite il metodo di fabbricazione {@link #of(String)}.
     * </p>
     * 
     * @param nome il nome della borsa.
     */
    private Borsa(final String nome) {
        this.nome = nome;
        this.azioni = new TreeSet<>();
        this.politica = new PrezzoInvariato();
    }

    /**
     * Imposta la politica di variazione del prezzo delle azioni quotate in questa borsa.
     * 
     * <p> Viene modificato this dato che viene impostata 
     * la politica.
     * 
     * @param politica la politica di prezzo da impostare.
     * @throws NullPointerException se politica è {@code null}.
     * 
     */
    public void politica(final PoliticaPrezzo politica) {
        Objects.requireNonNull(politica, "La politica di prezzo non può essere null");
        this.politica = politica;
    } 

    /**
     * Restituisce l'elenco delle azioni quotate in questa borsa.
     * 
     * @return l'elenco delle azioni.
     */
    public Iterable<Azione> azioni() {
        return Collections.unmodifiableSet(azioni);
    }

    /**
     * Quota un'azienda in questa borsa.
     * 
     * <p> L'azione prodotta da questa quotazione
     * verrà aggiunta all'elenco delle azioni quotate in questa borsa, quindi
     * viene modificato this.</p>
     * 
     * <p> Il metodo è protected in modo che non sia accessibile direttamente
     * dal client.
     * 
     * @param azienda l'azienda che deve essere quotata in questa borsa.
     * @param azioniTotali il numero di azioni totali dell'Azienda che
     *  saranno disponibili in questa borsa.
     * @param prezzo il prezzo unitario di ciascuna azione.
     * @throws NullPointerException se l'azienda è {@code null}.
     * @throws IllegalArgumentException se il numero di azioni totali o il prezzo
     * sono minori o uguali a 0.
     */
    protected void quota(final Azienda azienda, final int azioniTotali, final int prezzo) {
        Azione azione = new Azione(azienda, azioniTotali, prezzo);
        azioni.add(azione);
    }

    /**
     * Compra un'azione in questa borsa.
     * 
     * <p> Questo metodo inoltre modifica lo stato 
     *  dell'azione, poiché aggiunge l'operatore che ha comprato l'azione
     *  e la quantità alla mappa degli operatori dell'azione, e 
     *  modifica il prezzo dell'Azione in base alla politica di variazione 
     *  del prezzo impostata per questa borsa. </p>
     *  
     * <p> Il metodo è protected in modo che non sia accessibile direttamente
     * dal client.
     * 
     * @param azione l'azione che l'operatore vuole comprare.
     * @param operatore l'operatore che vuole effettuare l'operazione di acquisto.
     * @param quantita la quantità di azioni che l'operatore desidera comprare.
     * @throws NullPointerException se l'azione o l'operatore sono {@code null}.
     * @throws IllegalArgumentException se la quantità di azioni da comprare
     *  è minore o uguale a 0, se la quantità di azioni che si vogliono
     *  comprare è maggiore delle azioni disponibili o se il budget
     *  dell'operatore non è sufficiente per acquistare le azioni.
     */
    protected void compraAzione(final Azione azione, final Operatore operatore, final int quantita) {
        Objects.requireNonNull(azione, "L'azione non può essere null");
        Objects.requireNonNull(operatore, "L'operatore non può essere null");
        if (quantita <= 0)
            throw new IllegalArgumentException("La quantità deve essere positiva");
        if (azione.azioniDisponibili() < quantita)
            throw new IllegalArgumentException("La quantità di azioni disponibili risulta essere minore della quantità richiesta");
        if (operatore.budget() < azione.prezzo() * quantita)
            throw new IllegalArgumentException("Budget non sufficiente per acquistare le azioni");
        azione.operatoriQuantita.put(operatore, azione.operatoriQuantita.getOrDefault(operatore, 0) + quantita);
        if (azione.operatoriQuantita.get(operatore) == 0) {
            azione.operatoriQuantita.remove(operatore);
        }
        azione.prezzo(politica.calcolaPrezzo(azione, quantita, true));
    }

    /**
     * Vende un'azione presente in questa borsa.
     * 
     * <p> Questo metodo inoltre modifica lo stato
     *  dell'azione, poiché modifica il valore delle azioni
     *  possedute dall'operatore presenti nella mappa degli operatori dell'Azione,
     *  e modifica il prezzo dell'Azione in base alla politica di variazione
     *  del prezzo impostata per questa borsa. </p>
     * 
     * <p> Il metodo è protected in modo che non sia accessibile direttamente
     * dal client.
     * 
     * @param azione l'azione che l'operatore vuole vendere.
     * @param operatore l'operatore che vuole effettuare l'operazione di vendita.
     * @param quantita la quantità di azioni che l'operatore desidera vendere.
     * @throws NullPointerException se l'azione è {@code null}.
     * @throws IllegalArgumentException se la quantità di azioni da vendere
     *  è minore o uguale a 0 o se l'operatore non possiede abbastanza azioni
     *  da vendere.
     * 
     */
    protected void vendiAzione(final Azione azione, final Operatore operatore, final int quantita) {
        Objects.requireNonNull(azione, "L'azione non può essere null");
        if (quantita <= 0)
            throw new IllegalArgumentException("La quantità deve essere positiva");
        if (azione.operatoriQuantita.getOrDefault(operatore, 0) < quantita)
            throw new IllegalArgumentException("Quantità di azioni possedute non sufficiente per la vendita");
        azione.operatoriQuantita.put(operatore, azione.operatoriQuantita.get(operatore) - quantita);
        if (azione.operatoriQuantita.get(operatore) == 0) {
            azione.operatoriQuantita.remove(operatore);
        }
        azione.prezzo(politica.calcolaPrezzo(azione, quantita, false));
    }

    /**
     * Restituisce l'azione quotata in questa borsa per l'azienda specificata.
     * 
     * @param azienda l'azienda di cui si vuole ottenere l'azione quotata in questa borsa.
     * @throws NullPointerException se l'azienda è {@code null}.
     * @throws NoSuchElementException se l'azienda non è quotata in questa borsa.
     * @return l'azione presente in questa borsa.
     */
    public Azione prendiAzione(final Azienda azienda) {
        Objects.requireNonNull(azienda, "L'azienda non può essere null");
        for (Azione a : azioni)
            if (a.azienda().nome.equals(azienda.nome))
                return a;
        throw new NoSuchElementException("Azione non quotata in questa borsa");
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Borsa other))
            return false;
        return nome.equals(other.nome);
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nome).append("\n");
        for (Azione a : azioni) {
            sb.append("- ").append(a.azienda().nome);
            sb.append(" ").append(a.azioniDisponibili()).append("\n");
            for (Map.Entry<Operatore, Integer> entry : a.operatoriQuantita.entrySet())
                sb.append("= ").append(entry.getKey().nome).append(" ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public int compareTo(Borsa altra) {
        return nome.compareTo(altra.nome);
    }

    /**
     * Classe che rappresenta un'azione.
     * 
     * <p> Un'azione è la rappresentazione di un'azienda quotata in una borsa.
     * 
     * <p> Un'azione è rappresentata da:
     *  <ul>
     *   <li> Un'azienda; </li>
     *   <li> Il numero di azioni totali; </li>
     *   <li> Il prezzo unitario dell'azione; </li>
     *   <li> Un elenco di operatori che possiedono queste azioni. </li>
     *  </ul>
     * 
     * <p> Il prezzo di un'azione può variare in base alle operazioni di acquisto e vendita
     * a seconda della politica di variazione del prezzo impostata per la borsa in cui è quotata.
     * 
     * <p> Non è possibile avere due azioni con la stessa azienda almeno che 
     * non siano quotate in borse diverse.
     * 
     * <p> Data un'istanza di Azione, è possibile sapere:
     *  <ul>
     *   <li> l'azienda di cui rappresenta le azioni; </li>
     *   <li> il numero di azioni totali; </li>
     *   <li> il prezzo unitario; </li>
     *   <li> il nome della borsa in cui è quotata; </li>
     *   <li> il numero di azioni disponibili; </li>
     *  </ul>
     * 
     * <p> Il criterio di confronto e ordinamento tra azioni è sia
     * il nome della borsa in cui sono quotate che il nome dell'azienda.
     * 
     * @see Comparable
     * 
     */
    public class Azione implements Comparable<Azione> {

        /** L'Azienda di cui questa azione rappresenta le azioni. */
        private final Azienda azienda;

        /** Il numero di azioni totali. */
        private final int azioniTotali;

        /** Il prezzo unitario dell'azione. */
        private int prezzo;

        /**
         * Elenco degli operatori che possiedono queste azioni.
         * La chiave è l'operatore e il valore è la quantità di azioni possedute.
         */
        private final Map<Operatore, Integer> operatoriQuantita = new TreeMap<>();

        /*-
         * AF: 
         *  - azienda è l'azienda di cui questa azione rappresenta le azioni.
         *  - azioniTotali sono le azioni totali di questa azione.
         *  - prezzo è il prezzo unitario dell'azione.
         *  - operatoriQuantita contiene come chiavi gli operatori che posseggono 
         *    l'azione e come valore la quantità di azioni possedute.
         * 
         * RI:
         *  - azienda non può essere null.
         *  - azioniTotali non può essere minore o uguale a 0.
         *  - prezzo non può essere minore o uguale a 0.
         *  - operatoriQuantita non può essere null e non può contenere null.
         *    Non può contenere duplicati e deve essere ordinata in ordine lessicografico.
         *    I valori di operatoriQuantità devono essere maggiori di 0.
         */

        /**
         * Costruisce un'istanza di Azione.
         * 
         * <p> Viene modificato this.
         * 
         * <p> Il costruttore è privato per garantire
         *  che le istanze di Azione siano create solo
         *  all'interno della classe Borsa. </p>
         * 
         * @param azienda l'azienda di cui questa azione rappresenta le azioni.
         * @param azioniTotali il numero di azioni totali.
         * @param prezzo il prezzo unitario dell'azione.
         * @throws NullPointerException se l'azienda è {@code null}.
         * @throws IllegalArgumentException se il numero di azioni totali
         * o il prezzo è minore o uguale a 0.
         */
        private Azione(final Azienda azienda, final int azioniTotali, final int prezzo) {
            Objects.requireNonNull(azienda, "L'azienda non può essere null");
            if (azioniTotali <= 0)
                throw new IllegalArgumentException("Il numero di azioni totali deve essere positivo");
            if (prezzo <= 0)
                throw new IllegalArgumentException("Il prezzo deve essere positivo");
            this.azienda = azienda;
            this.azioniTotali = azioniTotali;
            this.prezzo = prezzo;
        }

        /**
         * Restituisce l'azienda di cui questa azione rappresenta le azioni.
         * @return l'istanza di Azienda.
         */
        public Azienda azienda() {
            return azienda;
        }

        /**
         * Restituisce le azioni totali di questa azione.
         * @return le azioni totali.
         */
        public int azioniTotali() {
            return azioniTotali;
        }

        /**
         * Restituisce il prezzo unitario di questa azione.
         * @return il prezzo unitario.
         */
        public int prezzo() {
            return prezzo;
        }

        /**
         * Imposta il prezzo unitario di questa azione.
         * 
         * <p> Viene modificato this dato che viene impostato
         * un nuovo prezzo.
         * 
         * @param prezzo il prezzo unitario.
         * @throws IllegalArgumentException se il prezzo è minore o uguale a 0.
         */
        private void prezzo(int prezzo) {
            if (prezzo <= 0)
                throw new IllegalArgumentException("Il prezzo deve essere positivo");
            this.prezzo = prezzo;
        }

        /**
         * Restituisce il nome della borsa in cui questa azione è quotata.
         * @return il nome della borsa.
         */
        public String nomeBorsa() {
            return nome;
        }

        /**
         * Restituisce il numero di azioni disponibili 
         * di questa azione.
         * 
         * <p> Le azioni disponibili sono le azioni totali
         * meno le azioni possedute dagli operatori. </p>
         * 
         * @return il numero di azioni disponibili.
         */
        public int azioniDisponibili() {
            int azioniDis = azioniTotali;
            for (Map.Entry<Operatore, Integer> entry : operatoriQuantita.entrySet())
                azioniDis -= entry.getValue();
            return azioniDis;
        }

        @Override
        public int compareTo(Azione o) {
            int result = nomeBorsa().compareTo(o.nomeBorsa());
            if (result == 0) {
                result = azienda().nome.compareTo(o.azienda().nome);
            }
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Azione other))
                return false;
            return azienda().equals(other.azienda()) && nomeBorsa().equals(other.nomeBorsa());
        }

        @Override
        public int hashCode() {
            return azienda().nome.hashCode();
        }
    }
}
